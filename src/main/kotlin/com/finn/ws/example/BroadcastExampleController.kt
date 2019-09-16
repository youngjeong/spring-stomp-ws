package com.finn.ws.example

import com.finn.ws.extentions.getLogger
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

@Controller
class BroadcastExampleController(
        private val rabbitTemplate: RabbitTemplate
) {
    companion object {
        private val logger = getLogger()
    }

    @MessageMapping("/example")
    fun broadcastExample(message: String) {
        rabbitTemplate.convertAndSend("exchange_name", "example_routing_key", "Hello, rabbit! I am $message")
    }
}