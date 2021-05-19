package com.application.integration_task.util;

import com.application.integration_task.entity.Beneficiary;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class QRCodeLink {

    // by default using https://qrcode.tec-it.com QR code provider's API
    private String QRCodeProvider = "https://qrcode.tec-it.com/API/QRCode?data=%s%s%s";
    private char deliminator = ',';

    public String generateQRCodeLink(Beneficiary beneficiary) {
        return String.format(QRCodeProvider,
                URLEncoder.encode(beneficiary.getUniqueCode(), StandardCharsets.UTF_8),
                deliminator,
                URLEncoder.encode(beneficiary.getName(), StandardCharsets.UTF_8));
    }


}
