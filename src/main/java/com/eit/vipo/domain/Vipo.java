package com.eit.vipo.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Vipo.
 */
@Entity
@Table(name = "vipo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vipo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "lattitude")
    private String lattitude;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @OneToOne(fetch = FetchType.LAZY)

    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @OneToMany(mappedBy = "vipo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VipoEntry> entries = new HashSet<>();

    @OneToOne(mappedBy = "vipo")
    @JsonIgnore
    private Store store;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Vipo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongitude() {
        return longitude;
    }

    public Vipo longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLattitude() {
        return lattitude;
    }

    public Vipo lattitude(String lattitude) {
        this.lattitude = lattitude;
        return this;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Vipo createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public Vipo user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<VipoEntry> getEntries() {
        return entries;
    }

    public Vipo entries(Set<VipoEntry> vipoEntries) {
        this.entries = vipoEntries;
        return this;
    }

    public Vipo addEntries(VipoEntry vipoEntry) {
        this.entries.add(vipoEntry);
        vipoEntry.setVipo(this);
        return this;
    }

    public Vipo removeEntries(VipoEntry vipoEntry) {
        this.entries.remove(vipoEntry);
        vipoEntry.setVipo(null);
        return this;
    }

    public void setEntries(Set<VipoEntry> vipoEntries) {
        this.entries = vipoEntries;
    }

    public Store getStore() {
        return store;
    }

    public Vipo store(Store store) {
        this.store = store;
        return this;
    }

    public void setStore(Store store) {
        this.store = store;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vipo)) {
            return false;
        }
        return id != null && id.equals(((Vipo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vipo{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", lattitude='" + getLattitude() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
