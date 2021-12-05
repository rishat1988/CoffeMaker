package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "train")
public class TrainModel {

    @Id
    @GeneratedValue
    @Column(name = "trainId")
    private int trainId;

    @Column(name = "train_number")
    @NotBlank(message = "train number can not be null or ' ' ")
    private String trainNumber;

    @Column(name = "capacity")
    @Min(value = 1, message = "capacity of the train can not less than 1")
    private int capacity;

    @Column(name = "train_type")
    private String trainType;

    public enum TrainEnumType {
        UNDEFINED(0),
        HIGHSPEED(1),
        CARGO(2),
        PASSENGER(3);

        private final int type;

        TrainEnumType(int type) {
            this.type = type;
        }

        public static TrainEnumType fromInt(int type) {
            switch (type) {
                case (1):
                    return HIGHSPEED;
                case (2):
                    return CARGO;
                case (3):
                    return PASSENGER;
                default:
                    return UNDEFINED;
            }
        }

    }

//    @ManyToMany
//    @JoinTable(name = "train_schedule", joinColumns = @JoinColumn( name = "train_id", referencedColumnName = "trainId"),
//         inverseJoinColumns =   @JoinColumn (name = "schedule_id", referencedColumnName = "scheduleId"))
//    private Set<Schedule> schedules;

}
