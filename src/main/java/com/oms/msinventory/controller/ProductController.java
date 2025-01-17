package com.oms.msinventory.controller;

import com.oms.msinventory.exception.custom.BadRequestException;
import com.oms.msinventory.model.request.CreateProductRequest;
import com.oms.msinventory.model.request.UpdateProductRequest;
import com.oms.msinventory.model.response.ProductResponse;
import com.oms.msinventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponse create(@RequestBody CreateProductRequest createProductRequest) {
        return productService.createNewProduct(createProductRequest);
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id,
                                  @RequestBody UpdateProductRequest updateProductRequest) {
        if (ObjectUtils.isEmpty(updateProductRequest) || ObjectUtils.isEmpty(id)) {
            throw new BadRequestException(String.format("ProductController.update body = %s", updateProductRequest));
        }
        return productService.update(id, updateProductRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping
    public List<ProductResponse> getAll(@RequestParam(value="page", defaultValue="1") int page,
                                        @RequestParam(value="size", defaultValue="10") int size) {
        if (page <= 0 || size <= 0) {
            throw new BadRequestException(String.format("ProductController.getAll page = %s size = %s", page, size));
        }

        return productService.getAll(page, size);
    }

    @GetMapping("/count")
    public Long getAllProductsCount() {
        return productService.getAllProductsCount();
    }

}
