package com.code.managerbackend.controller;

import com.code.managerbackend.dto.PermissionDTO;
import com.code.managerbackend.dto.UserDTO;
import com.code.managerbackend.service.PermissionService;
import com.code.managerbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public List<PermissionDTO> listAll() {
        return permissionService.listAll();
    }

}
