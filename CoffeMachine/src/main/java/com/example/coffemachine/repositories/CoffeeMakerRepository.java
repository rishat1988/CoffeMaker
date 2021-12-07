package com.example.coffemachine.repositories;

import com.example.coffemachine.models.CoffeeMakerModel;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

@Service
public interface CoffeeMakerRepository
{

    /**
     * Метод включения кофеварки
     *
     * @param coffeeMakerModel - создание включения кофеварки
     * @param conn             - соединение с БД
     * @return - id работы кофеварки
     * @throws Exception - выбрасываемое исключение
     */
    UUID addTurnOn(CoffeeMakerModel coffeeMakerModel, Connection conn) throws Exception;

    /**
     * Метод изменения (завершения) работы кофеварки после включения
     * Заполняются все поля в этой итерации кофеварки под уникальным ИД
     *
     * @param coffeeMakerModel - дополнительные данные после завершения работы кофеварки
     * @param coffeeMakerId    - Ид итерации работы кофеварки, после включения
     * @param conn             - соединение с БД
     * @throws Exception - выбрасываемое исключение
     */
    void addTurnOff(CoffeeMakerModel coffeeMakerModel, UUID coffeeMakerId, Connection conn) throws Exception;

    /**
     * Метод для получения текущего значения воды в кофеварке
     *
     * @param coffeeMakerId - Ид итерации работы кофеварки, после включения
     * @param conn          - соединение с БД
     * @return - уровень воды в кофеварки из БД
     * @throws Exception - выбрасываемое исключение
     */
    public float currentLevelOfCoffee(UUID coffeeMakerId, Connection conn) throws Exception;

    /**
     * Метод получения всех итераций по работе кофеварки
     *
     * @param coffeeMakerId - ИД коферварки
     * @param conn - подключение к БД
     * @return - возвращает список всех итераций
     * @throws Exception - выбрасываемое исключение
     */
    List<CoffeeMakerModel> getAllWorksCoffeeMakerByUserId(UUID coffeeMakerId, Connection conn) throws Exception;

}
