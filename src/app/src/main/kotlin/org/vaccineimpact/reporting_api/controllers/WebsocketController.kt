package org.vaccineimpact.reporting_api.controllers

import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import org.eclipse.jetty.websocket.api.annotations.WebSocket

@WebSocket
class WebsocketController
{

    @OnWebSocketConnect
    fun connected(session: Session) = println("session connected")

    @OnWebSocketMessage
    fun message(session: Session, message: String)
    {
        emit(session, message)
    }

    fun emit(session: Session,
             message: String) = session.remote.sendString(message)

}