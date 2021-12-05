package repositories;

import models.ScheduleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ScheduleRepository extends JpaRepository<ScheduleModel, Integer> {

    @Query("Select s from ScheduleModel s join " +
            "StationModel sm on s.station.stationId = sm.stationId where s.station.stationId = ?1")
    Set<ScheduleModel> getScheduleModelsByStationStationId(String StationId);
}
