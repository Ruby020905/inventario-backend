package com.company.inventario.services;

import org.springframework.http.ResponseEntity;

import com.company.inventario.model.Product;
import com.company.inventario.response.ProductResponseRest;


public interface IProductService {

	public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId);
	public ResponseEntity<ProductResponseRest> searchById(Long id);
}
