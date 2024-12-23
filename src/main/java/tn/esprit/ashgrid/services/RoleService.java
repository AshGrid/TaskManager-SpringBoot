package tn.esprit.ashgrid.services;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.esprit.ashgrid.entities.User;
import tn.esprit.ashgrid.repositories.UserRepository;

import java.util.List;

@Service
public class RoleService {

    private final UserRepository userRepository;

    public RoleService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserDetails(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}
