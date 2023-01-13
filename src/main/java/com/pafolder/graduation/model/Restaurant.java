package com.pafolder.graduation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.util.ProxyUtils;

import java.io.Serializable;

@Entity
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "address"}, name = "restaurant_unique_name_address_idx")})
public class Restaurant implements Serializable {
    @Id
    @SequenceGenerator(name = "restaurant_id_seq", sequenceName = "restaurant_id_seq", allocationSize = 1, initialValue = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_id_seq")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotBlank
    @NotNull
    private String name;

    @Column(name = "address", nullable = false)
    @NotBlank
    @NotNull
    private String address;

    public Restaurant(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Restaurant() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(ProxyUtils.getUserClass(o))) {
            return false;
        }
        return id != null;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }

    @Override
    public String toString() {
        return "Restaurant { id=" + id + ", name='" + (name != null ? name : "") +
                "', address='" + (address != null ? address : "") + "' }";
    }
}
