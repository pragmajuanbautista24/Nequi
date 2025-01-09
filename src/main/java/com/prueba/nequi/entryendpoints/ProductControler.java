package com.prueba.nequi.entryendpoints;

import com.prueba.nequi.domain.model.dto.ProductBranchDto;
import com.prueba.nequi.domain.model.response.BasicResponse;
import com.prueba.nequi.domain.usecase.ProductUseCase;
import com.prueba.nequi.providers.entity.Product;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/productos", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductControler {

    private final ProductUseCase productUseCase;

    public ProductControler(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @GetMapping
    public Flux<Product> getAll() {
        return productUseCase.findAllProducts();
    }

    @GetMapping("/{id}")
    public Mono<BasicResponse<Product>> getById(@PathVariable Integer id) {
        return productUseCase.findByIdProduct(id);
    }

    @PostMapping
    public Mono<BasicResponse<Product>> create(@RequestBody Product product) {
        return productUseCase.saveProduct(product);
    }

    @PutMapping("/{id}")
    public Mono<BasicResponse<Product>> update(@PathVariable Integer id, @RequestBody Product product) {
        return productUseCase.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public Mono<BasicResponse<Product>> delete(@PathVariable Integer id) {
        return productUseCase.deleteByIdProduct(id);
    }

    @GetMapping("/top")
    public Flux<ProductBranchDto> getTopProducts(@RequestParam String name){
        return productUseCase.getTopProducts(name);
    }

}
