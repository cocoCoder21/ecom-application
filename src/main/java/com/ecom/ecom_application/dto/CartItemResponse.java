package com.ecom.ecom_application.dto;

import lombok.Data;

@Data
public class CartItemResponse {
    private Long productId;
    private Integer quantity;

}
