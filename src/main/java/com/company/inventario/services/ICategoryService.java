package com.company.inventario.services;

import org.springframework.http.ResponseEntity;

import com.company.inventario.response.CategoryResponseRest;

public interface ICategoryService {

	public ResponseEntity<CategoryResponseRest> search();
	public ResponseEntity<CategoryResponseRest> searchById(Long id);

}
