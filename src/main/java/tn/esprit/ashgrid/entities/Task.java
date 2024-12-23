package tn.esprit.ashgrid.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    public enum Status {
        PENDING, IN_PROGRESS, COMPLETED
    }
}
