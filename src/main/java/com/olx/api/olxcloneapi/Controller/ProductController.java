package com.olx.api.olxcloneapi.Controller;

import com.olx.api.olxcloneapi.Entity.Product;
import com.olx.api.olxcloneapi.Model.ProductModal;
import com.olx.api.olxcloneapi.Repository.ProductRepository;
import com.olx.api.olxcloneapi.Service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController

public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private String uploadDir="C:\\Photos";


    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestParam("name") String name,
                                           @RequestParam("category") String category,
                                           @RequestParam("price") String price,
                                           @RequestParam("email") String email,
                                           @RequestParam("image") MultipartFile image) {
        try {
            if (image.isEmpty()) {
                System.out.println("Image is empty");
                return ResponseEntity.badRequest().body("Please select an image to upload");
            }
            ProductModal product = new ProductModal();
            product.setName(name);
            product.setCategory(category);
            product.setPrice(price);
            product.setImage(image);
            product.setEmail(email);
           Product savedProduct= productService.createProduct(product);

            // Return the saved product
            System.out.println("Saved product: " + savedProduct);
            return ResponseEntity.ok(savedProduct);
        } catch (IOException e) {
            System.out.println("Error uploading image: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    @GetMapping("/products")
    public ResponseEntity<?> productsList(){
        try {

            List<Product> products=productService.findAllProducts();
        return  ResponseEntity.ok(products);

        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.badRequest().body("server error");
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable("productId") Long productId){
        try {
            Optional<Product> product=productService.findProductById(productId);
            if(product.isEmpty()){
                return ResponseEntity.badRequest().body("product not fount ");
            }
            return ResponseEntity.ok(product.get());
        }catch (Exception e){
            System.out.println(e);

            return ResponseEntity.badRequest().body("some thing wrong");
        }

    }
}
