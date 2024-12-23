package tn.esprit.ashgrid.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class SignupRequest {
    private String name;
    private String email;
    private String password;
   // private MultipartFile image;
}

