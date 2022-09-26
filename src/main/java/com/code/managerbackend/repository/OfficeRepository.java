package com.code.managerbackend.repository;

import com.code.managerbackend.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OfficeRepository extends JpaRepository<Office, Long> {

    boolean existsByName(String name);

   // @Query(name = "SELECT o FROM Office o WHERE o.sector = :idSector")
    List<Office> findOfficeBySectorId(Long idSector);
}

