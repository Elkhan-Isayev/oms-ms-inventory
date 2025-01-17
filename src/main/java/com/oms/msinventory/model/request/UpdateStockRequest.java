package com.oms.msinventory.model.request;

import lombok.Data;

@Data
public class UpdateStockRequest {

    private Integer quantityAvailable;

    private Long productId;

    private Long warehouseId;

}
