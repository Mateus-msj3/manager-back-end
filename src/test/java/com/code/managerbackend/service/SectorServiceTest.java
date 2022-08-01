package com.code.managerbackend.service;

import com.code.managerbackend.dto.SectorDTO;
import com.code.managerbackend.exception.ResourceNotFoundException;
import com.code.managerbackend.model.Office;
import org.junit.jupiter.api.Assertions;
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
        SectorDTO sectorDTO = createSector();

        Mockito.when(sectorService.save(sectorDTO)).thenReturn(createSavedSector());

        SectorDTO savedSectorSucess = sectorService.save(sectorDTO);

        assertThat(savedSectorSucess.getId()).isNotNull();
        assertThat(savedSectorSucess.getName()).isEqualTo("Marketing");
        assertThat(savedSectorSucess.getInitDate()).isEqualTo(LocalDate.now());
        assertThat(savedSectorSucess.getSituation()).isEqualTo(true);
    }

    @Test
    @DisplayName("Lançar um erro quando tentar salvar um setor com o nome já existente")
    public void shouldNotSaveSectorWithDuplicateName() {
        SectorDTO sectorDTO = createSector();

        Mockito.when(sectorService.save(sectorDTO)).thenReturn(sectorDTO);

        assertThat(sectorDTO.getName()).isEqualTo("Marketing");

        Mockito.verify(sectorService, Mockito.never()).save(sectorDTO);

    }

    @Test
    @DisplayName("Deve retornar um setor quando informado um id")
    public void getByIdTest() {
        Long id = 1l;
        SectorDTO sectorDTO = createSector();
        sectorDTO.setId(id);
        Mockito.when(sectorService.listById(id)).thenReturn(sectorDTO);

        SectorDTO dto = sectorService.listById(id);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getName()).isEqualTo(dto.getName());
        assertThat(dto.getSituation()).isEqualTo(dto.getSituation());
        assertThat(dto.getInitDate()).isEqualTo(dto.getInitDate());
        assertThat(dto.getOffices()).isNotNull();
    }

    @Test
    @DisplayName("Deve retornar um mensagem de erro quando informado um id inexistente")
    public void getByInexistentIdTest() {

        var message = "Not found sector with id = ";

        Mockito.when(sectorService.listById(Mockito.anyLong()))
                .thenThrow(new ResourceNotFoundException(message));

        assertThat(message).isEqualTo(message);
    }

    @Test
    @DisplayName("Deve deletar um setor quando informado um id")
    public void deleteSectorTest() {
        Long id = 1l;

        Assertions.assertDoesNotThrow( () -> sectorService.delete(id));

        Mockito.verify(sectorService, Mockito.times(1)).delete(id);
    }

    @Test
    @DisplayName("Deve atualizar um setor")
    public void updateSectorByIdTest() {
        Long id = 1l;

        SectorDTO updatingScetor = SectorDTO.builder().id(id).build();

        SectorDTO sectorDTO = createSector();
        sectorDTO.setId(id);
        Mockito.when(sectorService.update(updatingScetor)).thenReturn(sectorDTO);

        SectorDTO dto = sectorService.update(updatingScetor);

        assertThat(dto.getId()).isEqualTo(sectorDTO.getId());
        assertThat(dto.getName()).isEqualTo(sectorDTO.getName());
        assertThat(dto.getSituation()).isEqualTo(sectorDTO.getSituation());
        assertThat(dto.getInitDate()).isEqualTo(sectorDTO.getInitDate());
        assertThat(dto.getOffices()).isNotNull();
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
