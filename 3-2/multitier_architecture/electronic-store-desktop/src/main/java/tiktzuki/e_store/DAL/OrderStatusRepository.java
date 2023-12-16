package tiktzuki.e_store.DAL;

import tiktzuki.e_store.DTO.OrderStatus;
import tiktzuki.e_store.infrastructure.repository.RepositoryImpl;

public class OrderStatusRepository extends RepositoryImpl<OrderStatus, Integer> {

    public OrderStatusRepository() {
        super(OrderStatus.class, Integer.class);
    }

}
