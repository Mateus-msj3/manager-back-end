package com.code.managerbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterSectorDTO {

    private Long id;

    private String name;

    private LocalDate initDate;

    private Boolean situation;

    private Long idOfficie;
}
