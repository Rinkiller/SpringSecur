package Rin.compani.SpringSecurity.controllers;


import Rin.compani.SpringSecurity.models.User;
import Rin.compani.SpringSecurity.service.UserService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
        * Контроллер для обработки запросов аутентификации.
        */
@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class LoginController {
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @PostMapping("/register")
    public String register(User user, RedirectAttributes redirectAttributes) {
        User newUser = new User(user.getName(), user.getEmail(), user.getPassword(), User.Role.USER);
        userService.saveUser(newUser);
        redirectAttributes.addFlashAttribute("message", "Пользователь успешно зарегистрирован!");
        return "redirect:/auth/dashboard";
    }
}