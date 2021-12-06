package com.example.coffemachine.models;

import com.example.coffemachine.Dto.CoffeeMakerDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CoffeeMakerModel
{
    private UUID coffeeMakerId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm'Z'")
    LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm'Z'")
    private LocalDateTime endTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm'Z'")
    private LocalDateTime diffTime;

    private boolean electricPowerSupply;

    private boolean onRepair;

    private float currentLevelOfWater;

    private CoffeeType coffeeType;

    public enum CoffeeType
    {
        ESPRESSO(1), CAPPUCCINO(2), LATTE(3), UNDEFINED(0);

        private final int number;

        CoffeeType(int number)
        {
            this.number = number;
        }

        public static CoffeeType fromInt(int number)
        {
            switch (number)
            {
                case 1:
                    return ESPRESSO;
                case 2:
                    return CAPPUCCINO;
                case 3:
                    return LATTE;
                default:
                    return UNDEFINED;
            }
        }

        public int toInt()
        {
            return number;
        }
    }

    public static CoffeeMakerModel fromDto(CoffeeMakerDto coffeeMakerDto)
    {
        var coffeeMakerModel = new CoffeeMakerModel()
                .setCoffeeMakerId(UUID.randomUUID())
                .setStartTime(coffeeMakerDto.getStartTime())
                .setElectricPowerSupply(coffeeMakerDto.isElectricPowerSupply())
                .setOnRepair(coffeeMakerDto.isOnRepair())
                .setCurrentLevelOfWater(coffeeMakerDto.getCurrentLevelOfWater())
                .setCoffeeType(CoffeeType.fromInt(coffeeMakerDto.getCoffeeNumber()));

        return coffeeMakerModel;
    }
}
