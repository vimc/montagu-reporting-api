package org.vaccineimpact.reporting_api.db

import com.google.gson.*
import org.jooq.TableField
import org.jooq.impl.DSL.*
import org.vaccineimpact.api.models.reports.Report
import org.vaccineimpact.api.models.reports.ReportVersion
import org.vaccineimpact.api.models.reports.ReportVersionDetails
import org.vaccineimpact.reporting_api.db.Tables.REPORT_VERSION
import org.vaccineimpact.reporting_api.db.tables.records.OrderlyRecord
import org.vaccineimpact.reporting_api.errors.UnknownObjectError
import java.sql.Timestamp

class Orderly(isReviewer: Boolean = false) : OrderlyClient
{
    override fun getAllReportVersions(): List<ReportVersion>
    {
        JooqContext().use {
            // create a temp table containing the latest version ID for each report name
            val latestVersionForEachReport = it.dsl.select(
                    REPORT_VERSION.REPORT,
                    REPORT_VERSION.ID.`as`("latestVersion"),
                    REPORT_VERSION.DATE.max().`as`("maxDate")
            )
                    .from(REPORT_VERSION)
                    .where(shouldInclude)
                    .groupBy(REPORT_VERSION.REPORT)
                    .asTemporaryTable(name = "latest_version_for_each_report")

            // join with all the versions to get full details of each report + version alongside
            // the latest version ID for that report
            return it.dsl.withTemporaryTable(latestVersionForEachReport)
                    .select(REPORT_VERSION.REPORT,
                            REPORT_VERSION.DISPLAYNAME,
                            REPORT_VERSION.ID,
                            REPORT_VERSION.PUBLISHED,
                            REPORT_VERSION.DATE.`as`("updatedOn"),
                            REPORT_VERSION.AUTHOR,
                            REPORT_VERSION.REQUESTER,
                            latestVersionForEachReport.field("latestVersion"))
                    .from(REPORT_VERSION)
                    .join(latestVersionForEachReport.tableName)
                    .on(REPORT_VERSION.REPORT.eq(latestVersionForEachReport.field("name")))
                    .where(shouldInclude)
                    .orderBy(REPORT_VERSION.REPORT, REPORT_VERSION.ID)
                    .fetchInto(ReportVersion::class.java)
        }
    }

    private val gsonParser = JsonParser()

    private val shouldInclude = REPORT_VERSION.PUBLISHED.bitOr(isReviewer)

    override fun getAllReports(): List<Report>
    {
        JooqContext().use {

            val tempTable = "all"

            val allReports = it.dsl.select(REPORT_VERSION.REPORT,
                    REPORT_VERSION.DATE.max().`as`("maxDate"))
                    .from(REPORT_VERSION)
                    .where(shouldInclude)
                    .groupBy(REPORT_VERSION.REPORT)

            return it.dsl.with(tempTable).`as`(allReports)
                    .select(REPORT_VERSION.REPORT, REPORT_VERSION.DISPLAYNAME,
                            REPORT_VERSION.ID.`as`("latestVersion"),
                            REPORT_VERSION.PUBLISHED,
                            REPORT_VERSION.DATE.`as`("updatedOn"),
                            REPORT_VERSION.AUTHOR,
                            REPORT_VERSION.REQUESTER)
                    .from(REPORT_VERSION)
                    .join(table(name(tempTable)))
                    .on(REPORT_VERSION.REPORT.eq(field(name(tempTable, "name"), String::class.java))
                            .and(REPORT_VERSION.DATE.eq(field(name(tempTable, "maxDate"), Timestamp::class.java))))
                    .where(shouldInclude)
                    .fetchInto(Report::class.java)
        }

    }

    override fun getVersionIDsForReportByName(name: String): List<String>
    {
        JooqContext().use {

            val result = it.dsl.select(REPORT_VERSION.ID)
                    .from(REPORT_VERSION)
                    .where(REPORT_VERSION.REPORT.eq(name)
                            .and(shouldInclude))

            if (result.count() == 0)
            {
                throw UnknownObjectError(name, "report")
            }
            else
            {
                return result.fetchInto(String::class.java)
            }
        }
    }

    override fun getReportVersionDetails(name: String, version: String): ReportVersionDetails
    {
        return JooqContext().use {

            val v = it.dsl.select()
                    .from(REPORT_VERSION)
                    .where(REPORT_VERSION.REPORT.eq(name)
                            .and((REPORT_VERSION.ID).eq(version))
                            .and(shouldInclude))
                    .fetchAnyInto(ReportVersion::class.java)
                    ?: throw UnknownObjectError("$name-$version", "reportVersion")
            ReportVersionDetails()
        }

    }

    override fun getArtefacts(name: String, version: String): JsonObject
    {
        return getSimpleMap(name, version, ORDERLY.HASH_ARTEFACTS)
    }

    override fun getArtefact(name: String, version: String, filename: String): String
    {
        val result = getSimpleMap(name, version, ORDERLY.HASH_ARTEFACTS)[filename]
                ?: throw UnknownObjectError(filename, "Artefact")

        return result.asString
    }

    override fun getData(name: String, version: String): JsonObject
    {
        return getSimpleMap(name, version, ORDERLY.HASH_DATA)
    }

    override fun getDatum(name: String, version: String, datumname: String): String
    {
        val result = getSimpleMap(name, version, ORDERLY.HASH_DATA)[datumname]
                ?: throw UnknownObjectError(datumname, "Data")

        return result.asString
    }

    override fun getResources(name: String, version: String): JsonObject
    {
        return getSimpleMap(name, version, ORDERLY.HASH_RESOURCES)
    }

    override fun getResource(name: String, version: String, resourcename: String): String
    {
        val result = getSimpleMap(name, version, ORDERLY.HASH_RESOURCES)[resourcename]
                ?: throw UnknownObjectError(resourcename, "Resource")

        return result.asString
    }


    private fun getSimpleMap(name: String, version: String, column: TableField<OrderlyRecord, String>): JsonObject
    {
        JooqContext().use {
            val result = it.dsl.select(column)
                    .from(ORDERLY)
                    .where(ORDERLY.NAME.eq(name).and((ORDERLY.ID).eq(version))
                            .and(shouldInclude))
                    .fetchAny() ?: throw UnknownObjectError("$name-$version", "reportVersion")

            if (result.value1() == null)
                return JsonObject()

            return gsonParser.parse(result
                    .into(String::class.java))
                    .asJsonObject
        }
    }

}
