package tiktzuki.e_store.DAL;

import java.util.List;

import tiktzuki.e_store.DTO.OrderDetail;
import tiktzuki.e_store.infrastructure.Connector;
import tiktzuki.e_store.infrastructure.MssqlConnector;
import tiktzuki.e_store.infrastructure.repository.Repository;
import tiktzuki.e_store.infrastructure.repository.RepositoryImpl;

public class OrderDetailRepository extends RepositoryImpl<OrderDetail, Integer> {
    private Connector connector;

    public OrderDetailRepository() {
        super(OrderDetail.class, Integer.class);
        connector = new MssqlConnector();
    }

    public static void main(String[] args) {
        Repository<OrderDetail, Integer> repos = new OrderDetailRepository();
        repos
                .findAll()
                .forEach(System.out::println);

        System.out.println(repos.count());
    }

    public List<OrderDetail> findAllByOrderId(Integer orderId) {
        String query = "SELECT * FROM tbl_ChiTietHoaDon WHERE MaHoaDon = " + orderId;
        return super.extract(connector.executeQuery(query));
    }

    public void deleteByOrderId(Integer orderId) {
        String query = "DELETE FROM tbl_ChiTietHoaDon WHERE MaHoaDon = " + orderId;
        connector.executeUpdate(query);
    }
}
