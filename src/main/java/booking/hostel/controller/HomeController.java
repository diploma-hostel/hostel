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

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final HostelService hostelService;
    private final UserService userService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("hostels", hostelService.getAll());
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

//    @GetMapping("/about")
//    public String about() {
//        return "about";
//    }

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
    public String signupPost(@ModelAttribute("user") User user, HttpServletRequest request) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        boolean isOwner = request.getParameter("owner") != null;
        user.setRole(Role.ROLE_CLIENT);
        if (isOwner) user.setRole(Role.ROLE_OWNER);
        userService.save(user);
        return "redirect:/login";
    }
}
