package com.eit.vipo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A VipoEntry.
 */
@Entity
@Table(name = "vipo_entry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VipoEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "register_date", nullable = false)
    private LocalDate registerDate;

    @NotNull
    @Column(name = "image_name", nullable = false)
    private String imageName;

    @OneToMany(mappedBy = "entry")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ImageProperty> props = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("entries")
    private Vipo vipo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not
    // remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public VipoEntry registerDate(LocalDate registerDate) {
        this.registerDate = registerDate;
        return this;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public String getImageName() {
        return imageName;
    }

    public VipoEntry imageName(String imageName) {
        this.imageName = imageName;
        return this;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Set<ImageProperty> getProps() {
        return props;
    }

    public VipoEntry props(Set<ImageProperty> imageProperties) {
        this.props = imageProperties;
        return this;
    }

    public VipoEntry addProps(ImageProperty imageProperty) {
        this.props.add(imageProperty);
        imageProperty.setEntry(this);
        return this;
    }

    public VipoEntry removeProps(ImageProperty imageProperty) {
        this.props.remove(imageProperty);
        imageProperty.setEntry(null);
        return this;
    }

    public void setProps(Set<ImageProperty> imageProperties) {
        this.props = imageProperties;
    }

    public Vipo getVipo() {
        return vipo;
    }

    public VipoEntry vipo(Vipo vipo) {
        this.vipo = vipo;
        return this;
    }

    public void setVipo(Vipo vipo) {
        this.vipo = vipo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VipoEntry)) {
            return false;
        }
        return id != null && id.equals(((VipoEntry) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "VipoEntry{" + "id=" + getId() + ", registerDate='" + getRegisterDate() + "'" + ", imageName='"
                + getImageName() + "'" + "}";
    }
}
