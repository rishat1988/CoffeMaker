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
public class CoffeeMakerServiceImpl implements CoffeeMakerService {

    private static final Logger LOG = LoggerFactory.getLogger(CoffeeMakerServiceImpl.class);

    private final CoffeeMakerRepository coffeeMakerRepository;
    private final DbConnector<Connection> _dbConn;

    @Autowired
    public CoffeeMakerServiceImpl(CoffeeMakerRepository coffeeMakerRepository,
                                  DbConnector<Connection> dbConn) {
        this.coffeeMakerRepository = coffeeMakerRepository;
        _dbConn = dbConn;
    }

    @Override
    public void addTurnOn(CoffeeMakerModel coffeeMakerModel) throws Exception {

        try (Connection conn = _dbConn.getSession()) {
            try {
                LOG.info("Turn on the coffee maker with the following information - {}", coffeeMakerModel);
                 coffeeMakerRepository.addTurnOn(coffeeMakerModel, conn);
                conn.commit();
                LOG.info("the activation data was successfully recorded- {}", coffeeMakerModel);
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            }
        }
    }

    @Override
    public void addTurnOff(CoffeeMakerModel coffeeMakerModel, UUID CoffeeMakerId) throws Exception {

    }
}
