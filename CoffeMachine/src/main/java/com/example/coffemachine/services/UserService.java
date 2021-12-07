package com.example.coffemachine.services;

import com.example.coffemachine.models.CoffeeMakerModel;
import com.example.coffemachine.models.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService
{
    /**
     * Метод создания нового пользователя
     *
     * @return - Ид юзера
     * @throws Exception - выбрасываемое исключение
     */
    UUID addUser () throws  Exception;
}
