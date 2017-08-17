package org.vaccineimpact.reporting_api.controllers

import com.google.gson.JsonObject
import org.vaccineimpact.api.models.Scope
import org.vaccineimpact.api.models.permissions.ReifiedPermission
import org.vaccineimpact.reporting_api.*
import org.vaccineimpact.reporting_api.db.Config
import org.vaccineimpact.reporting_api.db.Orderly
import org.vaccineimpact.reporting_api.db.OrderlyClient
import org.vaccineimpact.reporting_api.errors.OrderlyFileNotFoundError

class ResourceController(context: ActionContext,
                         val orderly: OrderlyClient,
                         val files: FileSystem) : Controller(context)
{
    constructor(context: ActionContext) :
            this(context, Orderly(context.hasPermission(ReifiedPermission("reports.review", Scope.Global()))), Files())

    fun get(): JsonObject
    {
        return orderly.getResources(context.params(":name"), context.params(":version"))
    }

    fun download(): Boolean
    {
        val name = context.params(":name")
        val version = context.params(":version")
        val resourcename = parseRouteParamToFilepath(context.params(":resource"))

        orderly.getResource(name, version, resourcename)

        val filename = "$name/$version/$resourcename"

        val absoluteFilePath = "${Config["orderly.root"]}archive/$filename"

        if (!files.fileExists(absoluteFilePath))
            throw OrderlyFileNotFoundError(resourcename)

        context.addResponseHeader("Content-Disposition", "attachment; filename=$filename")
        context.addDefaultResponseHeaders(ContentTypes.binarydata)

        val response = context.getSparkResponse().raw()
        files.writeFileToOutputStream(absoluteFilePath, response.outputStream)

        return true
    }

}
