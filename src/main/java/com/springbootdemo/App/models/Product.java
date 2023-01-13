package com.springbootdemo.App.models;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 255)
    private String productName;

    @Column(nullable = false, length = 4)
    private int year;

    @Column(nullable = false, length = 20)
    private Double price;

    @Column(nullable = false, length = 512)
    private String url;
}
