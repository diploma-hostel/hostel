package booking.hostel.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
public class Hostel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToOne
    User owner;
    String name;
    String address;
    Integer numberOfBeds;
    Double pricePerDay;
    Double pricePerMonth;
    Double discount;
    @Column(columnDefinition = "TEXT")
    String description;
    String sketchfab_link;
    String status;
    Long createdTimestamp;
    Long updatedTimestamp;
    @OneToMany
    List<Image> imageList;
    Boolean isApproved;
}
