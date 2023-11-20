package br.com.spolador.dscommerce.controllers;

import br.com.spolador.dscommerce.dto.ProductDto;
import br.com.spolador.dscommerce.services.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable Long id){
        ProductDto dto = productService.findById(id);
        return ResponseEntity.ok(dto); // retorna 200
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> findAll(Pageable pageable){
        Page<ProductDto> dto = productService.findAll(pageable);
        return ResponseEntity.ok(dto); // retorna 200
    }

    @PostMapping
    public ResponseEntity<ProductDto> insert(@Valid @RequestBody ProductDto dto){
        dto = productService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto); // retorna 201
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id, @Valid @RequestBody ProductDto dto){
        dto = productService.update(id, dto);
        return ResponseEntity.ok(dto); // retorna 200
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
       productService.deleteById(id);
        return ResponseEntity.noContent().build(); // retorna 204
    }
}
