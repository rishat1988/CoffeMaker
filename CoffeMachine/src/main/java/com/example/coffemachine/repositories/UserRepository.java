package com.example.coffemachine.repositories;

import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.util.UUID;


@Repository
public interface UserRepository
{
    /**
     * @param conn - подключение к БД
     * @return - Ид юзера
     * @throws Exception - выбрасываемое исключение
     */
    UUID addUser (UUID id, Connection conn) throws  Exception;

}
