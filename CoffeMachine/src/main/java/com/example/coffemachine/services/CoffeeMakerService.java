package com.example.coffemachine.services;

import com.example.coffemachine.models.CoffeeMakerModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface CoffeeMakerService {

    /**
     * Метод включения кофеварки
     *
     * @param coffeeMakerModel - создание включения кофеварки
//     * @return - {@link CoffeeMakerModel}
     * @throws Exception - выбрасываемое исключение
     */
    void addTurnOn(CoffeeMakerModel coffeeMakerModel) throws Exception;

    /**
     * Метод изменения (завершения) работы кофеварки после включения
     * Заполняются все поля в этой итерации кофеварки под уникальным ИД
     *
     * @param coffeeMakerModel - дополнительные данные после завершения работы кофеварки
     * @param coffeeMakerId   - Ид итерации работы кофеварки, после включения
     * @throws Exception - выбрасываемое исключение
     */
    void addTurnOff(CoffeeMakerModel coffeeMakerModel, UUID coffeeMakerId) throws Exception;
}
