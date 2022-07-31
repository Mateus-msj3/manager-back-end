package com.code.managerbackend.service;

import com.code.managerbackend.dto.SectorDTO;
import com.code.managerbackend.model.Office;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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
        SectorDTO sectorDTO = createSector();

        //Retorno
        Mockito.when(sectorService.save(sectorDTO)).thenReturn(createSavedSector());

        //Execução
        SectorDTO savedSectorSucess = sectorService.save(sectorDTO);

        //Verificação
        assertThat(savedSectorSucess.getId()).isNotNull();
        assertThat(savedSectorSucess.getName()).isEqualTo("Marketing");
        assertThat(savedSectorSucess.getInitDate()).isEqualTo(LocalDate.now());
        assertThat(savedSectorSucess.getSituation()).isEqualTo(true);
    }

    @Test
    @DisplayName("Lançar um erro quando tentar salvar um setor com o nome já existente")
    public void shouldNotSaveSectorWithDuplicateName() {

        //Cenário
        SectorDTO sectorDTO = createSector();

        //Execução
        Mockito.when(sectorService.save(sectorDTO)).thenReturn(sectorDTO);

        //Verificação
        assertThat(sectorDTO.getName()).isEqualTo("Marketing");

        //Não chamar o save do repository
        Mockito.verify(sectorService, Mockito.never()).save(sectorDTO);

    }


    private SectorDTO createSector() {

        Office office = createOfficeToSector();

        List<Office> officeList = Arrays.asList(office);
        return SectorDTO.builder()
                .name("Marketing")
                .initDate(LocalDate.now())
                .situation(true)
                .offices(officeList)
                .build();
    }

    private Office createOfficeToSector() {
        return Office.builder()
                .name("Social mídia")
                .minimumSalaryRange(new BigDecimal(800.0))
                .maximumSalaryRange(new BigDecimal(3000.0))
                .sector(null)
                .build();
    }

    private SectorDTO createSavedSector() {
        Office office = createOfficeToSector();
        List<Office> officeList = Arrays.asList(office);

        return SectorDTO.builder()
                .id(1L)
                .name("Marketing")
                .initDate(LocalDate.now())
                .situation(true)
                .offices(officeList)
                .build();
    }
}
