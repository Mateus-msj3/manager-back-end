package com.code.managerbackend.service;

import com.code.managerbackend.dto.OfficeDTO;
import com.code.managerbackend.model.Office;

import java.util.List;

public interface OfficeService {

    List<OfficeDTO> listAll();

    OfficeDTO listById(Long id);

    OfficeDTO save(OfficeDTO officeDTO);

    OfficeDTO update(OfficeDTO officeDTO);

    List<OfficeDTO> findOfficeBySectorId(Long id);

    void delete(Long id);

    void existsByName(Office office);
}
