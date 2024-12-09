package Rin.compani.SpringSecurity.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Модель пользователя.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    private UUID id;

    /**
     * Имя пользователя.
     */
    private String name;

    /**
     * Email пользователя (уникальное поле).
     */
    @Column(unique = true)
    private String email;

    /**
     * Пароль пользователя (хранится в зашифрованном виде).
     */
    private String password;

    /**
     * Роль пользователя в системе.
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Конструктор для создания нового пользователя.
     *
     * @param name Имя пользователя
     * @param email Email пользователя
     * @param password Пароль пользователя
     * @param role Роль пользователя
     */
    public User(String name, String email, String password, Role role) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Перечисление ролей пользователей.
     */
    public enum Role {
        USER, ADMIN
    }
}