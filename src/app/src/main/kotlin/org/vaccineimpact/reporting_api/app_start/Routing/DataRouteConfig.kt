package org.vaccineimpact.reporting_api.app_start.Routing

import org.vaccineimpact.reporting_api.*
import org.vaccineimpact.reporting_api.app_start.RouteConfig
import org.vaccineimpact.reporting_api.controllers.DataController

object DataRouteConfig : RouteConfig {

    // TODO allow report level permissions for these endpoints
    // will be much easier once we have a relational database
    // as working out which report these files come from would be a pain currently
    private val readReports = setOf("*/reports.read")
    private val controller = DataController::class

    override val endpoints: List<EndpointDefinition> = listOf(
            Endpoint("/data/csv/:id/", controller, "downloadCSV", ContentTypes.csv)
                    .secure(readReports)
                    .allowParameterAuthentication(),

            Endpoint("/data/rds/:id/", controller, "downloadRDS")
                    .secure(readReports)
                    .allowParameterAuthentication()
    )
}