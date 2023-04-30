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

    public List<Booking> getByHostel(Hostel hostel) {
        return bookingRepository.findAllByHostel(hostel);
    }
    public List<Booking> getByUser(User user) {
        return bookingRepository.findAllByUser(user);
    }
    public boolean existsByHostelAndUser(Hostel hostel, User user) {
        return bookingRepository.existsByHostelAndUser(hostel, user);
    }

    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }
    public void unsettle(Hostel hostel, int number) {
        List<Booking> all = bookingRepository.findAllByHostelAndNumber(hostel, number);
        if (all != null)
            bookingRepository.deleteAll(all);
    }
}
