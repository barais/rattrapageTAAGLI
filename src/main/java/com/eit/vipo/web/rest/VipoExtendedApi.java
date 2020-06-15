package com.eit.vipo.web.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eit.vipo.domain.ImageProperty;
import com.eit.vipo.domain.Vipo;
import com.eit.vipo.domain.VipoEntry;
import com.eit.vipo.domain.extendedDomain.ProductData;
import com.eit.vipo.repository.ImagePropertyRepository;
import com.eit.vipo.repository.VipoEntryRepository;
import com.eit.vipo.repository.VipoRepository;
import com.eit.vipo.security.AuthoritiesConstants;
import com.eit.vipo.security.SecurityUtils;
import com.eit.vipo.service.VipoService;
import com.eit.vipo.service.customdto.VipoEntryCustomDto;

/**
 * REST controller for managing {@link com.eit.vipo.domain.Vipo}.
 */
@RestController
@RequestMapping("/api")
public class VipoExtendedApi {

	private final Logger log = LoggerFactory.getLogger(VipoExtendedApi.class);

	private final VipoService vipoService;
	private final VipoEntryRepository vipoEntryRepository;
	private final VipoRepository vipoRepository;
	private final ImagePropertyRepository imagePropertyRepository;

	public VipoExtendedApi(VipoService vipoService, VipoEntryRepository vipoEntryRepository,
			VipoRepository vipoRepository, ImagePropertyRepository imagePropertyRepository) {
		this.vipoService = vipoService;
		this.vipoEntryRepository = vipoEntryRepository;
		this.vipoRepository = vipoRepository;
		this.imagePropertyRepository = imagePropertyRepository;
	}

	@PostMapping("/pushvipoentries")
	@PreAuthorize("hasRole(\"" + AuthoritiesConstants.VIPO + "\")")
	@Transactional
	public void addVipoEntries(@Valid @RequestBody VipoEntryCustomDto vipoEntryCustomDto) throws URISyntaxException {
		Vipo v = vipoRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();

		VipoEntry entry = new VipoEntry();
		entry.setImageName(vipoEntryCustomDto.getFilename());
		entry.setRegisterDate(LocalDate.now());
		entry.setVipo(v);
		v.addEntries(entry);

		// log.debug("size " + vipoEntryCustomDto.getProps().size());
		vipoEntryCustomDto.getProps().forEach(e -> {
			ImageProperty p = new ImageProperty();
			p.setLabel(e.getLabel());
			p.setHeight(e.getCoordinates().getHeight().intValue());
			p.setWidth(e.getCoordinates().getWidth().intValue());
			p.setX(e.getCoordinates().getX().intValue());
			p.setY(e.getCoordinates().getY().intValue());
			p.sethVGColor(e.getCoordinates().getHvgColor());
			p.setEntry(entry);
			entry.addProps(p);
			this.imagePropertyRepository.save(p);
		});
		this.vipoEntryRepository.save(entry);
		this.vipoRepository.save(v);

		// log.debug(vipoEntryCustomDto.getFilename());

	}

	@GetMapping("/getProductData/{sublínea}/{color}")
	@PreAuthorize("hasRole(\"" + AuthoritiesConstants.VIPO + "\")")
	public List<ProductData> getProductData(@PathVariable(value = "sublínea") String sublínea,
			@PathVariable(value = "color") String color) throws IOException {
		FileInputStream file = new FileInputStream(new File("/tmp/EJEMPLOFICHEROCR.xlsx"));
		Workbook workbook = new XSSFWorkbook(file);
		Sheet sheet = workbook.getSheetAt(0);
		List<ProductData> res = new ArrayList<ProductData>();
		int i = 0;

		for (Row row : sheet) {
			if (i > 0) {
				Cell cell = row.getCell(4);
				Cell cell1 = row.getCell(17);
				String cell1data = cell.getStringCellValue();
				String cell2data = cell1.getStringCellValue();
				if (cell1data.toLowerCase().equals(sublínea.toLowerCase())
						&& cell2data.toLowerCase().contains(color.toLowerCase())) {
					ProductData data = new ProductData();
					data.setLocalDESC(row.getCell(0).getStringCellValue());
					data.setSubLneaID(row.getCell(4).getStringCellValue());
					data.setClaseDESC(row.getCell(7).getStringCellValue());
					data.setMarcaDESC(row.getCell(10).getStringCellValue());
					data.setModeloID(row.getCell(11).getStringCellValue());
					data.setSkuNmeroDeProductoID(new Double(row.getCell(12).getNumericCellValue()).longValue());
					data.setColorDESC(row.getCell(17).getStringCellValue());
					data.setTallaCODEXTERNO(new Double(row.getCell(19).getStringCellValue()).longValue());
					data.setPrecioVigentePRECIOVGTPRMD(new Double(row.getCell(22).getNumericCellValue()).longValue());
					data.setPrecioNormalID(new Double(row.getCell(23).getNumericCellValue()).longValue());
					data.setStockDisponibleEnUnidades("" + row.getCell(26).getNumericCellValue());
					res.add(data);
					if (res.size() == 10) {
						workbook.close();
						file.close();
						return res;
					}
				}
			}
			i = i + 1;
		}
		workbook.close();
		file.close();
		return res;

	}

	@GetMapping("/getProductImage/{country}/{ref}")
	@PreAuthorize("hasRole(\"" + AuthoritiesConstants.VIPO + "\")")
	public @ResponseBody byte[] getImage(@PathVariable(value = "country") String country, @PathVariable(value = "ref") String ref) throws IOException {
		/*
		 * For Colombia



https://s7d5.scene7.com/is/image/FalabellaCO/4204993



for Perú



https://s7d5.scene7.com/is/image/FalabellaPE/4204993



For Argentina

https://s7d5.scene7.com/is/image/FalabellaAR/4204993



For chile

https://s7d5.scene7.com/is/image/Falabella/4204993
		 */
		
		URL url = new URL("https://s7d5.scene7.com/is/image/Falabella/"+ref+"?scl=1&qlt=100,1&cache=off");
		if ("argentina".equals(country.toLowerCase())){
			url = new URL("https://s7d5.scene7.com/is/image/FalabellaAR/"+ref+"?scl=1&qlt=100,1&cache=off");				
		}else 	if ("peru".equals(country.toLowerCase())){
			url = new URL("https://s7d5.scene7.com/is/image/FalabellaPE/"+ref+"?scl=1&qlt=100,1&cache=off");				
		}
		else if ("colombia".equals(country.toLowerCase())){
			url = new URL("https://s7d5.scene7.com/is/image/FalabellaCO/"+ref+"?scl=1&qlt=100,1&cache=off");				
		}
		URLConnection yc = url.openConnection();
		return IOUtils.toByteArray(yc.getInputStream());
	}
}
