package com.code.managerbackend.service.implementation;

import com.code.managerbackend.dto.OfficeDTO;
import com.code.managerbackend.exception.ObjectNotFoundException;
import com.code.managerbackend.model.Office;
import com.code.managerbackend.repository.OfficeRepository;
import com.code.managerbackend.service.OfficeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfficeServiceImpl implements OfficeService {

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<OfficeDTO> listAll() {
        List<Office> offices = officeRepository.findAll();
        return offices.stream()
                .map(office -> modelMapper.map(office, OfficeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public OfficeDTO listById(Long id) {
        Optional<Office> office = officeRepository.findById(id);
        if (office.isEmpty()) {
            throw new ObjectNotFoundException("Office not found");
        }
        OfficeDTO officeDTO = modelMapper.map(office.get(), OfficeDTO.class);

        return officeDTO;
    }

    @Override
    public OfficeDTO save(OfficeDTO officeDTO) {
        Office office = modelMapper.map(officeDTO, Office.class);
        officeRepository.save(office);
        return officeDTO;
    }

    @Override
    public OfficeDTO update(OfficeDTO officeDTO) {
        try {
            Optional<Office> currentOffice = officeRepository.findById(officeDTO.getId());
            if (currentOffice.isPresent()) {
                Office office = modelMapper.map(officeDTO, Office.class);
                BeanUtils.copyProperties(officeDTO, currentOffice.get(), "id");
                officeRepository.save(office);
            }
        } catch (NoSuchElementException exception) {
            throw new ObjectNotFoundException("Office not found.");
        }

        return officeDTO;
    }

    @Override
    public void delete(Long id) {
        Optional<Office> office = officeRepository.findById(id);
        if (office.isEmpty()) {
            throw new ObjectNotFoundException("Office not found");
        }
        officeRepository.deleteById(id);
    }

}
