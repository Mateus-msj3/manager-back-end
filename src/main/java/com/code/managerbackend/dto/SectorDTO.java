package com.code.managerbackend.dto;

import com.code.managerbackend.model.Office;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class SectorDTO {

    private Long id;

    private String name;

    private LocalDate initDate;

    private Boolean situation;

    private List<Office> offices = new ArrayList<>();
}
