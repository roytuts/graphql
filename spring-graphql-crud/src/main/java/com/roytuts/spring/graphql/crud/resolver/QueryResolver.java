package com.roytuts.spring.graphql.crud.resolver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.roytuts.spring.graphql.crud.entity.Category;
import com.roytuts.spring.graphql.crud.entity.Product;
import com.roytuts.spring.graphql.crud.repository.CategoryRepository;
import com.roytuts.spring.graphql.crud.repository.ProductRepository;

@Component
public class QueryResolver implements GraphQLQueryResolver {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	public List<Category> allCategories() {
		return categoryRepository.findAll();
	}

	public List<Product> allProducts() {
		return productRepository.findAll();
	}

	public Category category(Integer id) {
		return categoryRepository.findById(id).orElseGet(null);
	}

	public Product product(Integer id) {
		return productRepository.findById(id).orElseGet(null);
	}

}
