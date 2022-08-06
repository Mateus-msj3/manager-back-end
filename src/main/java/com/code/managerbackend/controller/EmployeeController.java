package com.code.managerbackend.controller;

import com.code.managerbackend.dto.EmployeeDTO;
import com.code.managerbackend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDTO> listAll() {
        return employeeService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> listById(@PathVariable Long id){
        EmployeeDTO employeeDTO = employeeService.listById(id);
        return ResponseEntity.ok().body(employeeDTO);
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> save(@RequestBody @Valid EmployeeDTO employeeDTO) {
        employeeDTO = employeeService.save(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        employeeDTO.setId(id);
        employeeDTO = employeeService.update(employeeDTO);
        return ResponseEntity.ok().body(employeeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
