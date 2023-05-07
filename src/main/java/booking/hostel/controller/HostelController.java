package booking.hostel.controller;

import booking.hostel.entity.*;
import booking.hostel.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/hostels")
@RequiredArgsConstructor
@Slf4j
public class HostelController {
    private final ObjectMapper mapper;
    private final HostelService hostelService;
    private final BookingService bookingService;
    private final UserService userService;

    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id, Model model) {
        Hostel hostel = hostelService.getById(id);
        System.out.println(hostel.getImageList().size());
        model.addAttribute("hostel", hostel);
        return "hostel/hostel";
    }

    @PostMapping("/update")
    @SneakyThrows
    public String update(@ModelAttribute Hostel hostel, Authentication authentication) {
        if (hostel.getOwner().getId().equals(userService.getByPhone(authentication.getName()).getId())) {
            hostel.setUpdatedTimestamp(System.currentTimeMillis());
            hostel.setIsApproved(false);
            hostelService.save(hostel);
        }
        return "redirect:/users/profile";
    }

    @GetMapping("/settle/{id}")
    public String settle(@PathVariable Integer id, Model model, @RequestParam(required = false) Integer number, Principal principal) {
        Hostel hostel = hostelService.getById(id);
        if (number == null) {
            List<Integer> reservedNumbers = bookingService.getByHostel(hostel).stream()
                    .map(Booking::getNumber).collect(Collectors.toList());
            model.addAttribute("hostel", hostel);
            model.addAttribute("reservedNumbers", reservedNumbers);
            return "hostel/settle";
        } else {
            User user = userService.getByPhone(principal.getName());
            if (user != null && user.getRole().getName().equals(Role.ROLE_CLIENT.getName())) {
                if (bookingService.existsByHostelAndUser(hostel, user)) {
                    throw new RuntimeException("Already exists");
                }
                Booking booking = Booking.builder().number(number).hostel(hostel).user(user).build();
                bookingService.save(booking);
                return "redirect:/?success=true";
            } else {
                throw new RuntimeException("Role is incorrect");
            }
        }
    }

    @GetMapping("/unsettle/{id}")
    public String unsettle(@PathVariable Integer id, @RequestParam Integer number, Principal principal) {
        Hostel hostel = hostelService.getById(id);
        if (hostel == null) throw new RuntimeException("Not exists");
        User user = userService.getByPhone(principal.getName());
        if (user != null && user.getRole().getName().equals(Role.ROLE_OWNER.getName())) {
            bookingService.unsettle(hostel, number);
            return "redirect:/hostels/renters/" + id;
        } else {
            throw new RuntimeException("Role is incorrect");
        }
    }

    @GetMapping("/renters/{id}")
    public String renters(@PathVariable Integer id, Model model, Principal principal) {
        System.out.println(id);
        Hostel hostel = hostelService.getById(id);
        if (principal == null) throw new RuntimeException("Not logged");
        if (hostel == null) throw new RuntimeException("Not exists");
        System.out.println(hostel.getName());
        List<Booking> renters = bookingService.getByHostel(hostel);
        model.addAttribute("renters", renters);
        return "hostel/renters";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("hostel", new Hostel());
        return "hostel/new-hostel";
    }

    @PostMapping("/new")
    @SneakyThrows
    public String save(@ModelAttribute Hostel hostel, @RequestParam("images") MultipartFile[] images, Authentication authentication) {
        hostel.setOwner(userService.getByPhone(authentication.getName()));

        List<Image> imageList = new ArrayList<>();
        for (MultipartFile file : images) {
            if (file.getSize() > 0 && file.getSize() <= 4194304) { // check file size
                byte[] bytes = file.getBytes();
                String fileName = file.getOriginalFilename();
                String fileType = file.getContentType();
                Image image = Image.builder().name(fileName).data(bytes).type(fileType).build();
                imageList.add(image);
            }
        }

        hostel.setImageList(imageList);
//        log.info(mapper.writeValueAsString(hostel));
        hostel.setCreatedTimestamp(System.currentTimeMillis());
        hostel.setUpdatedTimestamp(System.currentTimeMillis());
        hostel.setIsApproved(false);
        hostelService.save(hostel);
        return "redirect:/";
    }
}

