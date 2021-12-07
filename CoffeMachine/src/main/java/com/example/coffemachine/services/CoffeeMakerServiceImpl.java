package com.example.coffemachine.services;

import com.example.coffemachine.exceptions.CoffeeMakerException;
import com.example.coffemachine.models.CoffeeMakerModel;
import com.example.coffemachine.repositories.CoffeeMakerRepository;
import com.example.coffemachine.repositories.DbConnector;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;

@Service
@Slf4j
public class CoffeeMakerServiceImpl implements CoffeeMakerService
{
    private static final Logger LOG = LoggerFactory.getLogger(CoffeeMakerServiceImpl.class);
    private static final float MIN_LEVEL_OF_WATER = 200;

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
                if (!coffeeMakerModel.getUserId().toString().isBlank())
                {
                    LOG.info("Turn on the coffee maker with the following information - {}", coffeeMakerModel);
                    if (!coffeeMakerModel.isElectricPowerSupply() || coffeeMakerModel.isOnRepair())
                    {
                        throw new CoffeeMakerException("You have no power or coffee maker is on repair");
                    }
                    if (coffeeMakerModel.getCurrentLevelOfWater() < MIN_LEVEL_OF_WATER)
                    {
                        throw new CoffeeMakerException("Not enough water for a cup of coffee");
                    }
                    if (coffeeMakerModel.getCoffeeType() == CoffeeMakerModel.CoffeeType.UNDEFINED)
                    {
                        throw new CoffeeMakerException("you didn't choose the type of coffee");
                    }
                    var id = coffeeMakerRepository.addTurnOn(coffeeMakerModel, conn);
                    conn.commit();
                    LOG.info("The activation data was successfully recorded- {}, with id - {}", coffeeMakerModel, id);
                    return id;
                } else
                {
                    throw new CoffeeMakerException("UserId can not be null");
                }
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
        try (var conn = _dbConn.getSession())
        {
            try
            {
                LOG.info("Turn off the coffee maker with the following information - {}, id - {}",
                        coffeeMakerModel, coffeeMakerId);
                if (coffeeMakerModel.isElectricPowerSupply())
                {
                    throw new CoffeeMakerException("The coffee maker is already on");
                }
                if (coffeeMakerModel.isOnRepair())
                {
                    throw new CoffeeMakerException("The coffee maker just broke ," +
                            " please take it to the service center");
                }
                var currentWaterInDb = coffeeMakerRepository.currentLevelOfCoffee(coffeeMakerId, conn);
                if (currentWaterInDb < 0)
                {
                    throw new CoffeeMakerException("Add water to coffee maker");
                }
                currentWaterInDb -= MIN_LEVEL_OF_WATER;
                coffeeMakerModel.setCurrentLevelOfWater(currentWaterInDb);
                coffeeMakerRepository.addTurnOff(coffeeMakerModel, coffeeMakerId, conn);
                conn.commit();
                LOG.info("Record was sucсessfully - {}, with id - {}", coffeeMakerModel, coffeeMakerId);
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
    public List<CoffeeMakerModel> getAllWorksCoffeeMakerByUserId(UUID userId) throws Exception
    {
        try (var conn = _dbConn.getSession())
        {
            try
            {
                if (userId.toString().isBlank())
                {
                    throw new CoffeeMakerException("UserId can not be null");
                }
                LOG.info("Start to got List<CoffeeMakerModel> by userId - {}", userId);
                var listOfCoffeeModels = coffeeMakerRepository.getAllWorksCoffeeMakerByUserId(userId, conn);
                conn.commit();
                return listOfCoffeeModels;
            } catch (Exception ex)
            {
                conn.rollback();
                throw ex;
            }
        }

    }
}
