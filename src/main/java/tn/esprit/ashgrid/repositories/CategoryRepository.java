package tn.esprit.ashgrid.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ashgrid.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
