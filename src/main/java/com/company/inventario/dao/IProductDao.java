package com.company.inventario.dao;

import org.springframework.data.repository.CrudRepository;

import com.company.inventario.model.Product;

public interface IProductDao extends CrudRepository<Product, Long> {

}
