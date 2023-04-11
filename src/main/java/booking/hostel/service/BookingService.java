package booking.hostel.service;

import booking.hostel.entity.*;
import booking.hostel.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking getById(Integer id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
    }

    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }
}
