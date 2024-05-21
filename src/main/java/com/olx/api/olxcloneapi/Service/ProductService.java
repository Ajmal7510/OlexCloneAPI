package com.olx.api.olxcloneapi.Service;

import com.olx.api.olxcloneapi.Entity.Product;
import com.olx.api.olxcloneapi.Model.ProductModal;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
    Product saveProduct(Product product);

    Product createProduct(ProductModal product) throws IOException;

    List<Product> findAllProducts();

    Optional<Product> findProductById(Long productId);
}
