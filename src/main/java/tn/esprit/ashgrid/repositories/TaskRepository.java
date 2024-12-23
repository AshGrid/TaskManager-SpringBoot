package tn.esprit.ashgrid.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ashgrid.entities.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {


    List<Task> findAllByUserId(Long userId);

    List<Task> findAllByCategoryId(Long categoryId);

    List <Task> findAllByStatus(Task.Status status);
}
