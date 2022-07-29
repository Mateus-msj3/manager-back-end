package com.code.managerbackend.service.implementation;

import com.code.managerbackend.dto.OfficeDTO;
import com.code.managerbackend.dto.SectorDTO;
import com.code.managerbackend.exception.ObjectNotFoundException;
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
        Optional<Sector> sector = sectorRepository.findById(id);
        if (sector.isEmpty()) {
            throw new ObjectNotFoundException("Sector not found");
        }
        SectorDTO sectorDTO = modelMapper.map(sector.get(), SectorDTO.class);

        return sectorDTO;
    }

    @Override
    public SectorDTO save(SectorDTO sectorDTO) {
        Sector sector = modelMapper.map(sectorDTO, Sector.class);
        for (Office office: sector.getOffices()) {
            office.setSector(sector);
        }
        sectorRepository.save(sector);
        return sectorDTO;
    }

    @Override
    public SectorDTO update(SectorDTO sectorDTO) {
        try {
            Optional<Sector> currentSector = sectorRepository.findById(sectorDTO.getId());
            if (currentSector.isPresent()) {
                //Sector sector = modelMapper.map(sectorDTO, Sector.class);
                BeanUtils.copyProperties(sectorDTO, currentSector.get(), "id");
                sectorRepository.save(currentSector.get());
            }
        } catch (NoSuchElementException exception) {
            throw new ObjectNotFoundException("Sector not found.");
        }
        return sectorDTO;
    }

    @Override
    public void delete(Long id) {
        Optional<Sector> sector = sectorRepository.findById(id);
        if (sector.isEmpty()) {
            throw new ObjectNotFoundException("Sector not found");
        }
        sectorRepository.deleteById(id);
    }
}
