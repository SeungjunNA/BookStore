package eliceproject.bookstore.order;

import java.util.List;

public interface OrderService {

    Order create(Order order);

    List<Order> findAll();

    Order getOrderDetail(Long orderId);

}
