package com.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "OrderItems")
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @ManyToOne
  @JoinColumn(name = "menu_id", nullable = false, referencedColumnName = "id")
  Menu menu;

  @Column(name = "order_time")
  Date orderTime;

  @Column(name = "total_price")
  Double totalPrice;

  Integer quantity;

  @ManyToOne
  @JoinColumn(name = "order_id", referencedColumnName = "id")
  @JsonIgnore
  Order order;

  public void calculateTotalPrice() {
    totalPrice = (double) Math.round(menu.getPrice() * quantity * 100.0) / 100.0;
  }

}
