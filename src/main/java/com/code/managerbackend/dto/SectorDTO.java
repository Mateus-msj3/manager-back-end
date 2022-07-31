package com.code.managerbackend.dto;

import com.code.managerbackend.model.Office;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SectorDTO {

    private Long id;

    @Column(nullable = false, length = 150)
    @NotEmpty(message = "{field.name.mandatory}")
    private String name;

    private LocalDate initDate;

    private Boolean situation;

    private List<Office> offices = new ArrayList<>();
}
