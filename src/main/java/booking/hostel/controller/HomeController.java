package booking.hostel.controller;

import booking.hostel.entity.Role;
import booking.hostel.entity.User;
import booking.hostel.service.HostelService;
import booking.hostel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final HostelService hostelService;
    private final UserService userService;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/team")
    public String team() {
        return "team";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupPost(@ModelAttribute("user") User user, HttpServletRequest request, Principal principal) {
        User user1 = userService.getByPhone(principal.getName());
        if (user != null) {
            user1.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user1.setFio(user.getFio());
            userService.save(user1);
            return "redirect:/users/profile";
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        boolean isOwner = request.getParameter("owner") != null;
        user.setRole(Role.ROLE_CLIENT);
        if (isOwner) user.setRole(Role.ROLE_OWNER);
        userService.save(user);
        return "redirect:/login";
    }
}
