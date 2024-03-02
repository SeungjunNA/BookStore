package eliceproject.bookstore.order;

import eliceproject.bookstore.order.dto.OrderDetailRequest;
import eliceproject.bookstore.order.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order create(OrderRequest orderRequest) {
        return new Order();
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
