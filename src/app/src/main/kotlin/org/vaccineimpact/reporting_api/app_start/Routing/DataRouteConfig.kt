package org.vaccineimpact.reporting_api.app_start.Routing

import org.vaccineimpact.reporting_api.*
import org.vaccineimpact.reporting_api.app_start.RouteConfig
import org.vaccineimpact.reporting_api.controllers.DataController

object DataRouteConfig : RouteConfig {

    private val controller = DataController::class

    override val endpoints: List<EndpointDefinition> = listOf(
            Endpoint("/data/csv/:id/", controller, "downloadCSV", ContentTypes.csv)
                    // more specific permisison checking in controller
                    .secure()
                    .allowParameterAuthentication(),

            Endpoint("/data/rds/:id/", controller, "downloadRDS")
                    .secure()
                    // more specific permisison checking in controller
                    .allowParameterAuthentication()
    )
}