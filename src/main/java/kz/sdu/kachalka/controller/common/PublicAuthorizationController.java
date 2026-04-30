package kz.sdu.kachalka.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import kz.sdu.kachalka.entity.User;
import kz.sdu.kachalka.entity.UserRole;
import kz.sdu.kachalka.service.UserService;

import java.util.Collections;
import java.util.Set;

@Controller
public class PublicAuthorizationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PublicAuthorizationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, @RequestParam(required = false) String error) {
        if (error != null) model.addAttribute("isAuthenticationFailed", true);
        return "public/authorization/login-page";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "public/authorization/registration-page";
    }

    @PostMapping("/registration")
    public String createUserAccount(@RequestParam String name,
                                    @RequestParam String email,
                                    @RequestParam String password) {
        String encodedPassword = passwordEncoder.encode(password);
        userService.save(new User(name, email, encodedPassword, UserRole.USER));
        forceAutoLogin(email, encodedPassword);
        return "redirect:/account";
    }

    private void forceAutoLogin(String email, String encodedPassword) {
        Set<SimpleGrantedAuthority> roles = Collections.singleton(UserRole.USER.toAuthority());
        Authentication auth = new UsernamePasswordAuthenticationToken(email, encodedPassword, roles);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}