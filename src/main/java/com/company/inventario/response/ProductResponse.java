package com.company.inventario.response;

import java.util.List;

import com.company.inventario.model.Product;

import lombok.Data;

@Data
public class ProductResponse {

	List<Product> products;
}
