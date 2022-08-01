package com.code.managerbackend.controller;

import com.code.managerbackend.dto.SectorDTO;
import com.code.managerbackend.exception.ResourceNotFoundException;
import com.code.managerbackend.exception.RuleBusinessException;
import com.code.managerbackend.model.Office;
import com.code.managerbackend.model.Sector;
import com.code.managerbackend.service.SectorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = SectorController.class)
@AutoConfigureMockMvc
public class SectorControllerTest {

    static String API = "/sectors";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SectorService sectorService;

    @Test
    @DisplayName("Deve criar um setor com sucesso")
    public void createdSectorTest() throws Exception {

        //Objeto passado na requisição
        SectorDTO sectorDTO = createSector();

        SectorDTO savedSector = createSavedSector();

        BDDMockito.given(sectorService.save(sectorDTO)).willReturn(savedSector);

        //Json que será retornado como resposta
        String json = objectToJson(sectorDTO);

        //Desenho da requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        //Fazendo a requisição
        mockMvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("name").value(sectorDTO.getName()))
                .andExpect(jsonPath("situation").value(sectorDTO.getSituation()))
                .andDo(print());

    }

    @Test
    @DisplayName("Deve lançar um erro de validação quando não houver dados válidos")
    public void createInvalidSectorTest() throws Exception {
        String json = objectToJson(new SectorDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors" , hasSize(1)))
                .andDo(print());
    }

    @Test
    @DisplayName("Deve lançar um erro ao cadastar um setor com um nome já existente")
    public void createSectorWithDuplicateName() throws Exception {
        SectorDTO sectorDTO = createSector();

        String json = objectToJson(sectorDTO);

        var message = "This name is already used by another sector.";

        //Execução
        BDDMockito.given(sectorService.save(sectorDTO))
                .willThrow(new RuleBusinessException(message));
        
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        
        mockMvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors" , hasSize(1)))
                .andExpect(jsonPath("errors[0]").value(message))
                .andDo(print());
    }

    @Test
    @DisplayName("Deve retornar todos os setores cadastrados")
    public void getAllSectorsTest() throws Exception {
        //Cenário
        SectorDTO sectorDTO = createSavedSector();

        List<SectorDTO> sectorDTOS = Arrays.asList(sectorDTO);

        BDDMockito.given(sectorService.listAll()).willReturn(sectorDTOS);

        //Execução
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Deve retornar um setor quando informado o id")
    public void getSectorByIdTest() throws Exception {
        //Cenário
        SectorDTO sectorDTO = createSavedSector();

        BDDMockito.given(sectorService.listById(sectorDTO.getId())).willReturn(sectorDTO);

        //Execução
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/"+sectorDTO.getId()))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(sectorDTO.getId()))
                .andExpect(jsonPath("name").value(sectorDTO.getName()))
                .andExpect(jsonPath("situation").value(sectorDTO.getSituation()))
                .andDo(print());
    }

    @Test
    @DisplayName("Deve retornar uma mensagem de erro quando o id do setor informado não existir")
    public void sectorNotFoundTest() throws Exception {
        //Cenário
        var message = "Not found sector with id = ";

        BDDMockito.given(sectorService.listById(Mockito.anyLong()))
                .willThrow(new ResourceNotFoundException(message));

        //Execução
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/"+ 1))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value(message))
                .andDo(print());
    }

    @Test
    @DisplayName("Deve deletar um setor pelo id informado")
    public void deleteSectorTest() throws Exception {
        //Cenário
        BDDMockito.given(sectorService.listById(Mockito.anyLong()))
                .willReturn(SectorDTO.builder().id(1l).build());

        //Execução
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(API.concat("/"+ 1));

        //Verificação
        mockMvc
                .perform(request)
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("Deve atualizar um setor quando informado o id")
    public void updateSectorByIdTest() throws Exception {
        //Cenário

        Long id = 1L;

        //Json que será retornado como resposta
        String json = objectToJson(createSavedSector());

        SectorDTO updatingSector = SectorDTO.builder()
                .id(1L).name("Finanças")
                .initDate(LocalDate.now())
                .situation(false)
                .offices(null)
                .build();

        BDDMockito.given(sectorService.listById(id))
                .willReturn(updatingSector);

        SectorDTO updated = createSavedSector();

        BDDMockito.given(sectorService.update(updatingSector))
                .willReturn(updated);

        //Desenho da requisição
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(API.concat("/" + id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);


        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andDo(print());
    }


    private SectorDTO createSector() {

        Office office = createOfficeToSector();

        List<Office> officeList = Arrays.asList(office);
        return SectorDTO.builder()
                .name("Marketing")
                .initDate(LocalDate.of(2022, Month.JULY, 30))
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
                .initDate(LocalDate.of(2022, Month.JULY, 30))
                .situation(true)
                .offices(officeList)
                .build();
    }


    private String objectToJson(SectorDTO sectorDTO) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String value = mapper.writeValueAsString(sectorDTO);

        return value;
    }

}
