package com.finn.ws.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.converter.MessageConverter
import org.springframework.messaging.converter.StringMessageConverter
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.scheduling.TaskScheduler
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class StompWebSocketConfig : WebSocketMessageBrokerConfigurer {

    @Autowired
    lateinit var stompHeaderUtility: StompHeaderUtility

    private lateinit var messageBrokerTaskScheduler: TaskScheduler

    @Autowired
    fun setMessageBrokerTaskScheduler(taskScheduler: TaskScheduler) {
        this.messageBrokerTaskScheduler = taskScheduler
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
//         PUB-SUB channel. handle SUBSCRIBE
        registry.enableSimpleBroker("/topic")
                .setHeartbeatValue(
                        longArrayOf(
                                5000,// heartbeat-in milliseconds
                                5000 // heartbeat-out milliseconds
                        )
                )
                .setTaskScheduler(this.messageBrokerTaskScheduler)

//         Request channel. handle SEND
        registry.setApplicationDestinationPrefixes("/app")
    }

    override fun configureMessageConverters(messageConverters: MutableList<MessageConverter>): Boolean {
        messageConverters.add(StringMessageConverter())
        return super.configureMessageConverters(messageConverters)
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws/finn")
                .setAllowedOrigins("*")
                .withSockJS()
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(object : ChannelInterceptor {
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
