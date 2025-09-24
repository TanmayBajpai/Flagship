package com.flagship.backend.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "feature_flags")
@Data
public class FeatureFlag {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String flagName;

    @Column
    private String description;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private int rolloutPercent;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "feature_flag_countries", joinColumns = @JoinColumn(name = "flag_id"))
    @Column(name = "allowed_countries")
    private List<String> allowedCountries = new ArrayList<>();

    @Column(nullable = false)
    private String seed;

    @Column(nullable = false)
    private String owner;

    @Column(nullable = false)
    private int withFlagSuccess;

    @Column(nullable = false)
    private int withoutFlagSuccess;
}
