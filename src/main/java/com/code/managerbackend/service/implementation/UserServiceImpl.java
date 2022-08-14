package com.code.managerbackend.service.implementation;

import com.code.managerbackend.dto.UserDTO;
import com.code.managerbackend.exception.ResourceNotFoundException;
import com.code.managerbackend.exception.RuleBusinessException;
import com.code.managerbackend.model.Permission;
import com.code.managerbackend.model.User;
import com.code.managerbackend.repository.UserRepository;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserDTO> listAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO listById(Long id) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user with id = " + id)));

        UserDTO userDTO = modelMapper.map(user.get(), UserDTO.class);

        return userDTO;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {

        checkNameUserBeforeSaving(userDTO.getUsername());
        encodePassword(userDTO);

        User user = modelMapper.map(userDTO, User.class);

        for (Permission permission : user.getPermissions()) {
            user.setPermissions(user.getPermissions());
        }

        userRepository.save(user);
        return userDTO;
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        Optional<User> currentUser = Optional.ofNullable(userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found office with id = " + userDTO.getId())));

        validateSectorBeforeUpdate(userDTO, currentUser);
        encodePassword(userDTO);

        User user = modelMapper.map(userDTO, User.class);

        for (Permission permission : user.getPermissions()) {
            user.setPermissions(user.getPermissions());
        }

        userRepository.save(user);
        return userDTO;
    }

    @Override
    public void delete(Long id) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user with id = " + id)));

        userRepository.deleteById(id);
    }

    private void checkNameUserBeforeSaving(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new RuleBusinessException("This name is already used by another user.");
        }
    }
    private void validateSectorBeforeUpdate(UserDTO userDTO, Optional<User> optionalSector) {
        if (!userDTO.getUsername().equals(optionalSector.get().getUsername())) {
            checkNameUserBeforeSaving(userDTO.getUsername());
        }
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private void encodePassword(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder().encode(userDTO.getPassword()));
    }

}
