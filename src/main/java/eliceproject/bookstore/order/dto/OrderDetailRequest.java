package eliceproject.bookstore.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailRequest {
    private Long bookId;
    private int stock;
}
