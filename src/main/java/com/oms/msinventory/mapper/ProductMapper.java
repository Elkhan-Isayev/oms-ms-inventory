package com.oms.msinventory.mapper;

import com.oms.msinventory.model.entity.ProductEntity;
import com.oms.msinventory.model.request.CreateProductRequest;
import com.oms.msinventory.model.request.UpdateProductRequest;
import com.oms.msinventory.model.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductEntity mapToProductEntity(CreateProductRequest createProductRequest);

    ProductResponse mapToProductResponse(ProductEntity productEntity);

    List<ProductResponse> mapToProductResponseList(List<ProductEntity> productEntities);

    void mapUpdateRequestToProductEntity(@MappingTarget ProductEntity productEntity,
                                       UpdateProductRequest updateProductRequest);

}
