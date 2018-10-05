package org.vaccineimpact.reporting_api.db

import com.google.gson.JsonObject
import org.vaccineimpact.api.models.reports.Report
import org.vaccineimpact.api.models.reports.ReportVersion
import org.vaccineimpact.api.models.reports.ReportVersionDetails
import org.vaccineimpact.reporting_api.errors.UnknownObjectError

interface OrderlyClient
{
    fun getAllReports(): List<Report>

    fun getAllReportVersions(): List<ReportVersion>

    @Throws(UnknownObjectError::class)
    fun getVersionIDsForReportByName(name: String): List<String>

    @Throws(UnknownObjectError::class)
    fun getReportVersionDetails(name: String, version: String): ReportVersionDetails

    @Throws(UnknownObjectError::class)
    fun getArtefacts(name: String, version: String): JsonObject

    @Throws(UnknownObjectError::class)
    fun getArtefact(name: String, version: String, filename: String): String

    @Throws(UnknownObjectError::class)
    fun getData(name: String, version: String): JsonObject

    @Throws(UnknownObjectError::class)
    fun getDatum(name: String, version: String, datumname: String): String

    @Throws(UnknownObjectError::class)
    fun getResources(name: String, version: String): JsonObject

    @Throws(UnknownObjectError::class)
    fun getResource(name: String, version: String, resourcename: String): String
}