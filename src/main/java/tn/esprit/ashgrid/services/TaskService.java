package tn.esprit.ashgrid.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.ashgrid.dto.TaskDTO;
import tn.esprit.ashgrid.entities.Category;
import tn.esprit.ashgrid.entities.Task;
import tn.esprit.ashgrid.entities.User;
import tn.esprit.ashgrid.repositories.CategoryRepository;
import tn.esprit.ashgrid.repositories.TaskRepository;
import tn.esprit.ashgrid.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service

public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    public Task createTask(String name, String description, Task.Priority priority,
                           LocalDate dueDate, Task.Status status, Long userId, Long categoryId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Category> category = categoryRepository.findById(categoryId);

        if (user.isPresent() && category.isPresent()) {
            Task task = new Task();
            task.setName(name);
            task.setDescription(description);
            task.setPriority(priority);
            task.setDueDate(dueDate);
            task.setStatus(status);
            task.setUser(user.get());
            task.setCategory(category.get());
            return taskRepository.save(task);
        } else {
            throw new IllegalArgumentException("User or Category not found!");
        }
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }

    public List<Task> getTasksByUser(Long userId) {
        return taskRepository.findAllByUserId(userId);
    }

    public List<Task> getTasksByCategory(Long categoryId) {
        return taskRepository.findAllByCategoryId(categoryId);
    }

    public Task updateTask(Long taskId, TaskDTO updatedTask) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));

        // Only update non-null fields
        if (updatedTask.getName() != null) existingTask.setName(updatedTask.getName());
        if (updatedTask.getDescription() != null) existingTask.setDescription(updatedTask.getDescription());
        if (updatedTask.getPriority() != null) existingTask.setPriority(updatedTask.getPriority());
        if (updatedTask.getDueDate() != null) existingTask.setDueDate(updatedTask.getDueDate());
        if (updatedTask.getStatus() != null) existingTask.setStatus(updatedTask.getStatus());

        // Update user if provided
        if (updatedTask.getUserId() != null) {
            User user = userRepository.findById(updatedTask.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + updatedTask.getUserId()));
            existingTask.setUser(user);
        }

        // Update category if provided
        if (updatedTask.getCategoryId() != null) {
            Category category = categoryRepository.findById(updatedTask.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with ID: " + updatedTask.getCategoryId()));
            existingTask.setCategory(category);
        }

        return taskRepository.save(existingTask);
    }

    public String deleteTask(Long taskId) {

        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));
        taskRepository.delete(existingTask);
        return "Task with ID: " + taskId + " deleted";
    }

    public List<Task> findByStatus(Task.Status status) {
        return taskRepository.findAllByStatus(status);
    }

    public void updateTaskStatus(Long taskId, Task.Status status, String userEmail) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the task belongs to the user or if the user has admin privileges
        if (!task.getUser().getId().equals(currentUser.getId()) &&
                currentUser.getRole() == User.Role.SIMPLE_USER) {
            throw new RuntimeException("Access denied: You can only update your tasks");
        }

        // Update only the status
        task.setStatus(status);
        taskRepository.save(task);
    }



//
//    @Scheduled(fixedRate = 10000)
//    public void testScheduling(){
//        log.info("scheduler");
//    }

}
