package com.globalkart.Controller;

import com.globalkart.Service.ProductService;
import com.globalkart.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService=productService;
    }
    @PostMapping("/add/product")
    public Product addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }


    @GetMapping("/get/all")
    public List<Product> getAllProduct(){
      return   productService.getAllProduct();
    }

}
