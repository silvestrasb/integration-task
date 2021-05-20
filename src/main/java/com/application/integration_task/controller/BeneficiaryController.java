package com.application.integration_task.controller;

import com.application.integration_task.entity.Beneficiary;
import com.application.integration_task.service.BeneficiaryService;
import com.application.integration_task.util.QRProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/beneficiaries")
public class BeneficiaryController {

    private final Logger log = LoggerFactory.getLogger(BeneficiaryController.class);

    private final BeneficiaryService beneficiaryService;

    public BeneficiaryController(BeneficiaryService beneficiaryService) {
        this.beneficiaryService = beneficiaryService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        log.info("/beneficiaries/list -> list: Listing beneficiaries");
        model.addAttribute("beneficiaries", beneficiaryService.findAll());

        return "beneficiaries/list-beneficiaries";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        log.info("/beneficiaries/showFormForAdd -> showFormForAdd: Returning beneficiary-form");
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setUniqueCode(UUID.randomUUID().toString());
        model.addAttribute("beneficiary", beneficiary);
        return "beneficiaries/beneficiary-form";
    }

    @GetMapping("/listByName")
    public String listByName(@RequestParam("beneficiaryName") String name, Model model) {
        log.info("/beneficiaries/listByName -> listByName: Listing beneficiaries by name: " + name);
        model.addAttribute("beneficiaries", beneficiaryService.findAllByName(name));
        return "beneficiaries/list-beneficiaries";
    }

    @GetMapping("/findByUniqueCode")
    public String findByUniqueCode(@RequestParam("beneficiaryUniqueCode") String uniqueCode, Model model) {
        log.info("/beneficiaries/listByName -> listByName: Listing beneficiary with unique code: " + uniqueCode);
        model.addAttribute("beneficiaries", beneficiaryService.findByUniqueCode(uniqueCode));
        System.out.println(beneficiaryService.findByUniqueCode(uniqueCode));
        return "beneficiaries/list-beneficiaries";
    }

    @GetMapping("/showQRCode")
    public String showQRCode(@RequestParam("beneficiaryId") int id, Model model) {
        model.addAttribute("qrCodeLink",
                new QRProviderFactory()
                        .getProvider("qrcode.tec-it.com")
                        .getLink(
                                beneficiaryService.findById(id)
                        )
        );

        log.info("/beneficiaries/showQRCode -> showQRCode: Showing QR code of beneficiary with id: " + id);

        return "beneficiaries/beneficiary-qr-code";

    }

    @PostMapping("/save")
    public String save(@ModelAttribute("beneficiary") Beneficiary beneficiary) {

        beneficiaryService.save(beneficiary);

        log.info("/beneficiaries/save -> save: Saving beneficiary with id: " + beneficiary.getId());
        return "redirect:/beneficiaries/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("beneficiaryId") int id, Model model) {
        log.info("/beneficiaries/showFormForUpdate -> showFormForUpdate: Updating beneficiary with id: " + id);
        model.addAttribute("beneficiary", beneficiaryService.findById(id));
        return "beneficiaries/beneficiary-form";
    }


    @GetMapping("/delete")
    public String delete(@RequestParam("beneficiaryId") int id) {
        log.info("/beneficiaries/delete -> delete: Deleting beneficiary with id: " + id);
        beneficiaryService.deleteById(id);
        return "redirect:/beneficiaries/list";
    }

}
