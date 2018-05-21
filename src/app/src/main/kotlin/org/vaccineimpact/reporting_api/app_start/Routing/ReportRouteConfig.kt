package org.vaccineimpact.reporting_api.app_start.Routing

import org.vaccineimpact.reporting_api.*
import org.vaccineimpact.reporting_api.app_start.RouteConfig
import org.vaccineimpact.reporting_api.controllers.ReportController
import spark.route.HttpMethod

object ReportRouteConfig : RouteConfig
{
    private val runReports = setOf("*/reports.run")
    private val readReports = setOf("report:<name>/reports.read")
    private val controller = ReportController::class

    override val endpoints: List<EndpointDefinition> = listOf(

            Endpoint("/reports/", controller, "getAllReports")
                    .json()
                    .transform(),

            Endpoint("/reports/:name/", controller, "getVersionsByName")
                    .json()
                    .transform(),

            Endpoint("/reports/:name/run/", controller, "run",
                    method = HttpMethod.post)
                    .json(),

            Endpoint("/reports/:key/status/", controller, "status")
                    .json())
}