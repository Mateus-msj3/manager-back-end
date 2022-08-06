package com.code.managerbackend.repository;

import com.code.managerbackend.dto.EmployeeDTO;
import com.code.managerbackend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByCpf(String cpf);

    List<EmployeeDTO> findByNameOrderByName(String name);

    EmployeeDTO findByCpf(String cpf);

    @Query(name = "SELECT e FROM Employee e WHERE e.active = ?1")
    List<EmployeeDTO> findAllStatusEmployee(boolean status);

}
