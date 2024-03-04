package eliceproject.bookstore.user;

public interface UserService {
    User save(UserDto userDto);
    String findUsername(UserDto userDto);
    String findPassword(UserDto userDto);
    boolean login(String username, String password);
}
