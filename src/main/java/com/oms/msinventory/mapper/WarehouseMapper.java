package com.oms.msinventory.mapper;

import com.oms.msinventory.model.entity.WarehouseEntity;
import com.oms.msinventory.model.request.CreateWarehouseRequest;
import com.oms.msinventory.model.request.UpdateWarehouseRequest;
import com.oms.msinventory.model.response.WarehouseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WarehouseMapper {

    WarehouseEntity mapToWarehouseEntity(CreateWarehouseRequest createWarehouseRequest);

    WarehouseResponse mapToWarehouseResponse(WarehouseEntity warehouseEntity);

    List<WarehouseResponse> mapToWarehouseResponseList(List<WarehouseEntity> warehouseEntities);

    void mapUpdateRequestToWarehouseEntity(@MappingTarget WarehouseEntity warehouseEntity,
                                         UpdateWarehouseRequest updateWarehouseRequest);

}
