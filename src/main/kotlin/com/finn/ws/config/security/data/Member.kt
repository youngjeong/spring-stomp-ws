package com.finn.ws.config.security.data

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import javax.persistence.*

@Entity
data class Member(
        @Id
        @GeneratedValue
        var idx: Long,

        var name: String,

        var password: String,

        @Enumerated(EnumType.STRING)
        @ElementCollection(fetch = FetchType.EAGER)
        var roles: MutableSet<Role>
) {
    fun getAuthorities(): User {
        return User(
                this.name, this.password,
                this.roles.map { role -> SimpleGrantedAuthority("ROLE_$role") }
        )
    }

    constructor(name: String, password: String) : this(0, name, password, setOf<Role>(Role.USER) as MutableSet<Role>)
}