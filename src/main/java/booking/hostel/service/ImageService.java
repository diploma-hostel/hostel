package booking.hostel.service;

import booking.hostel.entity.Image;
import booking.hostel.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public Image getById(Integer id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hostel not found with id: " + id));
    }
}