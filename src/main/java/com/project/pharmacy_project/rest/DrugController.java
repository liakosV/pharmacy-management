package com.project.pharmacy_project.rest;

import com.project.pharmacy_project.dto.DrugInsertDto;
import com.project.pharmacy_project.dto.DrugReadOnlyDto;
import com.project.pharmacy_project.service.DrugService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drugs")
@RequiredArgsConstructor
public class DrugController {

    private final DrugService drugService;

    @PostMapping
    public ResponseEntity<DrugReadOnlyDto> saveDrug(@RequestBody DrugInsertDto insertDto) {
        DrugReadOnlyDto drugReadOnlyDto = drugService.saveDrug(insertDto);

        return new ResponseEntity<>(drugReadOnlyDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void removeDrug(@PathVariable Long id) {
        drugService.removeDrug(id);
    }

    @GetMapping
    public ResponseEntity<List<DrugReadOnlyDto>> getAllDrugs() {
        List<DrugReadOnlyDto> drugList = drugService.getAllDrugs();
        return new ResponseEntity<>(drugList, HttpStatus.OK);
    }
}
