package com.application.integration_task.util;

import com.application.integration_task.entity.Beneficiary;
import org.springframework.stereotype.Component;

@Component
public class QRProviderFactory {

    private final QRCodeTecItProvider qrCodeTecItProvider;

    public QRProviderFactory(QRCodeTecItProvider qrCodeTecItProvider) {
        this.qrCodeTecItProvider = qrCodeTecItProvider;
    }

    public QRCodeProvider<Beneficiary> getProvider(String provider) {
        if ("qrcode.tec-it.com".equals(provider)) {
            return qrCodeTecItProvider;
        }
        return null;
    }
}
