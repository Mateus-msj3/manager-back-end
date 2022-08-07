package com.code.managerbackend.service;

import com.code.managerbackend.dto.EmployeeDTO;
import com.code.managerbackend.model.Office;
import com.code.managerbackend.model.Sector;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> listAll();

    EmployeeDTO listById(Long id);

    List<EmployeeDTO> listByName(String name);

    EmployeeDTO listByCpf(String cpf);

    List<EmployeeDTO> listBySector(Sector sector);

    List<EmployeeDTO> listByOffice(Office office);

    EmployeeDTO save(EmployeeDTO employeeDTO);

    EmployeeDTO update(EmployeeDTO employeeDTO);

    void delete(Long id);

}
