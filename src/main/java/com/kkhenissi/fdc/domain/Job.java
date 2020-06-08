package com.kkhenissi.fdc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Job.
 */
@Entity
@Table(name = "job")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "min_salary")
    private Long minSalary;

    @Column(name = "max_salary")
    private Long maxSalary;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "job_item",
               joinColumns = @JoinColumn(name = "job_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"))
    private Set<Item> items = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "jobs", allowSetters = true)
    private FdcUser fdcUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Job jobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Long getMinSalary() {
        return minSalary;
    }

    public Job minSalary(Long minSalary) {
        this.minSalary = minSalary;
        return this;
    }

    public void setMinSalary(Long minSalary) {
        this.minSalary = minSalary;
    }

    public Long getMaxSalary() {
        return maxSalary;
    }

    public Job maxSalary(Long maxSalary) {
        this.maxSalary = maxSalary;
        return this;
    }

    public void setMaxSalary(Long maxSalary) {
        this.maxSalary = maxSalary;
    }

    public Set<Item> getItems() {
        return items;
    }

    public Job items(Set<Item> items) {
        this.items = items;
        return this;
    }

    public Job addItem(Item item) {
        this.items.add(item);
        item.getJobs().add(this);
        return this;
    }

    public Job removeItem(Item item) {
        this.items.remove(item);
        item.getJobs().remove(this);
        return this;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public FdcUser getFdcUser() {
        return fdcUser;
    }

    public Job fdcUser(FdcUser fdcUser) {
        this.fdcUser = fdcUser;
        return this;
    }

    public void setFdcUser(FdcUser fdcUser) {
        this.fdcUser = fdcUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Job)) {
            return false;
        }
        return id != null && id.equals(((Job) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Job{" +
            "id=" + getId() +
            ", jobTitle='" + getJobTitle() + "'" +
            ", minSalary=" + getMinSalary() +
            ", maxSalary=" + getMaxSalary() +
            "}";
    }
}
