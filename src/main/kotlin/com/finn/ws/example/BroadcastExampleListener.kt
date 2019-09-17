package com.finn.ws.example

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class BroadcastExampleListener(
        private val simpMessagingTemplate: SimpMessagingTemplate
) {

    @RabbitListener(queues = ["example_queue"])
    fun onMessage(message: String) {
        simpMessagingTemplate.convertAndSend(
                "/subs/subs_example",
                "$message [BROADCASTED]"
        )
    }
}