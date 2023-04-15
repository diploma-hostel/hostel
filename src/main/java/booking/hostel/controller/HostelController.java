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

@Controller
@RequestMapping("/hostels")
@RequiredArgsConstructor
@Slf4j
public class HostelController {
    private final ObjectMapper mapper;
    private final HostelService hostelService;
    private final UserService userService;

    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id, Model model) {
        Hostel hostel = hostelService.getById(id);
        model.addAttribute("hostel", hostel);
        return "hostel/hostel";
    }

    @GetMapping
    public String getAll(Model model, Principal principal) {
        User owner = userService.getByPhone(principal.getName());
        List<Hostel> hostels = hostelService.getByOwner(owner);
        model.addAttribute("hostels", hostels);
        return "hostel/hostels";
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
        return "redirect:/hostels";
    }
}

