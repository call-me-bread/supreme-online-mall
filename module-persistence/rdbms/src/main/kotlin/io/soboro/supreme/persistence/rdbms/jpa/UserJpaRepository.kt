package io.soboro.supreme.persistence.rdbms.jpa

import io.soboro.supreme.core.model.user.entity.User
import io.soboro.supreme.core.model.user.vo.Email
import io.soboro.supreme.core.repository.UserRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface UserJpaRepository : JpaRepository<User, Long> {
    fun existsByUserSecretEmail(email: Email): Boolean
    fun findByUsername(username: String): User?
    fun findByUserSecretEmail(email: Email): User?
}

@Repository
class UserRepositoryAdapter(
    private val userJpaRepository: UserJpaRepository,
) : UserRepository {
    override fun findByUsername(username: String): User? {
        return userJpaRepository.findByUsername(username)
    }

    override fun existsByEmail(email: Email): Boolean {
        return userJpaRepository.existsByUserSecretEmail(email)
    }

    override fun save(user: User) {
        userJpaRepository.save(user)
    }

    override fun findByEmail(email: Email): User? {
        return userJpaRepository.findByUserSecretEmail(email)
    }
}
