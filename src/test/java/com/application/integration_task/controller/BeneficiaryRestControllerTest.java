package com.application.integration_task.controller;

import com.application.integration_task.entity.Beneficiary;
import com.application.integration_task.service.BeneficiaryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeneficiaryRestController.class)
class BeneficiaryRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BeneficiaryService beneficiaryService;


    @Test
    void getBeneficiaries_text_text() throws Exception {
        // Given
        List<Beneficiary> beneficiaryList = List.of(
                new Beneficiary("1", "Mantas"),
                new Beneficiary("2", "Tomas"),
                new Beneficiary("3", "Marija"),
                new Beneficiary("4", "Orinta"),
                new Beneficiary("5", "Kornelija")
        );

        String url = "/api/beneficiary";

        Mockito.when(beneficiaryService.findAll()).thenReturn(beneficiaryList);

        // When
        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        String expectedJsonResponse = objectMapper.writeValueAsString(beneficiaryList);

        // Then
        assertEquals(actualJsonResponse, expectedJsonResponse);
    }

    @Test
    void getBeneficiary() throws Exception {
        // Given
        Beneficiary beneficiary = new Beneficiary("TEST_UNIQUE_CODE", "TEST_NAME");
        beneficiary.setId(1);
        String url = "/api/beneficiary/" + beneficiary.getId();
        Mockito.when(beneficiaryService.findById(1)).thenReturn(beneficiary);

        // When

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        String expectedJsonResponse = objectMapper.writeValueAsString(beneficiary);

        // Then

        assertEquals(actualJsonResponse, expectedJsonResponse);
    }


    @Test
    void addBeneficiary() throws Exception {
        // Given
        Beneficiary beneficiary = new Beneficiary("TEST_UNIQUE_CODE", "TEST_NAME");
        String url = "/api/beneficiary";

        // When
        MvcResult mvcResult = mockMvc.perform(
                post(url)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(beneficiary)))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        String expectedJsonResponse = objectMapper.writeValueAsString(beneficiary);

        // Then
        assertEquals(actualJsonResponse, expectedJsonResponse);

    }


    @Test
    void updateBeneficiary() throws Exception {
        // Given
        Beneficiary beneficiary = new Beneficiary("TEST_UNIQUE_CODE", "TEST_NAME");
        String url = "/api/beneficiary";

        // When
        MvcResult mvcResult = mockMvc.perform(
                put(url)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(beneficiary)))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        String expectedJsonResponse = objectMapper.writeValueAsString(beneficiary);

        // Then
        assertEquals(actualJsonResponse, expectedJsonResponse);

    }


    @Test
    void deleteBeneficiary() throws Exception {
        // Given
        Beneficiary beneficiary = new Beneficiary("TEST_UNIQUE_CODE", "TEST_NAME");
        beneficiary.setId(53);
        String url = "/api/beneficiary/" + beneficiary.getId();

        // When
        MvcResult mvcResult = mockMvc.perform(delete(url)).andExpect(status().isOk()).andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedResponse = "Deleted beneficiary's id - " + beneficiary.getId();

        // Then
        assertEquals(actualResponse, expectedResponse);

    }
}