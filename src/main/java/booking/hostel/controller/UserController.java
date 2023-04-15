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

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final HostelService hostelService;

//    @GetMapping("/{id}")
//    public String getById(@PathVariable Integer id, Model model) {
//        User user = userService.getById(id);
//        model.addAttribute("user", user);
//        return "user/user";
//    }

//    @GetMapping
//    public String getAll(Model model) {
//        List<User> users = userService.getAll();
//        model.addAttribute("users", users);
//        return "user/users";
//    }

//    @GetMapping("/new")
//    public String create(Model model) {
//        model.addAttribute("roles", Role.values());
//        model.addAttribute("user", new User());
//        return "user/new-user";
//    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        User user = userService.getByPhone(principal.getName());
        List<Hostel> hostels = new ArrayList<>();
        if (user.getRole().getName().equals(Role.ROLE_OWNER.getName())) {
            hostels = hostelService.getByOwner(user);
            model.addAttribute("isOwner", true);
        }
        if (user.getRole().equals(Role.ROLE_CLIENT)) {
//            get list of liked hostels
//            hostels = hostelService.getByOwner(owner);
            model.addAttribute("isOwner", false);
        }
        model.addAttribute("hostels", hostels);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @PostMapping
    public String save(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userService.save(user);
        return "redirect:/users";
    }
}
