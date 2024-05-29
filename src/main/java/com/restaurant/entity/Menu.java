package com.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Menus")
public class Menu {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "name", nullable = false)
  @NotNull(message = "Name is required!")
  @NotEmpty(message = "Name is required!")
  @Pattern(regexp = ".*\\S+.*", message = "Name is required")
  @Size(min = 2, message = "Name should have at least 2 characters!")
  String name;

  @URL(message = "Link image must be a valid URL!")
  @Column(name = "image", nullable = false)
  String image;

  @Column(name = "price", nullable = false)
  @NotNull(message = "Price is required!")
  @Positive(message = "Price must be greater than 0!")
  Double price;

  @Column(name = "additional_details")
  @NotNull(message = "Additional details is required!")
  @NotEmpty(message = "Additional details is required!")
  @Pattern(regexp = ".*\\S+.*", message = "Additional details is required!")
  String additionalDetails;

  @Column(name = "description")
  String description;

  @Column(name = "is_active", columnDefinition = "boolean default true")
  @JsonIgnore
  Boolean isActive;

  @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
  @JsonIgnore
  List<OrderItem> orderItems;

}
