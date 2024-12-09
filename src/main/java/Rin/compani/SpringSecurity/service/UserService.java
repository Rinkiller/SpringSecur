package Rin.compani.SpringSecurity.service;


import Rin.compani.SpringSecurity.models.User;
import Rin.compani.SpringSecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserService {

    /**
     * Репозиторий для работы с пользователями.
     */
    private final UserRepository userRepository;

    /**
     * Экземпляр PasswordEncoder для шифрования паролей.
     */
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Сохраняет нового пользователя в базу данных.
     *
     * @param user Новый пользователь
     * @return Сохраненный пользователь
     */
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Получает все пользователей из базы данных.
     *
     * @return Список всех пользователей
     */
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Поиск пользователя по email.
     *
     * @param email Email пользователя
     * @return Optional с найденным пользователем или пустой, если не найден
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}