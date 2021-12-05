package models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table (name = "schedule")
public class ScheduleModel {

    @Id
    @GeneratedValue
    @Column (name = "scheduleId")
    private int scheduleId;

    @ManyToOne
    @JoinColumn(name = "stationId")
    StationModel station;

    @ManyToMany (mappedBy = "schedules")
    private Set <TrainModel> trains;


}
