package com.oms.msinventory.controller;

import com.oms.msinventory.exception.custom.BadRequestException;
import com.oms.msinventory.model.request.CreateStockRequest;
import com.oms.msinventory.model.request.UpdateStockRequest;
import com.oms.msinventory.model.response.StockResponse;
import com.oms.msinventory.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping
    public StockResponse create(@RequestBody CreateStockRequest createStockRequest) {
        return stockService.createNewStock(createStockRequest);
    }

    @PutMapping("/{id}")
    public StockResponse update(@PathVariable Long id,
                                @RequestBody UpdateStockRequest updateStockRequest) {
        if (ObjectUtils.isEmpty(updateStockRequest) || ObjectUtils.isEmpty(id)) {
            throw new BadRequestException(String.format("StockController.update body = %s", updateStockRequest));
        }
        return stockService.update(id, updateStockRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        stockService.delete(id);
    }

    @GetMapping
    public List<StockResponse> getAll(@RequestParam(value="page", defaultValue="1") int page,
                                      @RequestParam(value="size", defaultValue="10") int size) {
        if (page <= 0 || size <= 0) {
            throw new BadRequestException(String.format("StockController.getAll page = %s size = %s", page, size));
        }

        return stockService.getAll(page, size);
    }

    @GetMapping("/count")
    public Long getAllStocksCount() {
        return stockService.getAllStocksCount();
    }

}
