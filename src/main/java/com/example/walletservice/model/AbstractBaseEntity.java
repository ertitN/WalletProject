package com.example.walletservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
 public abstract class AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @CreatedDate
    @Column(name = "created_date",nullable = false)
    @JsonIgnore
    private Instant createDate = Instant.now();
    @LastModifiedDate
    @Column(name = "last_modified_date",nullable = false)
    @JsonIgnore
    private Instant updateDate = Instant.now();
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
