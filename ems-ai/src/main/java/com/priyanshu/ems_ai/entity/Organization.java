package com.priyanshu.ems_ai.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "organizations", schema = "ems")
public class Organization {

    @Id
    private UUID id;

    private String name;
}