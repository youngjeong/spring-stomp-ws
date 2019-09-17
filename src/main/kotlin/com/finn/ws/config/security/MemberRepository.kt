package com.finn.ws.config.security

import com.finn.ws.config.security.data.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByName(name: String): Member?
}