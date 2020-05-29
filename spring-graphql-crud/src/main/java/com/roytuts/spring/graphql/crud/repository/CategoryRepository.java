package com.roytuts.spring.graphql.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roytuts.spring.graphql.crud.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
