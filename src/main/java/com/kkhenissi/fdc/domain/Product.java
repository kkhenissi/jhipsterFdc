package com.kkhenissi.fdc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.kkhenissi.fdc.domain.enumeration.Size;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "size")
    private Size size;

    @Column(name = "available_until")
    private Instant availableUntil;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "product_subcategory",
               joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "subcategory_id", referencedColumnName = "id"))
    private Set<SubCategory> subcategories = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private Brand brand;

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

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public Product image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Product imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Size getSize() {
        return size;
    }

    public Product size(Size size) {
        this.size = size;
        return this;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Instant getAvailableUntil() {
        return availableUntil;
    }

    public Product availableUntil(Instant availableUntil) {
        this.availableUntil = availableUntil;
        return this;
    }

    public void setAvailableUntil(Instant availableUntil) {
        this.availableUntil = availableUntil;
    }

    public Set<SubCategory> getSubcategories() {
        return subcategories;
    }

    public Product subcategories(Set<SubCategory> subCategories) {
        this.subcategories = subCategories;
        return this;
    }

    public Product addSubcategory(SubCategory subCategory) {
        this.subcategories.add(subCategory);
        subCategory.getProducts().add(this);
        return this;
    }

    public Product removeSubcategory(SubCategory subCategory) {
        this.subcategories.remove(subCategory);
        subCategory.getProducts().remove(this);
        return this;
    }

    public void setSubcategories(Set<SubCategory> subCategories) {
        this.subcategories = subCategories;
    }

    public Brand getBrand() {
        return brand;
    }

    public Product brand(Brand brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", price=" + getPrice() +
            ", size='" + getSize() + "'" +
            ", availableUntil='" + getAvailableUntil() + "'" +
            "}";
    }
}
