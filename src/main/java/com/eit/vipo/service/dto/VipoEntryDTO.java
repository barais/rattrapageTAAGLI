package com.eit.vipo.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.eit.vipo.domain.VipoEntry} entity.
 */
public class VipoEntryDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate registerDate;

    @NotNull
    private String imageName;


    private Long vipoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Long getVipoId() {
        return vipoId;
    }

    public void setVipoId(Long vipoId) {
        this.vipoId = vipoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VipoEntryDTO vipoEntryDTO = (VipoEntryDTO) o;
        if (vipoEntryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vipoEntryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VipoEntryDTO{" +
            "id=" + getId() +
            ", registerDate='" + getRegisterDate() + "'" +
            ", imageName='" + getImageName() + "'" +
            ", vipo=" + getVipoId() +
            "}";
    }
}
