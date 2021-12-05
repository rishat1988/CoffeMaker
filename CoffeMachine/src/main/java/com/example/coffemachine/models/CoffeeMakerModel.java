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
@Getter
@Accessors(chain = true)
public class CoffeeMakerModel {

    private UUID coffeeMakerId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm'Z'")
    LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm'Z'")
    private LocalDateTime endTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm'Z'")
    private LocalDateTime diffTime;

    private boolean electricPowerSupply;

    private boolean onRepair;

    public static CoffeeMakerModel fromDto(CoffeeMakerDto coffeeMakerDto) {
        var coffeeMakerModel = new CoffeeMakerModel()
                .setCoffeeMakerId(coffeeMakerDto.getCoffeeMakerId())
                .setStartTime(coffeeMakerDto.getStartTime())
                .setElectricPowerSupply(coffeeMakerDto.isElectricPowerSupply())
                .setOnRepair(coffeeMakerDto.isOnRepair());

        return coffeeMakerModel;
    }
}
