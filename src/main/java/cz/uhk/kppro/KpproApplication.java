package cz.uhk.kppro;

import cz.uhk.kppro.model.User;
import cz.uhk.kppro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class KpproApplication {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public KpproApplication(UserService userService, PasswordEncoder passwordEncoder){
        this.userService =userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
            addUser("admin", "heslo", "ADMIN");
            addUser("user", "heslo", "USER");
        };
    }

    private void addUser(String username, String password, String role) {
        if (userService.findByUsername(username) == null) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(role);
            userService.save(user);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(KpproApplication.class, args);
    }
}