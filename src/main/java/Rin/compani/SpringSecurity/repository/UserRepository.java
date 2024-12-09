package Rin.compani.SpringSecurity.repository;

import Rin.compani.SpringSecurity.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с пользователями.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Найти пользователя по email.
     *
     * @param email Email пользователя
     * @return Optional с найденным пользователем или пустой, если не найден
     */
    Optional<User> findByEmail(String email);
}