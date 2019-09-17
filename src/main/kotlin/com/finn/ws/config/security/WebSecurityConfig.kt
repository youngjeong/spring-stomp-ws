package com.finn.ws.config.security

import com.finn.ws.config.security.data.Role
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class WebSecurityConfig(
        private val memberService: MemberServiceImpl,
        private val passwordEncoder: PasswordEncoder
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/sign_up").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/ws/finn/**").hasAnyRole(* Role.values().map { it.name }.toTypedArray())
//                .anyRequest().authenticated()
                .and().csrf().disable()
                .formLogin()
//                .and()
//            .httpBasic().disable()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(memberService)?.passwordEncoder(passwordEncoder)
    }

    override fun configure(web: WebSecurity?) {
        web?.ignoring()?.antMatchers("/resources/**")
    }
}