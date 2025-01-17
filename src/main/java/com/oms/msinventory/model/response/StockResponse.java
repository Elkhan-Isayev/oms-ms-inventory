package com.oms.msinventory.model.response;

import lombok.Data;

@Data
public class StockResponse {

    private Long id;

    private Integer quantityAvailable;

    private WarehouseResponse warehouse;

    private ProductResponse product;

}
