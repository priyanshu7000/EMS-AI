package com.priyanshu.ems_ai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "organizations", schema = "ems")
@Data // This automatically adds Getters, Setters, and toString
@NoArgsConstructor // Replaces your empty constructor
@AllArgsConstructor // Useful for building objects
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Automatically creates the ID
    private UUID id;

    private String name;
}