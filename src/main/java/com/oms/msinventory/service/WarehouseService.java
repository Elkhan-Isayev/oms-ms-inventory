package com.oms.msinventory.service;

import com.oms.msinventory.exception.custom.NotFoundException;
import com.oms.msinventory.mapper.WarehouseMapper;
import com.oms.msinventory.model.entity.WarehouseEntity;
import com.oms.msinventory.model.request.CreateWarehouseRequest;
import com.oms.msinventory.model.request.UpdateWarehouseRequest;
import com.oms.msinventory.model.response.WarehouseResponse;
import com.oms.msinventory.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    public Long getAllWarehousesCount() {
        return warehouseRepository.count();
    }

    public List<WarehouseResponse> getAll(int page, int size) {
        List<WarehouseEntity> warehouses = warehouseRepository.findAll(PageRequest.of(page - 1, size))
                .stream()
                .collect(Collectors.toList());

        if (warehouses.isEmpty()) {
            throw new NotFoundException(String.format("WarehouseService.getAll page = %s and size = %s", page, size));
        }

        return warehouseMapper.mapToWarehouseResponseList(warehouses);
    }

    public WarehouseResponse createNewWarehouse(CreateWarehouseRequest createWarehouseRequest) {
        var warehouseEntity = warehouseMapper.mapToWarehouseEntity(createWarehouseRequest);

        var createdWarehouse = warehouseRepository.save(warehouseEntity);

        return warehouseMapper.mapToWarehouseResponse(createdWarehouse);
    }

    public void delete(Long id) {
        if (!warehouseRepository.existsById(id)) {
            throw new NotFoundException(String.format("WarehouseService.delete id = %s", id));
        }

        warehouseRepository.deleteById(id);
    }

    public WarehouseResponse update(Long id, UpdateWarehouseRequest updateWarehouseRequest) {
        var warehouse = warehouseRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("WarehouseService.update id = %s", id)));

        warehouseMapper.mapUpdateRequestToWarehouseEntity(warehouse, updateWarehouseRequest);

        return warehouseMapper.mapToWarehouseResponse(warehouseRepository.save(warehouse));
    }

}
