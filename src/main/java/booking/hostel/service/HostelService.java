package booking.hostel.service;

import booking.hostel.entity.*;
import booking.hostel.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HostelService {

    private final HostelRepository hostelRepository;
    private final ImageRepository imageRepository;

    public Hostel getById(Integer id) {
        return hostelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hostel not found with id: " + id));
    }

    public List<Hostel> getAll() {
        return hostelRepository.findAll();
    }

    public Hostel save(Hostel hostel) {
        imageRepository.saveAllAndFlush(hostel.getImageList());
        return hostelRepository.save(hostel);
    }
}
