package com.skillswap.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class Skill {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String title;

    @NotBlank @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String category;

    @Size(max = 700)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SkillType type;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private User owner;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    protected Skill() {}

    public Skill(String title, String category, String description, SkillType type, User owner) {
        this.title = title.trim();
        this.category = category.trim();
        this.description = description == null ? "" : description.trim();
        this.type = type;
        this.owner = owner;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public SkillType getType() { return type; }
    public User getOwner() { return owner; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public enum SkillType { TEACH, LEARN }
}
