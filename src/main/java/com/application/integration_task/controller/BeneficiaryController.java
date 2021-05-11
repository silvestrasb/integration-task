package com.application.integration_task.controller;

import com.application.integration_task.entity.Beneficiary;
import com.application.integration_task.service.BeneficiaryService;
import com.application.integration_task.util.QRCodeLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/beneficiaries")
public class BeneficiaryController {

    @Autowired
    private BeneficiaryService beneficiaryService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("beneficiaries", beneficiaryService.findAll());

        return "beneficiaries/list-beneficiaries";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setUniqueCode(UUID.randomUUID().toString());
        model.addAttribute("beneficiary", beneficiary);
        return "beneficiaries/beneficiary-form";
    }

    @GetMapping("/listByName")
    public String listByName(@RequestParam("beneficiaryName") String name, Model model) {
        model.addAttribute("beneficiaries", beneficiaryService.findAllByName(name));
        return "beneficiaries/list-beneficiaries";
    }

    @GetMapping("/findByUniqueCode")
    public String findByUniqueCode(@RequestParam("beneficiaryUniqueCode") String uniqueCode, Model model) {
        model.addAttribute("beneficiaries", beneficiaryService.findByUniqueCode(uniqueCode));
        return "beneficiaries/list-beneficiaries";
    }

    @GetMapping("/showQRCode")
    public String showQRCode(@RequestParam("beneficiaryId") int id, Model model) {
        model.addAttribute("qrCodeLink",
                new QRCodeLink()
                        .generateQRCodeLink(
                                beneficiaryService.findById(id)
                        ));

        return "beneficiaries/beneficiary-qr-code";

    }

    @PostMapping("/save")
    public String save(@ModelAttribute("beneficiary") Beneficiary beneficiary) {
        beneficiaryService.save(beneficiary);
        return "redirect:/beneficiaries/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("beneficiaryId") int id, Model model) {
        model.addAttribute("beneficiary", beneficiaryService.findById(id));
        return "beneficiaries/beneficiary-form";
    }


    @GetMapping("/delete")
    public String delete(@RequestParam("beneficiaryId") int id) {
        beneficiaryService.deleteById(id);
        return "redirect:/beneficiaries/list";
    }

}
