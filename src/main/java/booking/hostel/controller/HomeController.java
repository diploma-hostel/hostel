package booking.hostel.controller;

import booking.hostel.service.HostelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final HostelService hostelService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("hostels", hostelService.getAll());
        return "index";
    }
}
