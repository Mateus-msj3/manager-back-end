package com.code.managerbackend.service;

import com.code.managerbackend.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> listAll();

    EmployeeDTO listById(Long id);

    List<EmployeeDTO> listByName(String name);

    EmployeeDTO listByCpf(String cpf);

    EmployeeDTO save(EmployeeDTO employeeDTO);

    EmployeeDTO update(EmployeeDTO employeeDTO);

    void delete(Long id);

}
