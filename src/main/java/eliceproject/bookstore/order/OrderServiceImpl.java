package eliceproject.bookstore.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order create(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderDetail(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() ->  new IllegalArgumentException("Order not found with id : " + orderId));
    }

}
