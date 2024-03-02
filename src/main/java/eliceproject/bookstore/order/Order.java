package eliceproject.bookstore.order;

import eliceproject.bookstore.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private User user;

    private OrderStatus orderStatus;

    private LocalDate orderDate;

}
