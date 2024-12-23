package tn.esprit.ashgrid.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ashgrid.dto.TaskDTO;
import tn.esprit.ashgrid.entities.Task;
import tn.esprit.ashgrid.services.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {
        Task task = taskService.createTask(
                taskDTO.getName(),
                taskDTO.getDescription(),
                taskDTO.getPriority(),
                taskDTO.getDueDate(),
                taskDTO.getStatus(),
                taskDTO.getUserId(),
                taskDTO.getCategoryId()
        );
        return ResponseEntity.ok(task);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUser(@PathVariable Long userId) {
        List<Task> tasks = taskService.getTasksByUser(userId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Task>> getTasksByCategory(@PathVariable Long categoryId) {
        List<Task> tasks = taskService.getTasksByCategory(categoryId);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        Task task = taskService.updateTask(id, taskDTO);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize(" hasRole('SUPER_ADMIN')")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "task deleted";
    }
    @GetMapping("getByStatus/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable("status") Task.Status status) {
        List<Task> tasks = taskService.findByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{taskId}/status")
    @PreAuthorize("hasRole('SIMPLE_USER') or hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<String> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestParam Task.Status status) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // Retrieve the authenticated user's email

        taskService.updateTaskStatus(taskId, status, email);
        return ResponseEntity.ok("Task status updated successfully");
    }
    }
