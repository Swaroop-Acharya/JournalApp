package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.Product;
import net.engineeringdigest.journalApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public  ResponseEntity<?> addProduct(@RequestBody Product product){
        try{
            productService.addProduct(product);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
