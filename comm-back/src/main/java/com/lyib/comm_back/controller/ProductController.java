package com.lyib.comm_back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lyib.comm_back.exception.ProductException;
import com.lyib.comm_back.model.Product;
import com.lyib.comm_back.service.ProductService;

@RestController
@RequestMapping("/app")
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping("/products")
  public ResponseEntity<Page<Product>> findProductByCategoryHandler(@RequestParam String category,
      @RequestParam List<String> colors, @RequestParam List<String> size, @RequestParam int minPrice,
      @RequestParam int maxPrice, @RequestParam int minDiscount, @RequestParam String sort,
      @RequestParam String stock, @RequestParam int pageNumber, @RequestParam int pageSize) {

    Page<Product> res = productService.getAllProduct(
        category, colors, size, minPrice, maxPrice,
        minDiscount, sort, stock, pageNumber, pageSize);

    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @GetMapping("/products/id/{productId}")
  public ResponseEntity<Product> findProductByHandler(@PathVariable Long productId) throws ProductException {

    Product product = productService.findProductById(productId);

    return new ResponseEntity<Product>(product, HttpStatus.OK);
  }
}
