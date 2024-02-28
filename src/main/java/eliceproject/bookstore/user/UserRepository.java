package eliceproject.bookstore.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String userId);
    User findByEmail(String email);

    User findByMobileNumber(String phoneNumber);
}
