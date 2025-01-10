package com.prueba.nequi.entryendpoints;

import com.prueba.nequi.domain.model.dto.ProductBranchDto;
import com.prueba.nequi.domain.model.dto.ProductDto;
import com.prueba.nequi.domain.model.response.BasicResponse;
import com.prueba.nequi.domain.usecase.ProductUseCase;
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
    public Flux<ProductDto> getAll() {
        return productUseCase.findAllProducts();
    }

    @GetMapping("/{id}")
    public Mono<BasicResponse<ProductDto>> getById(@PathVariable Integer id) {
        return productUseCase.findByIdProduct(id);
    }

    @GetMapping("/top")
    public Flux<ProductBranchDto> getTopProducts(@RequestParam String name) {
        return productUseCase.getTopProducts(name);
    }

}
