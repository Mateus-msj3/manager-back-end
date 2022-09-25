package com.code.managerbackend.service;

import com.code.managerbackend.dto.FilterSectorDTO;
import com.code.managerbackend.dto.SectorDTO;

import java.util.List;

public interface SectorService {

    List<SectorDTO> listAll();

    SectorDTO listById(Long id);

    SectorDTO save(SectorDTO sectorDTO);

    SectorDTO update(SectorDTO sectorDTO);

    List<SectorDTO> filterSector(FilterSectorDTO filterSectorDTO);

    void delete(Long id);
}
