package io.soboro.supreme.core.application

import io.soboro.supreme.core.model.user.entity.User
import io.soboro.supreme.core.model.user.exception.UserNotFoundException
import io.soboro.supreme.core.model.user.repository.UserRepository
import io.soboro.supreme.core.model.user.vo.Email
import io.soboro.supreme.core.support.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LoginService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    @Transactional(readOnly = true)
    fun validateUser(email: String, password: String) {
        val user = userRepository.findByEmail(Email(email))
            ?: throw UserNotFoundException()

        user.checkPasswordOk(password)
    }

    fun User.checkPasswordOk(password: String): Boolean {
        return passwordEncoder.matches(password, this.password().toString())
    }
}
