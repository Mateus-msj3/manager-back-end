package com.code.managerbackend.dto;

import com.code.managerbackend.model.Address;
import com.code.managerbackend.model.Enum.SexoEum;
import com.code.managerbackend.model.Enum.TipoContratoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;

    @NotEmpty(message = "{field.name.mandatory}")
    private String name;

    @NotEmpty(message = "{field.cpf.mandatory}")
    @CPF
    private String cpf;

    private LocalDate birtDate;

    @Email
    private String email;

    private SexoEum sex;

    private String avatarUrl;

    private Address address;

    private LocalDate admissionDate;

    private LocalDate resignationDate;

    private BigDecimal salary;

    private Boolean active;

    private TipoContratoEnum tipoContrato;

}
