package com.code.managerbackend.service;

import com.code.managerbackend.dto.SectorDTO;
import com.code.managerbackend.repository.SectorRepository;
import com.code.managerbackend.service.implementation.SectorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class SectorServiceTest {

    @MockBean
    SectorService sectorService;

    @Test
    @DisplayName("Deve salvar um livro")
    public void saveSectorTest() {

        //Cenário
        SectorDTO sectorDTO = SectorDTO.builder()
                .name("Marketing")
                .initDate(LocalDate.now())
                .situation(true)
                .offices(null)
                .build();

        //Retorno
        Mockito.when(sectorService.save(sectorDTO))
                .thenReturn(SectorDTO.builder()
                        .id(1l)
                        .name("Marketing")
                        .initDate(LocalDate.now())
                        .situation(true)
                        .offices(null)
                        .build());

        //Execução
        SectorDTO savedSector = sectorService.save(sectorDTO);

        //Verificação
        assertThat(savedSector.getId()).isNotNull();
        assertThat(savedSector.getName()).isEqualTo("Marketing");
        assertThat(savedSector.getInitDate()).isEqualTo(LocalDate.now());
        assertThat(savedSector.getSituation()).isEqualTo(true);
    }
}
