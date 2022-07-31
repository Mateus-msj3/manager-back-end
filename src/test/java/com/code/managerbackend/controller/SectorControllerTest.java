package com.code.managerbackend.controller;

import com.code.managerbackend.dto.SectorDTO;
import com.code.managerbackend.model.Office;
import com.code.managerbackend.model.Sector;
import com.code.managerbackend.service.SectorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
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
    private SectorService sectorService;

    @Test
    @DisplayName("Deve criar um setor com sucesso")
    public void createdSectorTest() throws Exception {

        Office office = Office.builder()
                .name("Social mídia")
                .minimumSalaryRange(new BigDecimal(800.0))
                .maximumSalaryRange(new BigDecimal(3000.0))
                .sector(null)
                .build();

        List<Office> officeList = Arrays.asList(office);

        //Objeto passado na requisição
        SectorDTO sectorDTO = SectorDTO.builder()
                .name("Marketing")
                .initDate(LocalDate.of(2022, Month.JULY, 30))
                .situation(true)
                .offices(officeList)
                .build();

        SectorDTO savedSector = SectorDTO.builder()
                .id(1L)
                .name("Marketing")
                .initDate(LocalDate.of(2022, Month.JULY, 30))
                .situation(true)
                .offices(officeList)
                .build();

        BDDMockito.given(sectorService.save(sectorDTO)).willReturn(savedSector);

        //Json que será retornado como resposta
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String json = mapper.writeValueAsString(sectorDTO);

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
}
