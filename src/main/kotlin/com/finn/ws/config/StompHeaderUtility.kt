package com.finn.ws.config

import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import java.security.Principal

@Component
class StompHeaderUtility() {
    fun createPrincipalFromStompConnectCommandHeader(headerAccessor: StompHeaderAccessor): Principal? {
        return if (StompCommand.CONNECT == headerAccessor.command) headerAccessor.getHeader("simpUser") as Principal? else null
    }
}