package tiktzuki.e_store.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tiktzuki.e_store.infrastructure.repository.Col;
import tiktzuki.e_store.infrastructure.repository.Id;
import tiktzuki.e_store.infrastructure.repository.Repository;
import tiktzuki.e_store.infrastructure.repository.RepositoryImpl;
import tiktzuki.e_store.infrastructure.repository.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("tbl_HoaDon")
public class Order {
    @Id
    @Col("MaHoaDon")
    Integer id;
    @Col("MaKhachHang")
    Integer customerId;
    @Col("MaNhanVien")
    Integer staffId;
    @Col("TongTien")
    Integer amount;
    @Col("NgayLap")
    String createDate;
    @Col("MaTrangThaiDonHang")
    Integer statusId;
    @Col("NgayNhan")
    String receiveDate;
    @Col("DiaChiNhan")
    String address;
    @Col("TrangThaiThanhToan") // 1: da thanh toan, 2: chua thanh toan
    Integer paymentStatus;
    @Col("NguoiNhan")
    String receiver;
    @Col("SDT")
    String phone;

    List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
    Employee employee;
    Customer customer;
    OrderStatus status;


    public static void main(String[] args) {
        Repository<Order, Integer> repos = new RepositoryImpl<Order, Integer>(Order.class, Integer.class);
        repos
                .findAll()
                .forEach(System.out::println);
    }

}
