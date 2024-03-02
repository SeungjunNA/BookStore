package eliceproject.bookstore.order;

import eliceproject.bookstore.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetailList = new ArrayList<>();

    private OrderStatus orderStatus;

    private LocalDate orderDate;


    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetailList.add(orderDetail);
        orderDetail.setOrder(this);
    }

}
