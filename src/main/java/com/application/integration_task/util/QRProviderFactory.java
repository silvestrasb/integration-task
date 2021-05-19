package com.application.integration_task.util;

import com.application.integration_task.entity.Beneficiary;

public class QRProviderFactory {

    public QRCodeProvider<Beneficiary> getProvider(String provider) {
        switch (provider) {
            case "TecIt":
                return new QRCodeTecItProvider();
            default:
                return null;
        }
    }
}
