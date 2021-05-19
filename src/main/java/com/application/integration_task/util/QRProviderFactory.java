package com.application.integration_task.util;

import com.application.integration_task.entity.Beneficiary;

public class QRProviderFactory {

    public QRCodeProvider<Beneficiary> getProvider(String provider) {
        switch (provider) {
            case "qrcode.tec-it.com":
                return new QRCodeTecItProvider();
            default:
                return null;
        }
    }
}
