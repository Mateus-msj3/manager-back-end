package com.code.managerbackend.service.implementation;

import com.code.managerbackend.dto.OfficeDTO;
import com.code.managerbackend.dto.SectorDTO;
import com.code.managerbackend.exception.ObjectNotFoundException;
import com.code.managerbackend.exception.ResourceNotFoundException;
import com.code.managerbackend.exception.RuleBusinessException;
import com.code.managerbackend.model.Office;
import com.code.managerbackend.model.Sector;
import com.code.managerbackend.repository.SectorRepository;
import com.code.managerbackend.service.SectorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SectorServiceImpl implements SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<SectorDTO> listAll() {
        List<Sector> sectors = sectorRepository.findAll();
        return sectors.stream()
                .map(sector -> modelMapper.map(sector, SectorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public SectorDTO listById(Long id) {
        Optional<Sector> sector = Optional.ofNullable(sectorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found sector with id = " + id)));

        SectorDTO sectorDTO = modelMapper.map(sector.get(), SectorDTO.class);

        return sectorDTO;
    }

    @Override
    public SectorDTO save(SectorDTO sectorDTO) {
        beforeSavingVerifyName(sectorDTO.getName());

        Sector sector = modelMapper.map(sectorDTO, Sector.class);
        for (Office office : sector.getOffices()) {
            office.setSector(sector);
        }
        sectorRepository.save(sector);
        return sectorDTO;
    }

    @Override
    public SectorDTO update(SectorDTO sectorDTO) {
        Optional<Sector> currentSector = Optional.ofNullable(sectorRepository.findById(sectorDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found office with id = " + sectorDTO.getId())));

        beforeSavingVerifyName(sectorDTO.getName());

        Sector sector = modelMapper.map(sectorDTO, Sector.class);

        for (Office office : sector.getOffices()) {
            office.setSector(sector);
            sector.setOffices(office.getSector().getOffices());
        }
        sectorRepository.save(sector);
        return sectorDTO;
    }

    @Override
    public void delete(Long id) {
        Optional<Sector> sector = Optional.ofNullable(sectorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found office with id = " + id)));

        sectorRepository.deleteById(id);
    }

    private void beforeSavingVerifyName(String name) {
        if (sectorRepository.existsByName(name)) {
            throw new RuleBusinessException("This name is already used by another sector.");
        }
    }
}
