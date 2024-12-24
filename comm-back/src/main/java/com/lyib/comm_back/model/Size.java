package com.lyib.comm_back.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class Size {
  // TODO Если не работает, то добавить сеттеры геттеры

  private String name;
  private int quantity;

  public Size() {
  }

}
