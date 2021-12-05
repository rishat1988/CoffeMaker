package com.example.coffemachine.repositories;

import com.example.coffemachine.models.CoffeeMakerModel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.UUID;

@Slf4j
@Repository
public class CoffeeMakerRepositoryImpl implements CoffeeMakerRepository {

    private static final Logger LOG = LoggerFactory.getLogger(CoffeeMakerRepositoryImpl.class);

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public void addTurnOn(CoffeeMakerModel coffeeMakerModel, Connection conn) throws Exception {

        LOG.info("After turning on the coffee maker, you need to record the initial data - {}", coffeeMakerModel);

        try (CallableStatement stmt = conn.prepareCall(" INSERT INTO coffe_maker(coffe_maker_id, start_time, " +
                "electric_power_supply, on_repair )VALUES (uuid_generate_v4() ,now(), ? , ? );")) {
            stmt.setBoolean(1, coffeeMakerModel.isElectricPowerSupply());
            stmt.setBoolean(2, coffeeMakerModel.isOnRepair());
//            stmt.addBatch();
            stmt.executeBatch();
            LOG.info("The activation data was successfully recorded - {}", coffeeMakerModel);
        }
    }

    @Override
    public void addTurnOff(CoffeeMakerModel coffeeMakerModel, UUID coffeeMakerId, Connection conn) throws Exception {

    }
}
