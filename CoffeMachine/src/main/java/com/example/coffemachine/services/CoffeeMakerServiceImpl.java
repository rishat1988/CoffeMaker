package com.example.coffemachine.services;

import com.example.coffemachine.models.CoffeeMakerModel;
import com.example.coffemachine.repositories.CoffeeMakerRepository;
import com.example.coffemachine.repositories.DbConnector;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.UUID;

import org.slf4j.Logger;

@Service
@Slf4j
public class CoffeeMakerServiceImpl implements CoffeeMakerService
{
    private static final float MIN_LEVEL_OF_WATER = 200;
    private static final Logger LOG = LoggerFactory.getLogger(CoffeeMakerServiceImpl.class);

    private final CoffeeMakerRepository coffeeMakerRepository;
    private final DbConnector<Connection> _dbConn;

    @Autowired
    public CoffeeMakerServiceImpl(CoffeeMakerRepository coffeeMakerRepository,
                                  DbConnector<Connection> dbConn)
    {
        this.coffeeMakerRepository = coffeeMakerRepository;
        _dbConn = dbConn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID addTurnOn(CoffeeMakerModel coffeeMakerModel) throws Exception
    {

        try (var conn = _dbConn.getSession())
        {
            try
            {
                LOG.info("Turn on the coffee maker with the following information - {}", coffeeMakerModel);
                if (!coffeeMakerModel.isElectricPowerSupply() || coffeeMakerModel.isOnRepair())
                {
                    throw new Exception("You have no power or coffee maker is on repair");
                }
                if (coffeeMakerModel.getCurrentLevelOfWater() < MIN_LEVEL_OF_WATER)
                {
                    throw new Exception("Not enough water for a cup of coffee");
                }
                if (coffeeMakerModel.getCoffeeType() == CoffeeMakerModel.CoffeeType.UNDEFINED)
                {
                    throw new Exception("you didn't choose the type of coffee");
                }
                var id = coffeeMakerRepository.addTurnOn(coffeeMakerModel, conn);
                conn.commit();
                LOG.info("The activation data was successfully recorded- {}б with id - {}", coffeeMakerModel, id);
                return id;
            } catch (Exception ex)
            {
                conn.rollback();
                throw ex;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTurnOff(CoffeeMakerModel coffeeMakerModel, UUID coffeeMakerId) throws Exception
    {
        try (Connection conn = _dbConn.getSession())
        {
            try
            {
                LOG.info("Turn off the coffee maker with the following information - {}, id - {}",
                        coffeeMakerModel, coffeeMakerId);
                if (coffeeMakerModel.isElectricPowerSupply())
                {
                    throw new Exception("The coffee maker is already on");
                }
                if (coffeeMakerModel.isOnRepair())
                {
                    throw new Exception("The coffee maker just broke ," +
                            " please take it to the service center");
                }
                var currentWaterInDb = coffeeMakerRepository.currentLevelOfCoffee(coffeeMakerId, conn);
                currentWaterInDb -= MIN_LEVEL_OF_WATER;
                coffeeMakerModel.setCurrentLevelOfWater(currentWaterInDb);
                coffeeMakerRepository.addTurnOff(coffeeMakerModel, coffeeMakerId, conn);
                conn.commit();
                LOG.info("Record was sucсessfully - {}, id - {}", coffeeMakerModel, coffeeMakerId);
            } catch (Exception ex)
            {
                conn.rollback();
                throw ex;
            }
        }
    }
}
