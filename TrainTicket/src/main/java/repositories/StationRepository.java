package repositories;

import models.ScheduleModel;
import models.StationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface StationRepository extends JpaRepository <StationModel, Integer> {

    /** Метод получения станции по ИД
     *
     * @param stationId - текущая станция
     * @return - объект станции
     */
    @Query ("select s from StationModel s where s.stationId = ?1")
    StationModel getStationModelByStationId (String stationId);

    @Modifying
    @Query (value = "insert into StationModel (stationId, name) values (:stationId, :name)", nativeQuery = true)
    void insertStation (@Param("stationId") UUID stationId, @Param("name") String name);

    @Modifying
    @Query ("update StationModel s SET s.name = ?1 where s.stationId = ?2")
    void updateStationById (String name, Integer stationId);

//    @Modifying
//    @Query("update Employees e set e.firstName = ?1 where e.employeeId = ?2")
//    int setFirstnameFor(String firstName, String employeeId);

//    @Modifying
//    @Query(
//            value =
//                    "insert into Users (name, age, email, status) values (:name, :age, :email, :status)",
//            nativeQuery = true)
//    void insertUser(@Param("name") String name, @Param("age") Integer age,
//                    @Param("status") Integer status, @Param("email") String email);
}
