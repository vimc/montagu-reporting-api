package org.vaccineimpact.reporting_api

import org.vaccineimpact.api.models.Scope
import org.vaccineimpact.api.models.permissions.PermissionSet
import org.vaccineimpact.api.models.permissions.ReifiedPermission
import org.vaccineimpact.reporting_api.errors.MissingRequiredPermissionError
import spark.Filter
import spark.Request
import spark.Response
import javax.servlet.http.HttpServletResponse

// The idea is that as this file grows, I'll group helpers and split them off into files/classes with more
// specific aims.

fun addTrailingSlashes(req: Request, res: Response)
{
    if (!req.pathInfo().endsWith("/"))
    {
        var path = req.pathInfo() + "/"
        if (req.queryString() != null)
        {
            path += "?" + req.queryString()
        }
        res.redirect(path)
    }
}

fun addDefaultResponseHeaders(res: HttpServletResponse, contentType: String)
{
    if (!res.containsHeader("Content-Encoding"))
    {
        res.contentType = contentType
        res.addHeader("Content-Encoding", "gzip")
        res.addHeader("Access-Control-Allow-Origin", "*")
    }
}

class DefaultHeadersFilter(val contentType: String) : Filter
{
    override fun handle(request: Request, response: Response)
    {
        addDefaultResponseHeaders(response.raw(), contentType)
    }
}

fun parseRouteParamToFilepath(routeParam: String): String
{
    return routeParam.replace(":", "/")
}

fun ActionContext.authorizedReport(): String
{

    val name = this.params(":name")
    val requiredScope = Scope.Specific("report", name)
    if (!reportReadingScopes().any({ it.encompasses(requiredScope) }))
    {
        throw MissingRequiredPermissionError(PermissionSet("$requiredScope/reports.read"))
    }

    return name
}


fun ActionContext.reportReadingScopes(): List<Scope>
{
   return this.permissions
            .filter { it.name == "reports.read" }
            .map { it.scope }
}

