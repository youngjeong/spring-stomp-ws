package com.finn.ws

import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduledRunner(private val simpMessagingTemplate: SimpMessagingTemplate) {
    @Scheduled(fixedDelayString = "1000")
    fun pushMessages() {
        simpMessagingTemplate.convertAndSend(
                "/topic/ping",
                "ppppoooonnnngggg"
        )
    }
}