package com.oms.msinventory.controller;

import com.oms.msinventory.exception.custom.BadRequestException;
import com.oms.msinventory.model.request.CreateWarehouseRequest;
import com.oms.msinventory.model.request.UpdateWarehouseRequest;
import com.oms.msinventory.model.response.WarehouseResponse;
import com.oms.msinventory.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    public WarehouseResponse create(@RequestBody CreateWarehouseRequest createWarehouseRequest) {
        return warehouseService.createNewWarehouse(createWarehouseRequest);
    }

    @PutMapping("/{id}")
    public WarehouseResponse update(@PathVariable Long id,
                                    @RequestBody UpdateWarehouseRequest updateWarehouseRequest) {
        if (ObjectUtils.isEmpty(updateWarehouseRequest) || ObjectUtils.isEmpty(id)) {
            throw new BadRequestException(String.format("WarehouseController.update body = %s", updateWarehouseRequest));
        }
        return warehouseService.update(id, updateWarehouseRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        warehouseService.delete(id);
    }

    @GetMapping
    public List<WarehouseResponse> getAll(@RequestParam(value="page", defaultValue="1") int page,
                                          @RequestParam(value="size", defaultValue="10") int size) {
        if (page <= 0 || size <= 0) {
            throw new BadRequestException(String.format("WarehouseController.getAll page = %s size = %s", page, size));
        }

        return warehouseService.getAll(page, size);
    }

    @GetMapping("/count")
    public Long getAllWarehousesCount() {
        return warehouseService.getAllWarehousesCount();
    }

}
