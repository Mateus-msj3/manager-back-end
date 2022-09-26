package com.code.managerbackend.controller;

import com.code.managerbackend.dto.OfficeDTO;
import com.code.managerbackend.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offices")
public class OfficeController {

    @Autowired
    private OfficeService officeService;

    @GetMapping
    public List<OfficeDTO> listAll() {
        return officeService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfficeDTO> listById(@PathVariable Long id){
        OfficeDTO officeDTO = officeService.listById(id);
        return ResponseEntity.ok().body(officeDTO);
    }

    @PostMapping
    public ResponseEntity<OfficeDTO> save(@RequestBody OfficeDTO officeDTO) {
        officeDTO = officeService.save(officeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(officeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfficeDTO> update(@PathVariable Long id, @RequestBody OfficeDTO officeDTO) {
        officeDTO.setId(id);
        officeDTO = officeService.update(officeDTO);
        return ResponseEntity.ok().body(officeDTO);
    }

    @GetMapping("/idSector/{id}")
    public List<OfficeDTO> findOfficeByIdSector(@PathVariable Long id) {
        return officeService.findOfficeBySectorId(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        officeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
