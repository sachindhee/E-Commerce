package com.globalkart.Service;

import com.globalkart.Repository.ProductRepository;
import com.globalkart.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository =productRepository ;
    }

    public Product addProduct(Product product){
        return  productRepository.save(product);
    }

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }



}
