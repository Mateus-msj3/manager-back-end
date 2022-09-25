package com.code.managerbackend.repository;

import com.code.managerbackend.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface SectorRepository extends JpaRepository<Sector, Long> {

    boolean existsByName(String name);

    @Query(value = "select sector from Sector sector inner join sector.offices officie on sector.id = officie.sector.id " +
            "where (:idOfficie is null or officie.id = :idOfficie) " +
            "and(:name is null or lower(sector.name) like concat('%', lower(:name), '%')) " +
            "and (:situation is null or sector.situation = :situation)")
    List<Sector> filterSector(@Param("idOfficie") Long idOfficie,
                              @Param("name") String name,
                              @Param("situation") Boolean situation);

}
