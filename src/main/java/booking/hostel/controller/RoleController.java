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
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id, Model model) {
        Role role = roleService.getById(id);
        model.addAttribute("role", role);
        return "role/role";
    }

    @GetMapping
    public String getAll(Model model) {
        List<Role> roles = roleService.getAll();
        model.addAttribute("roles", roles);
        return "role/roles";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("role", new Role());
        return "role/new-role";
    }

    @PostMapping
    public String save(Role role) {
        roleService.save(role);
        return "redirect:/roles";
    }
}

