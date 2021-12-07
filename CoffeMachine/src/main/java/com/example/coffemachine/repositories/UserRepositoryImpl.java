package com.example.coffemachine.repositories;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.UUID;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository
{
    private static final Logger LOG = LoggerFactory.getLogger(UserRepositoryImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID addUser(UUID id, Connection conn) throws Exception
    {
        LOG.info("Start to save new user in database ");

        try (var stmt = conn.prepareCall("INSERT INTO \"user\" (user_id) VALUES (?);"))
        {
            stmt.setObject(1, id);
            stmt.addBatch();
            stmt.executeBatch();
            LOG.info("Successfully was added user with id - {}", id);
            return id;
        }
    }
}