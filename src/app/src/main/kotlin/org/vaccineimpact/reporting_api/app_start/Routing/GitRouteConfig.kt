package org.vaccineimpact.reporting_api.app_start.Routing

import org.vaccineimpact.reporting_api.Endpoint
import org.vaccineimpact.reporting_api.EndpointDefinition
import org.vaccineimpact.reporting_api.app_start.RouteConfig
import org.vaccineimpact.reporting_api.controllers.GitController
import org.vaccineimpact.reporting_api.json
import spark.route.HttpMethod

object GitRouteConfig : RouteConfig {
    private val runReports = setOf("*/reports.run")
    private val controller = GitController::class

    override val endpoints: List<EndpointDefinition> = listOf(
            Endpoint("/reports/git/status/", controller, "status")
                    .json(),

            Endpoint("/reports/git/pull/", controller, "pull", method = HttpMethod.post)
                    .json(),

            Endpoint("/reports/git/fetch/", controller, "fetch", method = HttpMethod.post)
                    .json()
    )

}