package com.kkhenissi.fdc.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.kkhenissi.fdc.domain.enumeration.CategoryStatus;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CategoryStatus status;

    @OneToMany(mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<SubCategory> subcategories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Category name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryStatus getStatus() {
        return status;
    }

    public Category status(CategoryStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
    }

    public Set<SubCategory> getSubcategories() {
        return subcategories;
    }

    public Category subcategories(Set<SubCategory> subCategories) {
        this.subcategories = subCategories;
        return this;
    }

    public Category addSubcategory(SubCategory subCategory) {
        this.subcategories.add(subCategory);
        subCategory.setCategory(this);
        return this;
    }

    public Category removeSubcategory(SubCategory subCategory) {
        this.subcategories.remove(subCategory);
        subCategory.setCategory(null);
        return this;
    }

    public void setSubcategories(Set<SubCategory> subCategories) {
        this.subcategories = subCategories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
