package com.lyib.comm_back.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lyib.comm_back.exception.ProductException;
import com.lyib.comm_back.model.Product;
import com.lyib.comm_back.request.CreateProductRequest;

public interface ProductService {

  public Product createProduct(CreateProductRequest request);

  public String deleteProduct(Long productId) throws ProductException;

  public Product updateProduct(Long productId, Product request) throws ProductException;

  public Product findProductById(Long productId) throws ProductException;

  public List<Product> findProductByCategory(String category);

  public List<Product> findAllProducts();

  public Page<Product> getAllProduct(String category, List<String> colors, List<String> size, int minPrice,
      int maxPrice, int minDiscount, String sort, String stock, int pageNumber, int pageSize);

}
