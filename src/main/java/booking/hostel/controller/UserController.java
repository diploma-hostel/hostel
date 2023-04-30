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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final HostelService hostelService;
    private final BookingService bookingService;

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        User user = userService.getByPhone(principal.getName());
        List<Hostel> hostels = new ArrayList<>();
        List<Booking> bookings = new ArrayList<>();
        if (user.getRole().getName().equals(Role.ROLE_OWNER.getName())) {
            hostels = hostelService.getByOwner(user);
            model.addAttribute("isOwner", true);
        }
        if (user.getRole().equals(Role.ROLE_CLIENT)) {
            bookings = bookingService.getByUser(user);
            model.addAttribute("isOwner", false);
        }
        model.addAttribute("hostels", hostels);
        model.addAttribute("bookings", bookings);
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
