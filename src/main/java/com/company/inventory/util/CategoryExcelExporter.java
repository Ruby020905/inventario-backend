package com.company.inventory.util;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import com.company.inventario.model.Category;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.io.IOException;
import java.util.List;

public class CategoryExcelExporter {
	
	private XSSFWorkbook workbook;
	
	private XSSFSheet sheet;
	
	private List  <Category> category;
	
	public CategoryExcelExporter(List<Category> categories) {
		this.category =categories;
		workbook= new XSSFWorkbook();
	}
	
	private void writeHeaderLine() {
		sheet = workbook.createSheet("Resultado");
		Row row = sheet.createRow(0);
		CellStyle style = workbook.createCellStyle();
		
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		createCell(row, 0, "ID", style);
		createCell(row, 1, "Nombre", style);
		createCell(row, 2, "Descripcion", style);

		
	}
	
	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
	    sheet.autoSizeColumn(columnCount);
	    Cell cell = row.createCell(columnCount);
	    
	    // Agregamos la validación para Long e Integer
	    if(value instanceof Integer) {
	        cell.setCellValue((Integer)value);
	    } else if(value instanceof Long) {
	        cell.setCellValue((Long)value); // ✅ Esto soluciona el ClassCastException
	    } else if (value instanceof Boolean) {
	        cell.setCellValue((Boolean)value);
	    } else {
	        // Si no es ninguno de los anteriores, lo tratamos como String de forma segura
	        cell.setCellValue(String.valueOf(value)); 
	    }
	    cell.setCellStyle(style);
	}
	
	private void writeDataLines() {
		
		int rowCount = 1;
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		
		for (Category result: category) {
			
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			createCell(row, columnCount++, result.getId(), style);
			createCell(row, columnCount++, result.getName(), style);
			createCell(row, columnCount++, result.getDescription(), style);

			
		}
		
	}
	
	public void export(HttpServletResponse response) throws IOException {
	    writeHeaderLine();
	    writeDataLines();
	    
	    // DECLARACIÓN: Es vital que esta línea esté presente
	    ServletOutputStream servletOutput = response.getOutputStream(); 
	    
	    workbook.write(servletOutput);
	    workbook.close();
	    
	    // CIERRE: Ahora ya no debería marcar error
	    servletOutput.close();
	}

}
