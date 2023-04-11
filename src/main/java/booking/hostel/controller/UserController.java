package booking.hostel.controller;

import booking.hostel.entity.*;
import booking.hostel.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "user/user";
    }

    @GetMapping
    public String getAll(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "user/users";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("roles", roleService.getAll());
        model.addAttribute("user", new User());
        return "user/new-user";
    }


    @PostMapping
    public String save(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userService.save(user);
        return "redirect:/users";
    }
}
