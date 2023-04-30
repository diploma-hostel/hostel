package booking.hostel.repository;

import booking.hostel.entity.Booking;
import booking.hostel.entity.Hostel;
import booking.hostel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByHostel(Hostel hostel);
    List<Booking> findAllByUser(User user);
    boolean existsByHostelAndUser(Hostel hostel, User user);
    List<Booking> findAllByHostelAndNumber(Hostel hostel, Integer number);
}

