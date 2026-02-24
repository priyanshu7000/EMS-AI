package com.priyanshu.ems_ai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;


@Entity
@Table(name = "roles", schema = "ems")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    private UUID id;

    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Organization organization;
}
