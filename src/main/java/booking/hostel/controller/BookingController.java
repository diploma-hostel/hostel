package booking.hostel.controller;

import booking.hostel.entity.*;
import booking.hostel.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final HostelService hostelService;
    private final UserService userService;

    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id, Model model) {
        Booking booking = bookingService.getById(id);
        model.addAttribute("booking", booking);
        return "booking";
    }

    @GetMapping
    public String getAll(Model model) {
        List<Booking> bookings = bookingService.getAll();
        model.addAttribute("bookings", bookings);
        return "bookings";
    }

    @GetMapping("/new")
    public String create(Model model) {
        Booking booking = new Booking();
        model.addAttribute("booking", booking);
        model.addAttribute("hostels", hostelService.getAll());
        model.addAttribute("users", userService.getAll());
        return "new-booking";
    }

    @PostMapping("/bookings")
    public String save(Booking booking) {
        bookingService.save(booking);
        return "redirect:/bookings";
    }
}

