package com.example.coffemachine.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeMakerDto {

    private UUID coffeeMakerId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm'Z'")
    LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm'Z'")
    private LocalDateTime endTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm'Z'")
    private LocalDateTime diffTime;

    private boolean electricPowerSupply;

    private boolean onRepair;

    float currentLevelOfWater;

    int coffeeNumber;
}
