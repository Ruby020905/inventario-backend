package com.company.inventario.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventario.dao.ICategoryDao;
import com.company.inventario.model.Category;
import com.company.inventario.response.CategoryResponse;
import com.company.inventario.response.CategoryResponseRest;

@Service
public class CategoryServiceImpl implements ICategoryService{

	@Autowired
	private ICategoryDao categoryDao;
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> search() {
	    
	    CategoryResponseRest response = new CategoryResponseRest();
	    
	    try {
	        List<Category> categoryList = (List<Category>) categoryDao.findAll();
	        
	        // Paso 1: Inicializa el objeto CategoryResponse
	        CategoryResponse categoryResponse = new CategoryResponse();
	        
	        // Paso 2: Asigna la lista al objeto inicializado
	        categoryResponse.setCategory(categoryList);
	        
	        // Paso 3: Asigna el objeto inicializado a la respuesta principal
	        response.setCategoryResponse(categoryResponse);
	        
	        response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
	        
	    } catch(Exception e) {
	        response.setMetadata("Respuesta nok", "-1", "Error al consultar");
	        e.printStackTrace();
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
}