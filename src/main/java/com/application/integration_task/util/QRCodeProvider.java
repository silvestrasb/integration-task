package com.application.integration_task.util;

public interface QRCodeProvider<E> {

    String getLink(E beneficiary);

    void setDelimiter(char delimiter);

}
