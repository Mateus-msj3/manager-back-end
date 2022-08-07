package com.code.managerbackend.repository;

import com.code.managerbackend.dto.EmployeeDTO;
import com.code.managerbackend.model.Employee;
import com.code.managerbackend.model.Office;
import com.code.managerbackend.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByCpf(String cpf);

    List<Employee> findByNameContainingIgnoreCase(String name);

    EmployeeDTO findByCpf(String cpf);

    @Query(name = "SELECT e FROM Employee e WHERE e.sector = ?1")
    List<Employee> findEmployeeBySector(Sector sector);

    @Query(name = "SELECT e FROM Employee e WHERE e.office = ?1")
    List<Employee> findEmployeeByOffice(Office office);

}
