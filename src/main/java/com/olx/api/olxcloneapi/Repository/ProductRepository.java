package com.olx.api.olxcloneapi.Repository;

import com.olx.api.olxcloneapi.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {


}
