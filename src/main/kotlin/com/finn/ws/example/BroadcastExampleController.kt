package com.finn.ws.example

import com.finn.ws.extentions.getLogger
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.stereotype.Controller
import java.security.Principal

@Controller
class BroadcastExampleController(
        private val rabbitTemplate: RabbitTemplate
) {
    companion object {
        private val logger = getLogger()
    }

    @MessageMapping("/example")
    fun broadcastExample(@Payload message: String, principal: Principal) {
        rabbitTemplate.convertAndSend("exchange_name", "example_routing_key", "${principal.name}: $message")
    }
}