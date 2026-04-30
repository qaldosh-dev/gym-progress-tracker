package kz.sdu.kachalka.controller.secured;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import kz.sdu.kachalka.entity.User;
import kz.sdu.kachalka.entity.UserRole;
import kz.sdu.kachalka.service.UserService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class PrivateAdminController {

    private final UserService userService;

    @Autowired
    public PrivateAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getManagementPage(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("userName", currentUser.getName());

        if (currentUser.isSuperAdmin()) {
            List<User> candidatesToDelete = userService.findAllByRoleIn(Arrays.asList(UserRole.USER, UserRole.ADMIN));
            List<User> candidatesToUpgrade = candidatesToDelete.stream()
                    .filter(User::isSimpleUser)
                    .collect(Collectors.toList());
            model.addAttribute("candidatesToDelete", candidatesToDelete);
            model.addAttribute("candidatesToUpgrade", candidatesToUpgrade);
        } else {
            List<User> candidatesToDelete = userService.findAllByRoleIn(Collections.singleton(UserRole.USER));
            model.addAttribute("candidatesToDelete", candidatesToDelete);
        }

        return "private/admin/management-page";
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam int id) {
        Optional<User> userToDeleteOpt = userService.findById(id);
        if (userToDeleteOpt.isEmpty()) return "redirect:/admin";

        User userToDelete = userToDeleteOpt.get();
        User currentUser = userService.getCurrentUser();

        if (userToDelete.isSuperAdmin()) return "redirect:/admin";
        if (userToDelete.isAdmin() && !currentUser.isSuperAdmin()) return "redirect:/admin";

        userService.deleteById(id);
        return "redirect:/admin";
    }
}