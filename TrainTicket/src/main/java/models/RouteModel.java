package models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "route")
@Data
public class RouteModel {

    @Id
    @GeneratedValue  (generator = "UUID")
    @GenericGenerator(name = "UUID", strategy =  "org.hibernate.id.UUIDGenerator")
    @Column (name = "routeId", updatable = false, nullable = false,columnDefinition = "UUID")
    private UUID routeId;

    @Column (name = "departure_time")
    private Timestamp departureTime;

    @Column (name = "arrival_time")
    private Timestamp arrivalTime;

    @ManyToOne
    @JoinColumn (name = "trainId")
    private TrainModel train;

    @ManyToOne
    @JoinColumn(name = "stationId")
    StationModel stationDeparture;

    @ManyToOne
    @JoinColumn(name = "stationId")
    StationModel stationArrival;
}
