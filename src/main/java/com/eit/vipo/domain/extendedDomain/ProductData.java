package com.eit.vipo.domain.extendedDomain;

import java.util.*;
import com.fasterxml.jackson.annotation.*;

public class ProductData {
    private String localDESC;
    private String subLneaID;
    private String claseDESC;
    private String marcaDESC;
    private String modeloID;
    private Long skuNmeroDeProductoID;
    private String colorDESC;
    private Long tallaCODEXTERNO;
    private Long precioVigentePRECIOVGTPRMD;
    private Long precioNormalID;
    private String stockDisponibleEnUnidades;

    @JsonProperty("Local DESC")
    public String getLocalDESC() { return localDESC; }
    @JsonProperty("Local DESC")
    public void setLocalDESC(String value) { this.localDESC = value; }

    @JsonProperty("SubL\u00ednea ID")
    public String getSubLneaID() { return subLneaID; }
    @JsonProperty("SubL\u00ednea ID")
    public void setSubLneaID(String value) { this.subLneaID = value; }

    @JsonProperty("Clase DESC")
    public String getClaseDESC() { return claseDESC; }
    @JsonProperty("Clase DESC")
    public void setClaseDESC(String value) { this.claseDESC = value; }

    @JsonProperty("Marca DESC")
    public String getMarcaDESC() { return marcaDESC; }
    @JsonProperty("Marca DESC")
    public void setMarcaDESC(String value) { this.marcaDESC = value; }

    @JsonProperty("Modelo ID")
    public String getModeloID() { return modeloID; }
    @JsonProperty("Modelo ID")
    public void setModeloID(String value) { this.modeloID = value; }

    @JsonProperty("SKU (N\u00famero de Producto) ID")
    public Long getSkuNmeroDeProductoID() { return skuNmeroDeProductoID; }
    @JsonProperty("SKU (N\u00famero de Producto) ID")
    public void setSkuNmeroDeProductoID(Long value) { this.skuNmeroDeProductoID = value; }

    @JsonProperty("Color DESC")
    public String getColorDESC() { return colorDESC; }
    @JsonProperty("Color DESC")
    public void setColorDESC(String value) { this.colorDESC = value; }

    @JsonProperty("Talla COD_EXTERNO")
    public Long getTallaCODEXTERNO() { return tallaCODEXTERNO; }
    @JsonProperty("Talla COD_EXTERNO")
    public void setTallaCODEXTERNO(Long value) { this.tallaCODEXTERNO = value; }

    @JsonProperty("Precio Vigente PRECIO_VGT_PRMD")
    public Long getPrecioVigentePRECIOVGTPRMD() { return precioVigentePRECIOVGTPRMD; }
    @JsonProperty("Precio Vigente PRECIO_VGT_PRMD")
    public void setPrecioVigentePRECIOVGTPRMD(Long value) { this.precioVigentePRECIOVGTPRMD = value; }

    @JsonProperty("Precio Normal ID")
    public Long getPrecioNormalID() { return precioNormalID; }
    @JsonProperty("Precio Normal ID")
    public void setPrecioNormalID(Long value) { this.precioNormalID = value; }

    @JsonProperty("Stock Disponible en Unidades")
    public String getStockDisponibleEnUnidades() { return stockDisponibleEnUnidades; }
    @JsonProperty("Stock Disponible en Unidades")
    public void setStockDisponibleEnUnidades(String value) { this.stockDisponibleEnUnidades = value; }
}
