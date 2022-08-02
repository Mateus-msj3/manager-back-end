package com.code.managerbackend.repository;

import com.code.managerbackend.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface OfficeRepository extends JpaRepository<Office, Long> {

    boolean existsByName(String name);
}
