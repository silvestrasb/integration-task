package com.application.integration_task.controller;

import com.application.integration_task.entity.Beneficiary;
import com.application.integration_task.exception.BeneficiaryNotFoundException;
import com.application.integration_task.exception.BeneficiaryUniqueCodeDuplicateException;
import com.application.integration_task.service.BeneficiaryService;
import com.application.integration_task.util.QRProviderFactory;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
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

    private final Logger log = LoggerFactory.getLogger(BeneficiaryRestController.class);
    private final QRProviderFactory qrProviderFactory;
    private final BeneficiaryService beneficiaryService;

    public BeneficiaryRestController(QRProviderFactory qrProviderFactory,
                                     BeneficiaryService beneficiaryService) {
        this.qrProviderFactory = qrProviderFactory;
        this.beneficiaryService = beneficiaryService;
    }

    @GetMapping("/beneficiary")
    @ApiOperation(value = "Get all available Beneficiaries from the database.")
    public List<Beneficiary> getBeneficiaries() {
        log.info("/api/beneficiary -> getBeneficiaries: Retrieving all beneficiaries.");
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

        log.info("/api/beneficiary/{beneficiaryId} -> getBeneficiary: Retrieving beneficiary with id :" + beneficiaryId);

        nonExistingIdCatcher(beneficiaryId);

        log.info("/api/beneficiary/{beneficiaryId} -> getBeneficiary: Retrieved beneficiary with id :" + beneficiaryId);

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

        log.info("/api/beneficiary/qr/{beneficiaryId} -> getUniqueCodeAndNameQRCode: Returning QR Image for beneficiary with id: " + beneficiaryId);

        nonExistingIdCatcher(beneficiaryId);

        String link = qrProviderFactory
                .getProvider("qrcode.tec-it.com")
                .getLink(
                        beneficiaryService.findById(beneficiaryId)
                );

        log.info("/api/beneficiary/qr/{beneficiaryId} -> getUniqueCodeAndNameQRCode: Returned QR Image for beneficiary with id: " + beneficiaryId);
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

        log.info("/api/beneficiary -> addBeneficiary: Saving beneficiary with id: " + beneficiary.getId());

        if (beneficiary.getUniqueCode() == null) {
            beneficiary.setUniqueCode(UUID.randomUUID().toString());
        }

        beneficiary.setId(0);

        duplicateKeyCatcher(beneficiary);

        log.info("/api/beneficiary -> addBeneficiary: Saved beneficiary with id: " + beneficiary.getId());

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

        log.info("/api/beneficiary -> updateBeneficiary: Updating beneficiary with id: " + beneficiary.getId());

        nonExistingIdCatcher(beneficiary.getId());

        if (beneficiary.getUniqueCode() == null) {
            beneficiary.setUniqueCode(beneficiaryService
                    .findById(beneficiary
                            .getId())
                    .getUniqueCode());
        }

        duplicateKeyCatcher(beneficiary);

        log.info("/api/beneficiary -> updateBeneficiary: Updated beneficiary with id: " + beneficiary.getId());

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

        log.info("/api/beneficiary/{beneficiaryId} -> deleteBeneficiary: Deleting beneficiary with id: " + beneficiaryId);

        nonExistingIdCatcher(beneficiaryId);
        beneficiaryService.deleteById(beneficiaryId);

        log.info("/api/beneficiary/{beneficiaryId} -> deleteBeneficiary: Deleted beneficiary with id: " + beneficiaryId);

        return "Deleted beneficiary's id -> " + beneficiaryId;
    }


    // HELPER METHODS

    private void duplicateKeyCatcher(Beneficiary beneficiary){
        try {
            beneficiaryService.save(beneficiary);
        } catch (DataIntegrityViolationException e) {
            log.error("duplicate key, beneficiary's with the same unique code id: " +
                    beneficiary.getId());
            throw new BeneficiaryUniqueCodeDuplicateException("Unique code already exists, id: " +
                    beneficiary.getId());
        }
    }

    private void nonExistingIdCatcher(int id){
        if (beneficiaryService.findById(id) == null){
            log.error("Beneficiary not found with id: " + id);
            throw new BeneficiaryNotFoundException("Beneficiary not found with id: " + id);
        }
    }
}
