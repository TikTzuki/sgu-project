package tiktzuki.e_store.DAL;

import tiktzuki.e_store.DTO.Order;
import tiktzuki.e_store.infrastructure.repository.Repository;
import tiktzuki.e_store.infrastructure.repository.RepositoryImpl;

public class OrderRepository extends RepositoryImpl<Order, Integer> {

    public OrderRepository() {
        super(Order.class, Integer.class);
    }

    public static void main(String[] args) {
        Repository<Order, Integer> repos = new OrderRepository();
        repos
                .findAll()
                .forEach(System.out::println);
    }
}
