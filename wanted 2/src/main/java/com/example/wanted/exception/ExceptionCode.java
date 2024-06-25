package com.example.wanted.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member Not Found"),

    MEMBER_EXISTS(404, "Member Already Exists"),
    PRODUCT_NOT_FOUND(404, "Product Not Found"),
    PRODUCT_NOT_AVAILABLE(404, "Product Not AVAILABLE"),
    ORDER_NOT_FOUND(404, "Order Not Found"),

    PERMISSION_DENIED(404, "Permission Denied");
    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
