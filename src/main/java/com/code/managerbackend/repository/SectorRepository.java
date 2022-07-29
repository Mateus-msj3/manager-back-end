package com.code.managerbackend.repository;

import com.code.managerbackend.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SectorRepository extends JpaRepository<Sector, Long> {
}
