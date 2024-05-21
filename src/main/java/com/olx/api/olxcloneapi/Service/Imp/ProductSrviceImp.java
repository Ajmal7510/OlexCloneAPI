package com.olx.api.olxcloneapi.Service.Imp;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.olx.api.olxcloneapi.Entity.Product;
import com.olx.api.olxcloneapi.Entity.Users;
import com.olx.api.olxcloneapi.Model.ProductModal;
import com.olx.api.olxcloneapi.Repository.ProductRepository;
import com.olx.api.olxcloneapi.Repository.UserRepository;
import com.olx.api.olxcloneapi.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductSrviceImp implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AmazonS3 amazonS3ClintAmazonS3;

    @Value("${s3.bucketName}")
    private String bucketName;

    private String uploadDir="https://spring-ecommerce.s3.eu-north-1.amazonaws.com/";

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product createProduct(ProductModal productModel) {
        String fileName = UUID.randomUUID().toString() + "_" + productModel.getImage().getOriginalFilename();
        System.out.println("File name: " + fileName);

        try {
            // Upload the file to S3
            amazonS3ClintAmazonS3.putObject(new PutObjectRequest(bucketName, fileName, productModel.getImage().getInputStream(), new ObjectMetadata()));

            // Construct the URL of the stored image
            String imageUrl = uploadDir  + fileName;

            Users user = userRepository.findByEmail(productModel.getEmail());

            // Create a new Product object with the provided data
            Product product = new Product();
            product.setName(productModel.getName());
            product.setCategory(productModel.getCategory());
            product.setPrice(Double.parseDouble(productModel.getPrice()));
            product.setImageUrl(imageUrl);
            product.setUser(user);
            product.setCreateAt(LocalDate.now());

            // Save the product to the database
            Product savedProduct = productRepository.save(product);

            return savedProduct;

        } catch (IOException e) {
            // Handle the exception (e.g., log or throw a custom exception)
            e.printStackTrace(); // Log the exception
            throw new RuntimeException("Failed to upload image to S3"); // Throw a custom exception
        }
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findProductById(Long productId) {
        return productRepository.findById(productId);
    }
}
