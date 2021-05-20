package com.application.integration_task.controller;

import com.application.integration_task.entity.Beneficiary;
import com.application.integration_task.service.BeneficiaryService;
import com.application.integration_task.util.QRProviderFactory;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

// REST Controller for CRUD operations on Beneficiaries in the DB
@RestController
@RequestMapping("/api")
public class BeneficiaryRestController {


    private final BeneficiaryService beneficiaryService;

    public BeneficiaryRestController(BeneficiaryService beneficiaryService) {

        this.beneficiaryService = beneficiaryService;
    }

    @GetMapping("/beneficiary")
    @ApiOperation(value = "Get all available Beneficiaries from the database.")
    public List<Beneficiary> getBeneficiaries() {
        return beneficiaryService.findAll();
    }


    @GetMapping("/beneficiary/{beneficiaryId}")
    @ApiOperation(value = "Get Beneficiary by id.")
    public Beneficiary getBeneficiary(
            @ApiParam(
                    name = "beneficiaryId",
                    value = "ID of the beneficiary",
                    example = "1",
                    required = true
            )
            @PathVariable int beneficiaryId) {
        return beneficiaryService.findById(beneficiaryId);
    }


    @GetMapping("/beneficiary/qr/{beneficiaryId}")
    @ApiOperation(value = "Provide an id to get QR image containing uniqueCode and name of the Beneficiary.")
    public ResponseEntity<Void> getUniqueCodeAndNameQRCode(
            @ApiParam(
                    name = "beneficiaryId",
                    value = "ID of the beneficiary",
                    example = "1",
                    required = true
            )
            @PathVariable int beneficiaryId) {

        String link = new QRProviderFactory()
                .getProvider("qrcode.tec-it.com")
                .getLink(
                        beneficiaryService.findById(beneficiaryId)
                );

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(link)).build();
    }


    @PostMapping("/beneficiary")
    @ApiOperation(
            value = "Add new Beneficiary",
            notes = "Let uniqueCode equal null if you want it to be auto generated"
    )
    public Beneficiary addBeneficiary(
            @ApiParam(
                    name = "beneficiary",
                    value = "Beneficiary object in json"
            )
            @RequestBody Beneficiary beneficiary) {
        if (beneficiary.getUniqueCode() == null) {
            beneficiary.setUniqueCode(UUID.randomUUID().toString());
        }
        beneficiary.setId(0);
        beneficiaryService.save(beneficiary);

        return beneficiary;
    }


    @PutMapping("/beneficiary")
    @ApiOperation(
            value = "Update Beneficiary",
            notes = "Let uniqueCode equal null if you want to keep the original unique code."
    )
    public Beneficiary updateBeneficiary(
            @ApiParam(
                    name = "beneficiary",
                    value = "Beneficiary object in json"
            )
            @RequestBody Beneficiary beneficiary) {
        if (beneficiary.getUniqueCode() == null) {
            beneficiary.setUniqueCode(beneficiaryService
                    .findById(beneficiary
                            .getId())
                    .getUniqueCode());
        }
        beneficiaryService.save(beneficiary);

        return beneficiary;
    }


    @DeleteMapping("/beneficiary/{beneficiaryId}")
    @ApiOperation(value = "Delete Beneficiary by id.")
    public String deleteBeneficiary(
            @ApiParam(
                    name = "beneficiaryId",
                    value = "ID of the beneficiary",
                    example = "1",
                    required = true
            )
            @PathVariable int beneficiaryId) {
        beneficiaryService.deleteById(beneficiaryId);
        return "Deleted beneficiary's id - " + beneficiaryId;
    }
}
