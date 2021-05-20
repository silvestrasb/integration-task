package com.application.integration_task.controller;

import com.application.integration_task.entity.Beneficiary;
import com.application.integration_task.service.BeneficiaryService;
import com.application.integration_task.util.QRCodeProvider;
import com.application.integration_task.util.QRCodeTecItProvider;
import com.application.integration_task.util.QRProviderFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeneficiaryController.class)
public class BeneficiaryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeneficiaryService beneficiaryService;

    @MockBean
    private QRProviderFactory qrProviderFactory;

    @MockBean
    private QRCodeTecItProvider qrCodeTecItProvider;


    @Test
    void list() throws Exception {
        // Given
        List<Beneficiary> beneficiaryList = List.of(
                new Beneficiary("1", "Mantas"),
                new Beneficiary("2", "Tomas"),
                new Beneficiary("3", "Marija"),
                new Beneficiary("4", "Orinta"),
                new Beneficiary("5", "Kornelija")
        );

        String url = "/beneficiaries/list";

        // When
        Mockito.when(beneficiaryService.findAll()).thenReturn(beneficiaryList);

        // Then
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(model().attribute("beneficiaries",
                        beneficiaryList));
    }

    @Test
    void showFormForAdd() throws Exception {
        // Given
        String url = "/beneficiaries/showFormForAdd";

        // Then
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("beneficiary"));
    }

    @Test
    void showQRCode() throws Exception {
        // Given
        Beneficiary beneficiary = new Beneficiary("TEST_UNIQUE_CODE", "TEST_NAME");

        String url = "/beneficiaries/showQRCode";

        Mockito.when(qrProviderFactory.getProvider("qrcode.tec-it.com")).thenReturn(qrCodeTecItProvider);
        Mockito.when(qrCodeTecItProvider.getLink(beneficiary)).thenReturn("www.test-link.com");

        String qrCodeLink = qrProviderFactory
                .getProvider("qrcode.tec-it.com")
                .getLink(beneficiary);


        // When
        Mockito.when(beneficiaryService.findById(0)).thenReturn(beneficiary);

        // Then
        mockMvc.perform(get(url).param("beneficiaryId", "0"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("qrCodeLink", qrCodeLink));
    }

    @Test
    void showFormForUpdate() throws Exception {
        // Given
        Beneficiary beneficiary = new Beneficiary("1", "Dimitri");
        beneficiary.setId(1);
        String url = "/beneficiaries/showFormForUpdate";

        // When
        Mockito.when(beneficiaryService.findById(beneficiary.getId())).thenReturn(beneficiary);

        // Then
        mockMvc.perform(get(url).param("beneficiaryId", Integer.toString(beneficiary.getId())))
                .andExpect(status().isOk())
                .andExpect(model().attribute("beneficiary", beneficiary));
    }

    @Test
    void listByName() throws Exception {
        // Given
        String name = "Tomas";
        List<Beneficiary> beneficiaryList = List.of(
                new Beneficiary(name),
                new Beneficiary(name),
                new Beneficiary(name)
        );

        String url = "/beneficiaries/listByName";

        // When
        Mockito.when(beneficiaryService.findAllByName(name)).thenReturn(beneficiaryList);

        // Then
        mockMvc.perform(get(url).param("beneficiaryName", name))
                .andExpect(status().isOk())
                .andExpect(model().attribute("beneficiaries", beneficiaryList));

    }

    @Test
    void findByUniqueCode() throws Exception {
        // Given
        Beneficiary beneficiary = new Beneficiary("UNIQUE_CODE", "Tomas");

        String url = "/beneficiaries/findByUniqueCode";

        // When
        Mockito.when(beneficiaryService.findByUniqueCode(beneficiary.getUniqueCode())).thenReturn(beneficiary);

        // Then
        mockMvc.perform(get(url).param("beneficiaryUniqueCode", beneficiary.getUniqueCode()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("beneficiaries", beneficiary));

    }
}