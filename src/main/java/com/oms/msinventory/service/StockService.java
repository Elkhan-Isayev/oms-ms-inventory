package com.oms.msinventory.service;

import com.oms.msinventory.exception.custom.NotFoundException;
import com.oms.msinventory.mapper.StockMapper;
import com.oms.msinventory.model.entity.StockEntity;
import com.oms.msinventory.model.request.CreateStockRequest;
import com.oms.msinventory.model.request.UpdateStockRequest;
import com.oms.msinventory.model.response.StockResponse;
import com.oms.msinventory.repository.ProductRepository;
import com.oms.msinventory.repository.StockRepository;
import com.oms.msinventory.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;
    private final StockMapper stockMapper;

    public Long getAllStocksCount() {
        return stockRepository.count();
    }

    public List<StockResponse> getAll(int page, int size) {
        List<StockEntity> stocks = stockRepository.findAll(PageRequest.of(page - 1, size))
                .stream()
                .collect(Collectors.toList());

        if (stocks.isEmpty()) {
            throw new NotFoundException(String.format("StockService.getAll page = %s and size = %s", page, size));
        }

        return stockMapper.mapToStockResponseList(stocks);
    }

    public StockResponse createNewStock(CreateStockRequest createStockRequest) {
        var stockEntity = stockMapper.mapToStockEntity(createStockRequest);

        var product = productRepository.findById(createStockRequest.getProductId()).orElseThrow(
                () -> new NotFoundException(String.format("StockService.createNewStock productId = %s", createStockRequest.getProductId())));

        var warehouse = warehouseRepository.findById(createStockRequest.getWarehouseId()).orElseThrow(
                () -> new NotFoundException(String.format("StockService.createNewStock warehouseId = %s", createStockRequest.getWarehouseId())));

        stockEntity.setProduct(product);
        stockEntity.setWarehouse(warehouse);

        var createdStock = stockRepository.save(stockEntity);

        return stockMapper.mapToStockResponse(createdStock);
    }

    public void delete(Long id) {
        if (!stockRepository.existsById(id)) {
            throw new NotFoundException(String.format("StockService.delete id = %s", id));
        }

        stockRepository.deleteById(id);
    }

    public StockResponse update(Long id, UpdateStockRequest updateStockRequest) {
        var stock = stockRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("StockService.update id = %s", id)));

        var product = productRepository.findById(updateStockRequest.getProductId()).orElseThrow(
                () -> new NotFoundException(String.format("StockService.update productId = %s", updateStockRequest.getProductId())));

        var warehouse = warehouseRepository.findById(updateStockRequest.getWarehouseId()).orElseThrow(
                () -> new NotFoundException(String.format("StockService.update warehouseId = %s", updateStockRequest.getWarehouseId())));

        stockMapper.mapUpdateRequestToStockEntity(stock, updateStockRequest);

        stock.setProduct(product);
        stock.setWarehouse(warehouse);

        return stockMapper.mapToStockResponse(stockRepository.save(stock));
    }

}
