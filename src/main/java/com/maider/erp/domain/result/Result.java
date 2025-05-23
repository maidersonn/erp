package com.maider.erp.domain.result;

public interface Result<TValue, TError> {

    Boolean isSuccess();
    TValue getValue();
    TError getError();

}
