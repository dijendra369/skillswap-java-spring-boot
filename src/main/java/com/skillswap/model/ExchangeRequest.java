package com.skillswap.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "exchange_requests")
public class ExchangeRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private User sender;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private User receiver;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Skill skill;

    @Size(max = 500)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.PENDING;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    protected ExchangeRequest() {}

    public ExchangeRequest(User sender, User receiver, Skill skill, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.skill = skill;
        this.message = message == null ? "" : message.trim();
    }

    public Long getId() { return id; }
    public User getSender() { return sender; }
    public User getReceiver() { return receiver; }
    public Skill getSkill() { return skill; }
    public String getMessage() { return message; }
    public Status getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setStatus(Status status) { this.status = status; }
    public enum Status { PENDING, ACCEPTED, DECLINED }
}
