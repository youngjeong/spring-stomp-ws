package com.finn.ws.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptorAdapter
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.scheduling.TaskScheduler
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer


@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {

    @Autowired
    lateinit var stompHeaderUtility: StompHeaderUtility

    private lateinit var messageBrokerTaskScheduler: TaskScheduler

    @Autowired
    fun setMessageBrokerTaskScheduler(taskScheduler: TaskScheduler) {
        this.messageBrokerTaskScheduler = taskScheduler
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker("/topic")
                .setHeartbeatValue(
                        longArrayOf(
                                5000,// heartbeat-in milliseconds
                                5000 // heartbeat-out milliseconds
                        )
                )
                .setTaskScheduler(this.messageBrokerTaskScheduler)
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws/coinone")
                .setAllowedOrigins("*")
                .withSockJS()
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(object : ChannelInterceptorAdapter() {
            override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {
                val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)!!

                stompHeaderUtility.createPrincipalFromStompConnectCommandHeader(accessor)?.let {
                    accessor.user = it
                }
                return message
            }
        })
    }
}
