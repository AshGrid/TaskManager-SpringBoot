package tn.esprit.ashgrid.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.ashgrid.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
