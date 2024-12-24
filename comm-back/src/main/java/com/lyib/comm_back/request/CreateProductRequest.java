package com.lyib.comm_back.request;

import java.util.HashSet;
import java.util.Set;

import com.lyib.comm_back.model.Size;

import lombok.Data;

@Data
public class CreateProductRequest {

  public String title;
  private String description;
  private int price;

  private int discountedPrice;

  private int discountedPercent;

  private int quantity;

  private String brand;

  private String colore;

  private Set<Size> size = new HashSet<>();

  private String imageUrl;

  private String topLavelCategory;
  private String secondLavelCategory;
  private String thirdLavelCategory;
}
