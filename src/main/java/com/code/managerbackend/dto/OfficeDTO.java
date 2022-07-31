package com.code.managerbackend.dto;

import com.code.managerbackend.model.Sector;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfficeDTO {

    private Long id;

    private String name;

    private BigDecimal minimumSalaryRange;

    private BigDecimal maximumSalaryRange;

    private Sector sector;
}
