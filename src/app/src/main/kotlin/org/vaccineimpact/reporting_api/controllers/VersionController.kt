package org.vaccineimpact.reporting_api.controllers

import java.io.File
import com.google.gson.JsonObject

import org.vaccineimpact.api.models.Changelog
import org.vaccineimpact.api.models.ReportVersionDetails
import org.vaccineimpact.api.models.Scope
import org.vaccineimpact.api.models.permissions.ReifiedPermission
import org.vaccineimpact.reporting_api.*
import org.vaccineimpact.reporting_api.db.AppConfig
import org.vaccineimpact.reporting_api.db.Config
import org.vaccineimpact.reporting_api.db.Orderly
import org.vaccineimpact.reporting_api.db.OrderlyClient
import org.vaccineimpact.reporting_api.errors.UnknownObjectError

class VersionController(context: ActionContext,
                        private val orderly: OrderlyClient,
                        private val zip: ZipClient,
                        private val orderlyServerAPI: OrderlyServerAPI,
                        private val config: Config) : Controller(context)
{

    constructor(context: ActionContext) :
            this(context,
                    Orderly(context.hasPermission(ReifiedPermission("reports.review", Scope.Global()))),
                    Zip(),
                    OrderlyServer(AppConfig(), KHttpClient()),
                    AppConfig())

    fun getChangelogByNameAndVersion(): List<Changelog>
    {
        val name = context.params(":name")
        val version = context.params(":version")
        return orderly.getChangelogByNameAndVersion(name, version)
    }

    fun getByNameAndVersion(): JsonObject
    {
        val name = context.params(":name")
        return orderly.getReportByNameAndVersion(name, context.params(":version"))
    }

    fun getDetailsByNameAndVersion(): ReportVersionDetails
    {
        val name = context.params(":name")
        return orderly.getDetailsByNameAndVersion(name, context.params(":version"))
    }

    fun getZippedByNameAndVersion(): Boolean
    {
        val name = context.params(":name")
        val version = context.params(":version")

        orderly.getReportByNameAndVersion(name, version)

        val response = context.getSparkResponse().raw()

        context.addDefaultResponseHeaders(ContentTypes.zip)
        context.addResponseHeader("Content-Disposition", "attachment; filename=$name/$version.zip")

        val folderName = "${this.config["orderly.root"]}archive/$name/$version/"

        zip.zipIt(folderName, response.outputStream, buildFileRegex(name, version, folderName))

        return true
    }

    private fun buildFileRegex(report: String, version: String, folderName: String): String
    {
        return if (context.hasPermission(ReifiedPermission("reports.review", Scope.Global())))
        {
            ".*"
        }
        else
        {
            val fileNameGroup = (orderly.getArtefactHashes(report, version)
                    + orderly.getResourceHashes(report, version))
                    .map { it.key }.joinToString("|")
            "$folderName($fileNameGroup)"
        }
    }

}