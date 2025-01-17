package com.oms.msinventory.service;

import com.oms.msinventory.exception.custom.NotFoundException;
import com.oms.msinventory.mapper.ProductMapper;
import com.oms.msinventory.model.entity.ProductEntity;
import com.oms.msinventory.model.request.CreateProductRequest;
import com.oms.msinventory.model.request.UpdateProductRequest;
import com.oms.msinventory.model.response.ProductResponse;
import com.oms.msinventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Long getAllProductsCount() {
        return productRepository.count();
    }

    public List<ProductResponse> getAll(int page, int size) {
        List<ProductEntity> products = productRepository.findAll(PageRequest.of(page - 1, size))
                .stream()
                .collect(Collectors.toList());

        if (products.isEmpty()) {
            throw new NotFoundException(String.format("ProductService.getAll page = %s and size = %s", page, size));
        }

        return productMapper.mapToProductResponseList(products);
    }

    public ProductResponse createNewProduct(CreateProductRequest createProductRequest) {
        var productEntity = productMapper.mapToProductEntity(createProductRequest);

        var createdProduct = productRepository.save(productEntity);

        return productMapper.mapToProductResponse(createdProduct);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException(String.format("ProductService.delete id = %s", id));
        }

        productRepository.deleteById(id);
    }

    public ProductResponse update(Long id, UpdateProductRequest updateProductRequest) {
        var product = productRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("ProductService.update id = %s", id)));

        productMapper.mapUpdateRequestToProductEntity(product, updateProductRequest);

        return productMapper.mapToProductResponse(productRepository.save(product));
    }
}
