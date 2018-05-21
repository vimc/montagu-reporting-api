package org.vaccineimpact.reporting_api.app_start.Routing

import org.vaccineimpact.reporting_api.*
import org.vaccineimpact.reporting_api.app_start.RouteConfig
import org.vaccineimpact.reporting_api.controllers.ArtefactController
import org.vaccineimpact.reporting_api.controllers.DataController
import org.vaccineimpact.reporting_api.controllers.ReportController
import org.vaccineimpact.reporting_api.controllers.ResourceController
import spark.route.HttpMethod

object VersionRouteConfig : RouteConfig
{
    private val readReports = setOf("report:<name>/reports.read")
    private val reviewReports = setOf("*/reports.review")
    private val artefactController = ArtefactController::class
    private val reportController = ReportController::class
    private val dataController = DataController::class
    private val resourceController = ResourceController::class

    override val endpoints = listOf(
            Endpoint("/reports/:name/versions/:version/", reportController, "getByNameAndVersion")
                    .json()
                    .transform(),

            Endpoint("/reports/:name/versions/:version/all/", reportController, "getZippedByNameAndVersion",
                    ContentTypes.zip)
                    .allowParameterAuthentication(),

            Endpoint("/reports/:name/versions/:version/publish/", reportController, "publish",
                    method = HttpMethod.post)
                    .json(),

            Endpoint("/reports/:name/versions/:version/artefacts/", artefactController, "get")
                    .json()
                    .transform(),

            Endpoint("/reports/:name/versions/:version/artefacts/:artefact/", artefactController, "download")
                    .allowParameterAuthentication(),

            Endpoint("/reports/:name/versions/:version/resources/", resourceController, "get")
                    .json()
                    .transform(),

            Endpoint("/reports/:name/versions/:version/resources/:resource/", resourceController, "download")
                    .allowParameterAuthentication(),

            Endpoint("/reports/:name/versions/:version/data/", dataController, "get")
                    .json()
                    .transform(),

            Endpoint("/reports/:name/versions/:version/data/:data/", dataController, "downloadData")
                    .allowParameterAuthentication()
    )
}