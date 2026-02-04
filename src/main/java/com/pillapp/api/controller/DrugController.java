package com.pillapp.api.controller;

import com.pillapp.api.dto.creation.NewPlannedDrugActionDTO;
import com.pillapp.api.dto.responses.data.DrugPlanDTO;
import com.pillapp.api.dto.creation.NewDrugPlanDTO;
import com.pillapp.api.dto.responses.data.PlannedDrugActionDTO;
import com.pillapp.api.service.DrugPlanService;
import com.pillapp.api.service.PlannedDrugActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class DrugController {

    private final DrugPlanService drugPlanService;
    private final PlannedDrugActionService plannedDrugActionService;
    @Autowired
    public DrugController(DrugPlanService drugPlanService, PlannedDrugActionService plannedDrugActionService){
        this.drugPlanService = drugPlanService;
        this.plannedDrugActionService = plannedDrugActionService;
    }

    @PostMapping("drug")
    @ResponseStatus(HttpStatus.CREATED)
    public DrugPlanDTO register(@RequestBody @Valid NewDrugPlanDTO drug){
        return drugPlanService.create(drug);
    }

    @PostMapping("drug/action")
    @ResponseStatus(HttpStatus.CREATED)
    public PlannedDrugActionDTO registerAction(@RequestBody @Valid NewPlannedDrugActionDTO action) {
        return plannedDrugActionService.create(action);
    }

}
