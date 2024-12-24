package com.lyib.comm_back.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lyib.comm_back.exception.ProductException;
import com.lyib.comm_back.model.Category;
import com.lyib.comm_back.model.Product;
import com.lyib.comm_back.repository.CategoryRepository;
import com.lyib.comm_back.repository.ProductRepository;
import com.lyib.comm_back.request.CreateProductRequest;

@Service
public class ProductServiceImplementation implements ProductService {

  private ProductRepository productRepository;
  private UserService userService;
  private CategoryRepository categoryRepository;

  @Autowired
  ProductServiceImplementation(ProductRepository productRepository, UserService userService,
      CategoryRepository categoryRepository) {
    this.productRepository = productRepository;
    this.userService = userService;
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Product createProduct(CreateProductRequest request) {
    Category topLevel = categoryRepository.findByName(request.getTopLavelCategory());

    if (topLevel == null) {
      Category topLevelCategory = new Category();
      topLevelCategory.setName(request.getTopLavelCategory());
      topLevelCategory.setLevel(1);

      topLevel = categoryRepository.save(topLevelCategory);
    }
    Category secondLevel = categoryRepository.findByNameAndParent(request.getSecondLavelCategory(),
        topLevel.getName());
    ;
    if (secondLevel == null) {
      Category secondLevelCategory = new Category();
      secondLevelCategory.setName(request.getSecondLavelCategory());
      secondLevelCategory.setParentCategory(topLevel);
      secondLevelCategory.setLevel(2);

      secondLevel = categoryRepository.save(secondLevelCategory);
    }
    Category thirdLevel = categoryRepository.findByNameAndParent(request.getThirdLavelCategory(),
        secondLevel.getName());
    if (thirdLevel == null) {
      Category thirdLevelCategory = new Category();
      thirdLevelCategory.setName(request.getThirdLavelCategory());
      thirdLevelCategory.setParentCategory(secondLevel);
      thirdLevelCategory.setLevel(3);

      thirdLevel = categoryRepository.save(thirdLevelCategory);
    }

    Product product = new Product();
    product.setTitle(request.getTitle());
    product.setColor(request.getColore());
    product.setDescription(request.getDescription());
    product.setDiscountPercent(request.getDiscountedPercent());
    product.setDiscountedPrice(request.getDiscountedPrice());
    product.setImageUrl(request.getImageUrl());
    product.setBrand(request.getBrand());
    product.setPrice(request.getPrice());
    product.setSizes(request.getSize());
    product.setQuantity(request.getQuantity());
    product.setCategory(thirdLevel);
    product.setCreatedAt(LocalDateTime.now());

    Product savedProduct = productRepository.save(product);

    return savedProduct;
  }

  @Override
  public String deleteProduct(Long productId) throws ProductException {
    Product product = findProductById(productId);
    product.getSizes().clear();
    productRepository.delete(product);
    return "Продукт был удален";
  }

  @Override
  public Product updateProduct(Long productId, Product request) throws ProductException {
    Product product = findProductById(productId);

    if (request.getQuantity() != 0) {
      product.setQuantity(request.getQuantity());
    }

    return productRepository.save(product);
  }

  @Override
  public Product findProductById(Long productId) throws ProductException {
    Optional<Product> optional = productRepository.findById(productId);

    if (optional.isPresent()) {
      return optional.get();
    }
    throw new ProductException("Продукт с id Не найден" + productId);
  }

  @Override
  public List<Product> findProductByCategory(String category) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findProductByCategory'");
  }

  @Override
  public Page<Product> getAllProduct(String category, List<String> colors, List<String> size, int minPrice,
      int maxPrice, int minDiscount, String sort, String stock, int pageNumber, int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);

    if (!colors.isEmpty()) {
      products = products.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
          .collect(Collectors.toList());
    }

    if (stock != null) {
      if (stock.equals("in_stock")) {
        products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
      } else if (stock.equals("out_of_stock")) {
        products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
      }
    }

    int startIndex = (int) pageable.getOffset();
    int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

    List<Product> pageContent = products.subList(startIndex, endIndex);

    Page<Product> filterProducts = new PageImpl<>(pageContent, pageable, products.size());

    return filterProducts;
  }

  @Override
  public List<Product> findAllProducts() {
    return productRepository.findAll();
  }

}
