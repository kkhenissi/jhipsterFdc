package com.kkhenissi.fdc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Task entity.\n@author The JHipster team.
 */
@ApiModel(description = "Task entity.\n@author The JHipster team.")
@Entity
@Table(name = "item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "current_price")
    private Double currentPrice;

    @Column(name = "status_item")
    private Boolean statusItem;

    @ManyToMany(mappedBy = "items")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Job> jobs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Item title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Item description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public Item currentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
        return this;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Boolean isStatusItem() {
        return statusItem;
    }

    public Item statusItem(Boolean statusItem) {
        this.statusItem = statusItem;
        return this;
    }

    public void setStatusItem(Boolean statusItem) {
        this.statusItem = statusItem;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public Item jobs(Set<Job> jobs) {
        this.jobs = jobs;
        return this;
    }

    public Item addJob(Job job) {
        this.jobs.add(job);
        job.getItems().add(this);
        return this;
    }

    public Item removeJob(Job job) {
        this.jobs.remove(job);
        job.getItems().remove(this);
        return this;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        return id != null && id.equals(((Item) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Item{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", currentPrice=" + getCurrentPrice() +
            ", statusItem='" + isStatusItem() + "'" +
            "}";
    }
}