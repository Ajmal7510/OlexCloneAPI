package com.olx.api.olxcloneapi.Model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductModal {
    private String name;
    private String category;
    private  String price;
    private MultipartFile image;
    private String email;
}
