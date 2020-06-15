package com.eit.vipo.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ImageProperty.
 */
@Entity
@Table(name = "image_property")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ImageProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @NotNull
    @Column(name = "height", nullable = false)
    private Integer height;

    @NotNull
    @Column(name = "width", nullable = false)
    private Integer width;

    @NotNull
    @Column(name = "x", nullable = false)
    private Integer x;

    @NotNull
    @Column(name = "y", nullable = false)
    private Integer y;

    @Column(name = "h_vg_color")
    private String hVGColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("props")
    private VipoEntry entry;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public ImageProperty label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getHeight() {
        return height;
    }

    public ImageProperty height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public ImageProperty width(Integer width) {
        this.width = width;
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getX() {
        return x;
    }

    public ImageProperty x(Integer x) {
        this.x = x;
        return this;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public ImageProperty y(Integer y) {
        this.y = y;
        return this;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String gethVGColor() {
        return hVGColor;
    }

    public ImageProperty hVGColor(String hVGColor) {
        this.hVGColor = hVGColor;
        return this;
    }

    public void sethVGColor(String hVGColor) {
        this.hVGColor = hVGColor;
    }

    public VipoEntry getEntry() {
        return entry;
    }

    public ImageProperty entry(VipoEntry vipoEntry) {
        this.entry = vipoEntry;
        return this;
    }

    public void setEntry(VipoEntry vipoEntry) {
        this.entry = vipoEntry;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageProperty)) {
            return false;
        }
        return id != null && id.equals(((ImageProperty) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ImageProperty{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", height=" + getHeight() +
            ", width=" + getWidth() +
            ", x=" + getX() +
            ", y=" + getY() +
            ", hVGColor='" + gethVGColor() + "'" +
            "}";
    }
}
