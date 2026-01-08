package com.company.inventory.util;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import com.company.inventario.model.Category;
import com.company.inventario.model.Product;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.io.IOException;
import java.util.Date;
import java.util.List;
public class ProductExcelExporter {

private XSSFWorkbook workbook;
	
	private XSSFSheet sheet;
	
	private List  <Product> products;
	
	public ProductExcelExporter(List<Product> products) {
		this.products= products;
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
		createCell(row, 3, "Clase de Medicamento", style);
		createCell(row, 4, "Presentacion", style);
		createCell(row, 5, "Unidad", style);
		createCell(row, 6, "Cantidad", style);
		createCell(row, 7, "Fecha", style);
		
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
	 // Dentro de writeDataLines, antes del for:
	 // --- NUEVO: Estilo específico para FECHAS ---
	    CellStyle dateStyle = workbook.createCellStyle();
	    dateStyle.setFont(font);
	    // Asigna el formato yyyy-mm-dd (o dd/mm/yyyy según prefieras)
	    dateStyle.setDataFormat(workbook.createDataFormat().getFormat("yyyy-mm-dd"));

	    // Y dentro del for, para la fecha usa:
	    
	    
	    for (Product result : products) {
	        Row row = sheet.createRow(rowCount++);
	        
	        // Asignación manual de columnas para que coincidan con el Header
	        createCell(row, 0, result.getId(), style);                  // ID
	        createCell(row, 1, result.getName(), style);                // Nombre
	        // La columna 2 queda vacía o puedes poner la Categoría ahí
	        createCell(row, 3, result.getCategory().getName(), style);  // Clase de Medicamento
	        createCell(row, 4, result.getType(), style);                // Presentacion
	        createCell(row, 5, result.getAccount(), style);             // Unidad
	        createCell(row, 6, result.getStock(), style);               // Cantidad (Cambié el orden para que coincida)
	        createCell(row, 7, result.getDate(), dateStyle);               // Fecha
	        
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
