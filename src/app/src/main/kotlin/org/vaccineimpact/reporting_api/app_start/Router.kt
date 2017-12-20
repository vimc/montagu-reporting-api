package org.vaccineimpact.reporting_api.app_start

import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import org.eclipse.jetty.websocket.api.annotations.WebSocket
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.server.WebSocketHandler
import org.slf4j.LoggerFactory
import org.vaccineimpact.reporting_api.*
import org.vaccineimpact.reporting_api.controllers.Controller
import org.vaccineimpact.reporting_api.controllers.WebsocketController
import org.vaccineimpact.reporting_api.errors.UnsupportedValueException
import spark.Route
import spark.Spark
import spark.route.HttpMethod

class Router(val config: RouteConfig)
{
    private val logger = LoggerFactory.getLogger(Router::class.java)

    companion object
    {
        val urls: MutableList<String> = mutableListOf()
    }

    fun transform(x: Any) = Serializer.instance.toResult(x)

    fun mapEndpoints(urlBase: String)
    {
        urls.addAll(config.endpoints.map {
            when (it.transform)
            {
                true -> mapTransformedEndpoint(it, urlBase)
                false -> mapEndpoint(it, urlBase)
            }
        })
    }

    private fun mapTransformedEndpoint(
            endpoint: EndpointDefinition,
            urlBase: String): String
    {
        val fullUrl = urlBase + endpoint.urlFragment
        val route = getWrappedRoute(endpoint)::handle
        val contentType = endpoint.contentType

        logger.info("Mapping $fullUrl to ${endpoint.actionName} on Controller ${endpoint.controllerName}")
        when (endpoint.method)
        {
            HttpMethod.get -> Spark.get(fullUrl, contentType, route, this::transform)
            HttpMethod.post -> Spark.post(fullUrl, contentType, route, this::transform)
            HttpMethod.put -> Spark.put(fullUrl, contentType, route, this::transform)
            HttpMethod.patch -> Spark.patch(fullUrl, contentType, route, this::transform)
            HttpMethod.delete -> Spark.delete(fullUrl, contentType, route, this::transform)
            else -> throw UnsupportedValueException(endpoint.method)
        }

        endpoint.additionalSetup(fullUrl)
        return fullUrl
    }

    private fun mapEndpoint(
            endpoint: EndpointDefinition,
            urlBase: String): String
    {

        val fullUrl = urlBase + endpoint.urlFragment
        val route = getWrappedRoute(endpoint)::handle
        val contentType = endpoint.contentType

        logger.info("Mapping $fullUrl to ${endpoint.actionName} on Controller ${endpoint.controllerName}")
        when (endpoint.method)
        {
            HttpMethod.get -> Spark.get(fullUrl, contentType, route)
            HttpMethod.post -> Spark.post(fullUrl, contentType, route)
            HttpMethod.put -> Spark.put(fullUrl, contentType, route)
            HttpMethod.patch -> Spark.patch(fullUrl, contentType, route)
            HttpMethod.delete -> Spark.delete(fullUrl, contentType, route)
            else -> throw UnsupportedValueException(endpoint.method)
        }

        endpoint.additionalSetup(fullUrl)
        return fullUrl
    }


    private fun getWrappedRoute(endpoint: EndpointDefinition): Route
    {
        return Route({ req, res -> invokeControllerAction(endpoint, DirectActionContext(req, res)) })
    }

    private fun invokeControllerAction(endpoint: EndpointDefinition, context: ActionContext): Any?
    {
        val controllerName = endpoint.controllerName
        val actionName = endpoint.actionName

        val controllerType = Class.forName("org.vaccineimpact.reporting_api.controllers.${controllerName}Controller")

        val controller = controllerType.getConstructor(ActionContext::class.java)
                .newInstance(context) as Controller
        val action = controllerType.getMethod(actionName)

        return action.invoke(controller)
    }

}