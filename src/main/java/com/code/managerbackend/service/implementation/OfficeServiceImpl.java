package com.code.managerbackend.service.implementation;

import com.code.managerbackend.dto.OfficeDTO;
import com.code.managerbackend.exception.ResourceNotFoundException;
import com.code.managerbackend.exception.RuleBusinessException;
import com.code.managerbackend.model.Office;
import com.code.managerbackend.repository.OfficeRepository;
import com.code.managerbackend.service.OfficeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Optional<Office> office = Optional.ofNullable(officeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found office with id = " + id)));

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
            Optional<Office> currentOffice = Optional.ofNullable(officeRepository.findById(officeDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Not found office with id = " + officeDTO.getId())));

                Office office = modelMapper.map(officeDTO, Office.class);
                BeanUtils.copyProperties(officeDTO, currentOffice.get(), "id");
                officeRepository.save(office);

        return officeDTO;
    }

    @Override
    public void delete(Long id) {
        Optional<Office> office = officeRepository.findById(id);
        if (office.isEmpty()) {
            throw new ResourceNotFoundException("Not found office with id = " + id);
        }
        officeRepository.deleteById(id);
    }

    @Override
    public void existsByName(Office office) {
        if (officeRepository.existsByName(office.getName())) {
            throw new RuleBusinessException("This name is already used by another office. " + office.getName());
        }
    }

}
