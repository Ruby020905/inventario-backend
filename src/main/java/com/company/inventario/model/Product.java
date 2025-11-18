package com.company.inventario.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="product")
public class Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -431002722752446841L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String account;
	private String type;
	private Date date;
	private int stock;

	@ManyToOne(fetch= FetchType.LAZY)
	@JsonIgnoreProperties ({"hibernateLazyInitializer" , "handler"})
	private Category category;
		
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name="picture" ,columnDefinition = "longblob") 
	private byte[] picture;

	
}
