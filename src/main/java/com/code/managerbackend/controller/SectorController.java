package com.code.managerbackend.controller;

import com.code.managerbackend.dto.FilterSectorDTO;
import com.code.managerbackend.dto.SectorDTO;
import com.code.managerbackend.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sectors")
public class SectorController {

    @Autowired
    private SectorService sectorService;

    @GetMapping
    public List<SectorDTO> listAll() {
        return sectorService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectorDTO> listById(@PathVariable Long id){
        SectorDTO sectorDTO = sectorService.listById(id);
        return ResponseEntity.ok().body(sectorDTO);
    }

    @PostMapping
    public ResponseEntity<SectorDTO> save(@RequestBody @Valid SectorDTO sectorDTO) {
        sectorDTO = sectorService.save(sectorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(sectorDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SectorDTO> update(@PathVariable Long id, @RequestBody SectorDTO sectorDTO) {
        sectorDTO.setId(id);
        sectorDTO = sectorService.update(sectorDTO);
        return ResponseEntity.ok().body(sectorDTO);
    }

    @PostMapping("/filterSector")
    public List<SectorDTO> filterSector(@RequestBody FilterSectorDTO filterSectorDTO) {
        return sectorService.filterSector(filterSectorDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        sectorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
