package com.application.integration_task.controller;

import com.application.integration_task.entity.Beneficiary;
import com.application.integration_task.service.BeneficiaryService;
import com.application.integration_task.util.QRCodeLink;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.UUID;

// REST Controller for CRUD operations on Beneficiaries in the DB
@RestController
@RequestMapping("/api")
public class BeneficiaryRestController {

    @Autowired
    private BeneficiaryService beneficiaryService;

    // getting all available beneficiaries
    @GetMapping("/beneficiary")
    public List<Beneficiary> getBeneficiaries() {
        return beneficiaryService.findAll();
    }

    // beneficiary  by id
    @GetMapping("/beneficiary/{beneficiaryId}")
    public Beneficiary getBeneficiary(@PathVariable int beneficiaryId) {
        return beneficiaryService.findById(beneficiaryId);
    }

    // return qr code based on id
    @GetMapping("/beneficiary/qr/{beneficiaryId}")
    public RedirectView getUniqueCodeAndNameQRCode(@PathVariable int beneficiaryId) {

        return new RedirectView(
                new QRCodeLink().generateQRCodeLink(
                        beneficiaryService.findById(beneficiaryId)
                )
        );
    }

    // adding a new beneficiary
    @PostMapping("/beneficiary")
    @ApiOperation(value = "add Beneficiary",
            notes = "Let uniqueCode equal null if you want it to be auto generated")
    public Beneficiary addBeneficiary(@RequestBody Beneficiary beneficiary) {
        if (beneficiary.getUniqueCode() == null) {
            beneficiary.setUniqueCode(UUID.randomUUID().toString());
        }
        beneficiary.setId(0);
        beneficiaryService.save(beneficiary);

        return beneficiary;
    }

    // updating an existing beneficiary
    // class BeneficiaryRepository throws required exceptions
    @PutMapping("/beneficiary")
    @ApiOperation(value = "update Beneficiary",
            notes = "Let uniqueCode equal null if you want to keep the original unique code.")
    public Beneficiary updateBeneficiary(@RequestBody Beneficiary beneficiary) {
        if (beneficiary.getUniqueCode() == null) {
            beneficiary.setUniqueCode(beneficiaryService
                            .findById(beneficiary
                            .getId())
                            .getUniqueCode());
        }
        beneficiaryService.save(beneficiary);

        return beneficiary;
    }

    // deleting existing beneficiary
    // class BeneficiaryRepository throws required exceptions
    @DeleteMapping("/beneficiary/{beneficiaryId}")
    public String deleteBeneficiary(@PathVariable int beneficiaryId) {
        beneficiaryService.deleteById(beneficiaryId);
        return "Deleted beneficiary's id - " + beneficiaryId;
    }
}
