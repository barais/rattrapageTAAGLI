package com.eit.vipo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import com.eit.vipo.web.rest.TestUtil;

public class VipoTest {

	@Test
	public void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(Vipo.class);
		Vipo vipo1 = new Vipo();
		vipo1.setId(1L);
		Vipo vipo2 = new Vipo();
		vipo2.setId(vipo1.getId());
		assertThat(vipo1).isEqualTo(vipo2);
		vipo2.setId(2L);
		assertThat(vipo1).isNotEqualTo(vipo2);
		vipo1.setId(null);
		assertThat(vipo1).isNotEqualTo(vipo2);
	}

	@Test
	public void givenCSVFile_whenRead_thenContentsAsExpected() {

/*		try {
			System.err.println(getStock("6925281924774"));
			System.err.println(getStock("816479014314"));
			System.err.println(getStock("816479014307"));
			System.err.println(getStock("8806086689410"));
			System.err.println(getStock("4548736013469"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	private long getStock(String ref) throws IOException {
		Reader in = new InputStreamReader(new FileInputStream("/tmp/inf_repovta-10001-20200226.txt"),
				StandardCharsets.ISO_8859_1);

//        	Reader in = new FileReader("/tmp/inf_repovta-10001-20200226.txt");
		BufferedReader brTest = new BufferedReader(in);

		int header = 0;
		String s = "";
		while (header != 4) {
			s = brTest.readLine();
			if (s != null && s.contains("<HEADER>"))
				header = header + 1;
		}
//            System.err.println(s);
		String s1 = brTest.readLine();

		while (s1 != null && !s1.contains("<DATOS>")) {
			s = s1;
			s1 = brTest.readLine();
		}

		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withDelimiter('|').withHeader(s.split("\\|")).parse(brTest);

		for (CSVRecord record : records) {

			if (record.get("EAN").equals(ref)) {
				return Long.parseLong(record.get("Stock en la Ubicación"));
			}

		}
		return -1;

	}

	@Test
	public void givenXLSXFile_whenRead_thenContentsAsExpected() {

		try {
			System.err.println(getPrice("2002768673005"));
//			System.err.println(getPrice("816479014314"));
//			System.err.println(getPrice("816479014307"));
//			System.err.println(getPrice("8806086689410"));
//			System.err.println(getPrice("4548736013469"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getPrice(String ref) throws IOException {
		FileInputStream file = new FileInputStream(new File("/tmp/EJEMPLOFICHEROCR.xlsx"));
		Workbook workbook = new XSSFWorkbook(file);
		Sheet sheet = workbook.getSheetAt(0);
		int i = 0;
		
		for (Row row : sheet) {
			if (i > 0) {
				Cell cell = row.getCell(14);
				if (cell.getNumericCellValue() == Double.parseDouble(ref)) {
					return row.getCell(16).getStringCellValue();
				}
				
			}
			i = i + 1;
		}
		return "-1";
		/*
		 * switch (cell.getCellTypeEnum()) { case STRING:
		 * System.err.println(cell.getStringCellValue()); break; case NUMERIC:
		 * System.err.println(cell.getNumericCellValue()); break; case BOOLEAN:
		 * System.err.println(cell.getBooleanCellValue()); break; case FORMULA:
		 * System.err.println(cell.getStringCellValue()); break; // default:
		 * data.get(new Integer(i)).add(" "); }
		 */

	}

}
