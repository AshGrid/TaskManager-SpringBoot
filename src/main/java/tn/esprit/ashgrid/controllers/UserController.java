package tn.esprit.ashgrid.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ashgrid.entities.User;
import tn.esprit.ashgrid.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/users/{userId}/role")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<String> updateUserRole(
            @PathVariable Long userId,
            @RequestParam User.Role newRole) {
        userService.changeUserRole(userId, newRole);
        return ResponseEntity.ok("User role updated successfully");
    }




}

