package kz.alishev.security

import kz.alishev.model.User
import kz.alishev.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val userEntity = userRepository.findByUsername(username)
            .orElseGet { userRepository.findByEmail(username).orElseThrow {
                UsernameNotFoundException("User '$username' not found")
            } }

        val authorities = userEntity.role.name?.map { SimpleGrantedAuthority(it.toString()) }

        return org.springframework.security.core.userdetails.User(
            userEntity.username,
            userEntity.password,
            userEntity.enabled,
            true, true, true,
            authorities
        )
    }

}