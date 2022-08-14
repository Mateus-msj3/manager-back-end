package com.code.managerbackend.service.implementation;

import com.code.managerbackend.dto.PermissionDTO;
import com.code.managerbackend.dto.UserDTO;
import com.code.managerbackend.exception.ResourceNotFoundException;
import com.code.managerbackend.exception.RuleBusinessException;
import com.code.managerbackend.model.Permission;
import com.code.managerbackend.model.User;
import com.code.managerbackend.repository.PermissionRepository;
import com.code.managerbackend.repository.UserRepository;
import com.code.managerbackend.service.PermissionService;
import com.code.managerbackend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PermissionDTO> listAll() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream()
                .map(permission -> modelMapper.map(permission, PermissionDTO.class))
                .collect(Collectors.toList());
    }

}
