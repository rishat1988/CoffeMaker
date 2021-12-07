package com.example.coffemachine.repositories;

import com.example.coffemachine.models.CoffeeMakerModel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Repository
public class CoffeeMakerRepositoryImpl implements CoffeeMakerRepository
{
    private static final Logger LOG = LoggerFactory.getLogger(CoffeeMakerRepositoryImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID addTurnOn(CoffeeMakerModel coffeeMakerModel, Connection conn) throws Exception
    {

        LOG.info("After turning on the coffee maker, you need to record the initial data - {}", coffeeMakerModel);

        try (CallableStatement stmt = conn.prepareCall(" INSERT INTO coffee_maker(coffee_maker_id, start_time, " +
                "electric_power_supply, on_repair, current_level_of_water , coffee_type , userid)VALUES (? ,now(), ? , ? ,?, ?, ? );"))
        {
            stmt.setObject(1, coffeeMakerModel.getCoffeeMakerId());
            stmt.setBoolean(2, coffeeMakerModel.isElectricPowerSupply());
            stmt.setBoolean(3, coffeeMakerModel.isOnRepair());
            stmt.setFloat(4, coffeeMakerModel.getCurrentLevelOfWater());
            stmt.setInt(5, coffeeMakerModel.getCoffeeType().toInt());
            stmt.setObject(6, coffeeMakerModel.getUserId());
            stmt.addBatch();
            stmt.executeBatch();
            LOG.info("The activation data was successfully recorded - {}", coffeeMakerModel);
            return coffeeMakerModel.getCoffeeMakerId();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float currentLevelOfCoffee(UUID coffeeMakerId, Connection conn) throws Exception
    {
        LOG.info("Start to get currentLevelOfCoffee - {} ", coffeeMakerId);

        var levelWater = 0F;
        var sqlStmt = String.format(
                " SELECT current_level_of_water from coffee_maker " +
                        "where coffee_maker_id  ='%s';", coffeeMakerId);

        try (var pstmt = conn.prepareStatement(sqlStmt, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY))
        {
            var rs = pstmt.executeQuery();
            if (rs.first())
            {
                do
                {
                    levelWater = rs.getFloat("current_level_of_water");
                } while (rs.next());

                LOG.info("successfully got current_level_of_water- {}", levelWater);
            }
        }
        return levelWater;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTurnOff(CoffeeMakerModel coffeeMakerModel, UUID coffeeMakerId, Connection conn) throws Exception
    {
        LOG.info("Start on updating the recording of the coffee maker with data - {}, " +
                "with id - {}", coffeeMakerModel, coffeeMakerId);

        try (CallableStatement stmt = conn.prepareCall(" UPDATE coffee_maker SET " +
                " end_time = now(), electric_power_supply = ?, on_repair = ? , " +
                "current_level_of_water = ?" +
                "where coffee_maker_id  = ?;"))
        {
            stmt.setBoolean(1, coffeeMakerModel.isElectricPowerSupply());
            stmt.setBoolean(2, coffeeMakerModel.isOnRepair());
            stmt.setFloat(3, coffeeMakerModel.getCurrentLevelOfWater());
            stmt.setObject(4, coffeeMakerId);
            stmt.addBatch();
            stmt.executeBatch();

            LOG.info("The activation data was successfully recorded - {}", coffeeMakerModel);
        }
        try (CallableStatement stmt = conn.prepareCall(" UPDATE coffee_maker " +
                "SET  diff_time = (end_time - start_time)" +
                " where coffee_maker_id  = ?;"))
        {
            stmt.setObject(1, coffeeMakerId);
            stmt.addBatch();
            stmt.executeBatch();
        }
        LOG.info("The activation data was successfully recorded diff_time - {}", coffeeMakerModel);
    }

    /**
     * {@inheritDoc}
     */
    public List<CoffeeMakerModel> getAllWorksCoffeeMakerByUserId(UUID userId, Connection conn) throws Exception
    {
        LOG.info("Start to got AllWorkCoffeeMaker from Database by userId - {}", userId);

        List<CoffeeMakerModel> coffeeMakerModels = new ArrayList<>();
        var sql = String.format("SELECT * FROM coffee_maker cm JOIN \"user\" u on u.user_id = cm.userid " +
                "where user_id = '%s'", userId);

        try (var stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY))
        {
            var rs = stmt.executeQuery();
            while (rs.next())
            {
                CoffeeMakerModel coffeeMakerModel = new CoffeeMakerModel()
                        .setCoffeeMakerId((UUID) rs.getObject("coffee_maker_id"))
                        .setStartTime(Optional.ofNullable(rs.getTimestamp("start_time")).map(CoffeeMakerRepositoryImpl::getUtcLocalDateTime).orElse(null))
                        .setElectricPowerSupply(rs.getBoolean("electric_power_supply"))
                        .setOnRepair(rs.getBoolean("on_repair"))
                        .setEndTime(Optional.ofNullable(rs.getTimestamp("end_time")).map(CoffeeMakerRepositoryImpl::getUtcLocalDateTime).orElse(null))
                        .setDiffTime((org.postgresql.util.PGInterval) rs.getObject("diff_time"))
                        .setCurrentLevelOfWater(rs.getFloat("current_level_of_water"))
                        .setCoffeeType(CoffeeMakerModel.CoffeeType.fromInt(rs.getInt("coffee_type")))
                        .setUserId((UUID) rs.getObject("userId"));
                coffeeMakerModels.add(coffeeMakerModel);
            }
        }
        LOG.info("Successfully got List<CoffeeMakerModel> - {}", coffeeMakerModels);
        return coffeeMakerModels;
    }

    private static LocalDateTime getUtcLocalDateTime(Timestamp ts)
    {
        return ts.toInstant().atZone(ZoneOffset.UTC).toLocalDateTime();
    }
}
