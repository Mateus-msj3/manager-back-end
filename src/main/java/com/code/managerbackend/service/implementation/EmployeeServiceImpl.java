package com.code.managerbackend.service.implementation;

import com.code.managerbackend.dto.EmployeeDTO;
import com.code.managerbackend.exception.ResourceNotFoundException;
import com.code.managerbackend.exception.RuleBusinessException;
import com.code.managerbackend.model.Employee;
import com.code.managerbackend.repository.EmployeeRepository;
import com.code.managerbackend.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<EmployeeDTO> listAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO listById(Long id) {
        Optional<Employee> employee = Optional.ofNullable(employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found employee with id = " + id)));

        EmployeeDTO employeeDTO = modelMapper.map(employee.get(), EmployeeDTO.class);

        return employeeDTO;
    }

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {

        beforeSavingCheckEmployeeCpf(employeeDTO.getCpf());
        Employee employee = modelMapper.map(employeeDTO, Employee.class);

        employeeRepository.save(employee);

        return employeeDTO;
    }

    @Override
    public EmployeeDTO update(EmployeeDTO employeeDTO) {
        Optional<Employee> currentEmployee = Optional.ofNullable(employeeRepository.findById(employeeDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found employee with id = " + employeeDTO.getId())));

        beforeUpdatingCheckEmployeeCpf(employeeDTO, currentEmployee);

        Employee employee = modelMapper.map(employeeDTO, Employee.class);

        employeeRepository.save(employee);
        return employeeDTO;
    }

    @Override
    public void delete(Long id) {
        Optional<Employee> employee = Optional.ofNullable(employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found employee with id = " + id)));

        employeeRepository.deleteById(id);
    }

    private void beforeSavingCheckEmployeeCpf(String cpf) {
        if (employeeRepository.existsByCpf(cpf)) {
            throw new RuleBusinessException("This cpf is already used by another employee.");
        }
    }

    private void beforeUpdatingCheckEmployeeCpf(EmployeeDTO employeeDTO, Optional<Employee> optionalEmployee) {
        if (!employeeDTO.getCpf().equals(optionalEmployee.get().getCpf())) {
            beforeSavingCheckEmployeeCpf(employeeDTO.getCpf());
        }
    }
}
