package com.kkhenissi.fdc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * The User entity.
 */
@ApiModel(description = "The User entity.")
@Entity
@Table(name = "fdc_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FdcUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * The firstname attribute.
     */
    @ApiModelProperty(value = "The firstname attribute.")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "hire_date")
    private Instant hireDate;

    @OneToMany(mappedBy = "fdcUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Job> jobs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "fdcUsers", allowSetters = true)
    private FdcUser manager;

    /**
     * Another side of the same relationship
     */
    @ApiModelProperty(value = "Another side of the same relationship")
    @ManyToOne
    @JsonIgnoreProperties(value = "fdcUsers", allowSetters = true)
    private Department department;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public FdcUser firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public FdcUser lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public FdcUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public FdcUser phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Instant getHireDate() {
        return hireDate;
    }

    public FdcUser hireDate(Instant hireDate) {
        this.hireDate = hireDate;
        return this;
    }

    public void setHireDate(Instant hireDate) {
        this.hireDate = hireDate;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public FdcUser jobs(Set<Job> jobs) {
        this.jobs = jobs;
        return this;
    }

    public FdcUser addJob(Job job) {
        this.jobs.add(job);
        job.setFdcUser(this);
        return this;
    }

    public FdcUser removeJob(Job job) {
        this.jobs.remove(job);
        job.setFdcUser(null);
        return this;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    public FdcUser getManager() {
        return manager;
    }

    public FdcUser manager(FdcUser fdcUser) {
        this.manager = fdcUser;
        return this;
    }

    public void setManager(FdcUser fdcUser) {
        this.manager = fdcUser;
    }

    public Department getDepartment() {
        return department;
    }

    public FdcUser department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FdcUser)) {
            return false;
        }
        return id != null && id.equals(((FdcUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FdcUser{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", hireDate='" + getHireDate() + "'" +
            "}";
    }
}
