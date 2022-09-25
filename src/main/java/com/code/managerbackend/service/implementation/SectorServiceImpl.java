package com.code.managerbackend.service.implementation;

import com.code.managerbackend.dto.FilterSectorDTO;
import com.code.managerbackend.dto.SectorDTO;
import com.code.managerbackend.exception.ResourceNotFoundException;
import com.code.managerbackend.exception.RuleBusinessException;
import com.code.managerbackend.model.Office;
import com.code.managerbackend.model.Sector;
import com.code.managerbackend.repository.SectorRepository;
import com.code.managerbackend.service.OfficeService;
import com.code.managerbackend.service.SectorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SectorServiceImpl implements SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OfficeService officeService;

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

        validateSectorBeforeSave(sectorDTO.getName(), sectorDTO);

        Sector sector = modelMapper.map(sectorDTO, Sector.class);

        for (Office office : sector.getOffices()) {
            office.setSector(sector);
            sector.setOffices(office.getSector().getOffices());
        }
        sectorRepository.save(sector);
        return sectorDTO;
    }

    @Override
    public SectorDTO update(SectorDTO sectorDTO) {
        Optional<Sector> currentSector = Optional.ofNullable(sectorRepository.findById(sectorDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found office with id = " + sectorDTO.getId())));

        validateSectorBeforeUpdate(sectorDTO, currentSector);

        Sector sector = modelMapper.map(sectorDTO, Sector.class);

        for (Office office : sector.getOffices()) {
            office.setSector(sector);
            sector.setOffices(office.getSector().getOffices());
        }
        sectorRepository.save(sector);
        return sectorDTO;
    }

    @Override
    public List<SectorDTO> filterSector(FilterSectorDTO filterSectorDTO) {
        List<Sector> sectors = sectorRepository.filterSector(filterSectorDTO.getIdOfficie(), filterSectorDTO.getName(), filterSectorDTO.getSituation());

        return sectors.stream().map(sector -> modelMapper.map(sector, SectorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Optional<Sector> sector = Optional.ofNullable(sectorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found office with id = " + id)));

        sectorRepository.deleteById(id);
    }

    private void checkSectorName(String name) {
        if (sectorRepository.existsByName(name)) {
            throw new RuleBusinessException("This name is already used by another sector.");
        }
    }

    private void validateSectorBeforeSave(String name, SectorDTO sectorDTO) {
        checkSectorName(name);

        for (int i = 0; i < sectorDTO.getOffices().size(); i++) {
            Office positionsReceived = sectorDTO.getOffices().get(i);
            officeService.existsByName(positionsReceived);
        }
    }

    private void validateSectorBeforeUpdate(SectorDTO sectorDTO, Optional<Sector> optionalSector) {
        if (!sectorDTO.getName().equals(optionalSector.get().getName())) {
            checkSectorName(sectorDTO.getName());
        }

        for (int i = 0; i < sectorDTO.getOffices().size(); i++) {
            String namePositionsReceived = sectorDTO.getOffices().get(i).getName();
            String nameCurrentPositions = optionalSector.get().getOffices().get(i).getName();

            if (!namePositionsReceived.equals(nameCurrentPositions)) {
                officeService.existsByName(sectorDTO.getOffices().get(i));
            }
        }

    }
}
