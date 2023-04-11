package booking.hostel.service;

import booking.hostel.entity.*;
import booking.hostel.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User getByPhone(String phone) {
        return userRepository.findFirstByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found with phone: " + phone));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}

