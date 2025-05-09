package kz.alishev.config

import jakarta.annotation.PostConstruct
import kz.alishev.model.Role
import kz.alishev.repository.RoleRepository
import org.springframework.stereotype.Component

@Component
class RoleInitializer(private val roleRepository: RoleRepository) {

    @PostConstruct
    fun init() {

        if (!roleRepository.findByName("ROLE_USER").isPresent) {

            val userRole = Role()
            userRole.name = "ROLE_USER"
            roleRepository.save(userRole)
        }


        if (!roleRepository.findByName("ROLE_ADMIN").isPresent) {
            val adminRole = Role()
            adminRole.name = "ROLE_ADMIN"
            roleRepository.save(adminRole)
        }
    }
}