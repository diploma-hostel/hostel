package booking.hostel.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToOne
    Hostel hostel;
    @ManyToOne
    User user;
    Long startTimestamp;
    Long endTimestamp;
    boolean isApproved;
}
