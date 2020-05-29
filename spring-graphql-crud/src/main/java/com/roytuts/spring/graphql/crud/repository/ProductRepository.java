package com.roytuts.spring.graphql.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roytuts.spring.graphql.crud.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
