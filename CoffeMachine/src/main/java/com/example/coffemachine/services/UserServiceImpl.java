package com.example.coffemachine.services;

import com.example.coffemachine.models.CoffeeMakerModel;
import com.example.coffemachine.models.UserModel;
import com.example.coffemachine.repositories.DbConnector;
import com.example.coffemachine.repositories.UserRepository;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService
{
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final DbConnector<Connection> _dbConn;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, DbConnector<Connection> dbConn)
    {
        this.userRepository = userRepository;
        _dbConn = dbConn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID addUser() throws Exception
    {
        try (var conn = _dbConn.getSession())
        {
            try
            {
                LOG.info("Start to add new user");
                var id = userRepository.addUser(UUID.randomUUID(), conn);
                LOG.info("Succesfully was added user in database");
                conn.commit();
                return id;
            } catch (Exception ex)
            {
                conn.rollback();
                throw ex;
            }
        }
    }
}
