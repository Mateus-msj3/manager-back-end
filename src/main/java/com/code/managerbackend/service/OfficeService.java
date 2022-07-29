package com.code.managerbackend.service;

import com.code.managerbackend.dto.OfficeDTO;

import java.util.List;

public interface OfficeService {

    List<OfficeDTO> listAll();

    OfficeDTO listById(Long id);

    OfficeDTO save(OfficeDTO officeDTO);

    OfficeDTO update(OfficeDTO officeDTO);

    void delete(Long id);
}