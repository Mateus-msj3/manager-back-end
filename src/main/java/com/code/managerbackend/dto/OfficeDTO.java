package com.code.managerbackend.dto;

import com.code.managerbackend.model.Sector;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OfficeDTO {

    private Long id;

    private String name;

    private BigDecimal minimumSalaryRange;

    private BigDecimal maximumSalaryRange;

    private Sector sector;
}
