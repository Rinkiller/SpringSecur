package Rin.compani.SpringSecurity.configuration;


import Rin.compani.SpringSecurity.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Создает PasswordEncoder для шифрования паролей.
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }





    /**
     * Создает UserDetailsService для предоставления деталей пользователя.
     *
     * @param userService Сервис для работы с пользователями
     * @return UserDetailsService
     */
    public static UserDetailsService userDetailsService(UserService userService) {
        return email -> userService.findByEmail(email)
                .map(user -> org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                        .password(user.getPassword())
                        .roles(user.getRole().toString())
                        .build())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Создает провайдер аутентификации на основе данных пользователя.
     *
     * @param userService Сервис для работы с пользователями
     * @param passwordEncoder Экземпляр PasswordEncoder
     * @return DaoAuthenticationProvider
     */
    public static DaoAuthenticationProvider authenticationProvider(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService(userService));
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    /**
     * Настраивает цепочку фильтров безопасности.
     *
     * @param http Объект HttpSecurity для настройки безопасности
     * @return Настроенная цепочка фильтров безопасности
     * @throws Exception Может выбросить исключение при неправильной конфигурации
     */
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/auth/login", "/auth/register").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .permitAll()
                        .defaultSuccessUrl("/auth/dashboard", true)
                )
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .authenticationProvider(authenticationProvider(null, null));

        return http.build();
    }
}