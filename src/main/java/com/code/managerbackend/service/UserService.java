package com.code.managerbackend.service;

import com.code.managerbackend.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> listAll();

    UserDTO listById(Long id);

    UserDTO save(UserDTO userDTO);

    UserDTO update(UserDTO userDTO);

    void delete(Long id);
}
