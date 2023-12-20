package com.restaurant.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "total_price", nullable = false)
  Double totalPrice;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  List<OrderItem> orderItems;

  public void calculateTotalPrice() {
    totalPrice = (double) Math.round(orderItems.stream().mapToDouble(OrderItem::getTotalPrice).sum() * 100.0) / 100.0;
  }

  public boolean addOrderItem(OrderItem orderItem) {
    return orderItems.add(orderItem);
  }

}
