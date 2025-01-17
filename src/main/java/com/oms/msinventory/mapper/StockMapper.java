package com.oms.msinventory.mapper;

import com.oms.msinventory.model.entity.ProductEntity;
import com.oms.msinventory.model.entity.StockEntity;
import com.oms.msinventory.model.request.CreateProductRequest;
import com.oms.msinventory.model.request.CreateStockRequest;
import com.oms.msinventory.model.request.UpdateProductRequest;
import com.oms.msinventory.model.request.UpdateStockRequest;
import com.oms.msinventory.model.response.ProductResponse;
import com.oms.msinventory.model.response.StockResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StockMapper {

    StockEntity mapToStockEntity(CreateStockRequest createStockRequest);

    StockResponse mapToStockResponse(StockEntity stockEntity);

    List<StockResponse> mapToStockResponseList(List<StockEntity> stockEntities);

    void mapUpdateRequestToStockEntity(@MappingTarget StockEntity stockEntity,
                                         UpdateStockRequest updateStockRequest);

}
