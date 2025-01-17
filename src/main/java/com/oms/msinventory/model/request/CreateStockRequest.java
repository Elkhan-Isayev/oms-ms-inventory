package com.oms.msinventory.model.request;

import lombok.Data;

@Data
public class CreateStockRequest {

    private Integer quantityAvailable;

    private Long productId;

    private Long warehouseId;

}
