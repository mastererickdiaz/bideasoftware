package com.bidea.app.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "book")
public class BookEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String lastname;

  @Column(nullable = false)
  private BigDecimal age;

  @Column(nullable = false)
  private String phoneNumber;

  @Column(name = "startDate", nullable = false)
  @Temporal(TemporalType.DATE)
  private LocalDate startDate;

  @Column(name = "endDate", nullable = false)
  @Temporal(TemporalType.DATE)
  private LocalDate endDate;

  @Column(nullable = false)
  private String houseId;

  @Column(nullable = false)
  private String discountCode;
}
