package com.example.demo.domain.customlist;

import com.example.demo.core.generic.AbstractEntity;
import com.example.demo.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import java.time.LocalDateTime;

@Entity
@Table(name = "list_entry")
@Getter
@Setter
@NoArgsConstructor
@Log4j2
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

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PostPersist
    public void logNewEntryAdded(){
        log.info("Created element '{}' with ID: {}", title, super.getId());
    }

    public enum Importance {
        LOW,
        MEDIUM,
        HIGH
    }
}
