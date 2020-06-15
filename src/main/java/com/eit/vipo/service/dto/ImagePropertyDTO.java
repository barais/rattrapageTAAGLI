package com.eit.vipo.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.eit.vipo.domain.ImageProperty} entity.
 */
public class ImagePropertyDTO implements Serializable {

    private Long id;

    @NotNull
    private String label;

    @NotNull
    private Integer height;

    @NotNull
    private Integer width;

    @NotNull
    private Integer x;

    @NotNull
    private Integer y;

    private String hVGColor;


    private Long entryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String gethVGColor() {
        return hVGColor;
    }

    public void sethVGColor(String hVGColor) {
        this.hVGColor = hVGColor;
    }

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long vipoEntryId) {
        this.entryId = vipoEntryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImagePropertyDTO imagePropertyDTO = (ImagePropertyDTO) o;
        if (imagePropertyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), imagePropertyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ImagePropertyDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", height=" + getHeight() +
            ", width=" + getWidth() +
            ", x=" + getX() +
            ", y=" + getY() +
            ", hVGColor='" + gethVGColor() + "'" +
            ", entry=" + getEntryId() +
            "}";
    }
}
