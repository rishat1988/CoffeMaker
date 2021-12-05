package com.example.coffemachine.controllers;

import com.example.coffemachine.Dto.CoffeeMakerDto;
import com.example.coffemachine.models.CoffeeMakerModel;
import com.example.coffemachine.services.CoffeeMakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/switch")
public class CoffeeMakerController {

    private final CoffeeMakerService coffeeMakerService;

    @Autowired
    CoffeeMakerController(CoffeeMakerService coffeeMakerService) {
        this.coffeeMakerService = coffeeMakerService;
    }

    @PostMapping("on")
    public void turnOn(@RequestBody CoffeeMakerDto coffeeMakerDto) throws Exception {
         coffeeMakerService.addTurnOn(CoffeeMakerModel.fromDto(coffeeMakerDto));
    }

    @PutMapping("off/{coffeeMakerId}")
    public void turnOff(@PathVariable("coffeeMakerId") UUID coffeeMakerId,
                        @RequestBody CoffeeMakerDto coffeeMakerDto) throws Exception {
        coffeeMakerService.addTurnOff(CoffeeMakerModel.fromDto(coffeeMakerDto), coffeeMakerId);
    }
}
