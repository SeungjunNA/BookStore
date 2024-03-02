package eliceproject.bookstore.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String mobileNumber;
    private String birthday;
    private boolean isDeleted;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String username, String password, String name, String email, String mobileNumber, String birthday) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.birthday = birthday;
    }

    public enum Role{
        USER, ADMIN
    }
}
