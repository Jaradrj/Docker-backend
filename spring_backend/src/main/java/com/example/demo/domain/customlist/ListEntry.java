package com.example.demo.domain.customlist;

import com.example.demo.core.generic.AbstractEntity;
import com.example.demo.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "list_entry")
@Getter
@Setter
@NoArgsConstructor
public class ListEntry extends AbstractEntity {

    @NotBlank
    @Size(min = 3)
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Size(max = 500)
    @Column(nullable = false, length = 500)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Importance importance;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // for sorting

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public ListEntry(UUID id, String title, String text, Importance importance, User user) {
        super(id);
        this.title = title;
        this.text = text;
        this.importance = importance;
        this.user = user;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public enum Importance {
        LOW,
        MEDIUM,
        HIGH
    }
}
