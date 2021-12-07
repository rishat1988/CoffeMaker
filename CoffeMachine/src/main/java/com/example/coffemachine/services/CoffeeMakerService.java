package com.example.coffemachine.services;

import com.example.coffemachine.models.CoffeeMakerModel;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

@Service
public interface CoffeeMakerService {

    /**
     * Метод включения кофеварки
     *
     * @param coffeeMakerModel - создание включения кофеварки
     * @throws Exception - выбрасываемое исключение
     * @return - ид работы кофеварки
     */
    UUID addTurnOn(CoffeeMakerModel coffeeMakerModel) throws Exception;

    /**
     * Метод изменения (завершения) работы кофеварки после включения
     * Заполняются все поля в этой итерации кофеварки под уникальным ИД
     *
     * @param coffeeMakerModel - дополнительные данные после завершения работы кофеварки
     * @param coffeeMakerId   - Ид итерации работы кофеварки, после включения
     * @throws Exception - выбрасываемое исключение
     */
    void addTurnOff(CoffeeMakerModel coffeeMakerModel, UUID coffeeMakerId) throws Exception;

    /**
     * Метод получения всех итераций по работе кофеварки
     *
     * @param coffeeMakerId - ИД коферварки
     * @return - возвращает список всех итераций
     * @throws Exception - выбрасываемое исключение
     */
    List<CoffeeMakerModel> getAllWorksCoffeeMakerByUserId(UUID coffeeMakerId) throws Exception;

}
