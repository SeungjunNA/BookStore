package eliceproject.bookstore.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String userId);
    User findByEmail(String email);
    User findByMobileNumber(String phoneNumber);
    User findByNameAndEmail(String name, String email);
    User findByNameAndUsernameAndEmail(String name, String username, String email);
}
