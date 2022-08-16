package com.code.managerbackend.model;

import com.code.managerbackend.model.Enum.SexoEnum;
import com.code.managerbackend.model.Enum.TipoContratoEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    @NotEmpty(message = "{field.name.mandatory}")
    private String name;

    @Column(nullable = false, length = 150)
    @NotEmpty(message = "{field.cpf.mandatory}")
    @CPF
    private String cpf;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birtDate;

    @Email
    private String email;

    @Enumerated(value = EnumType.STRING)
    private SexoEnum sex;

    @Column(nullable = false, length = 150)
    @NotEmpty(message = "{field.name.mandatory}")
    private String avatarUrl;

    @Embedded
    private Address address;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate admissionDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate resignationDate;

    private BigDecimal salary;

    private Boolean active;

    @Enumerated(value = EnumType.STRING)
    private TipoContratoEnum tipoContrato;

    @ManyToOne()
    private Sector sector;

    @ManyToOne()
    private Office office;

    @ManyToOne(cascade=CascadeType.PERSIST)
    private User user;

}
