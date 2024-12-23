package tn.esprit.ashgrid.dto;

import lombok.Data;
import tn.esprit.ashgrid.entities.Task.Priority;
import tn.esprit.ashgrid.entities.Task.Status;

import java.time.LocalDate;

@Data
public class TaskDTO {
    private Long id;
    private String name;
    private String description;
    private Priority priority;
    private LocalDate dueDate;
    private Status status;
    private Long userId; // Reference to User entity
    private Long categoryId; // Reference to Category entity
}
