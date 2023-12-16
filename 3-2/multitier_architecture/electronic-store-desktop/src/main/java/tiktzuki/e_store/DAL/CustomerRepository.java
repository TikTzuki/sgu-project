package tiktzuki.e_store.DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import tiktzuki.e_store.DTO.Customer;
import tiktzuki.e_store.DTO.Employee;
import tiktzuki.e_store.infrastructure.Connector;
import tiktzuki.e_store.infrastructure.MssqlConnector;
import tiktzuki.e_store.infrastructure.repository.RepositoryImpl;

public class CustomerRepository extends RepositoryImpl<Customer, Integer> {
    private Connector connector;

    public CustomerRepository() {
        super(Customer.class, Integer.class);
        connector = new MssqlConnector();

    }

    public List<Customer> findAllNotDeleted() {
        String query = "SELECT * FROM tbl_KhachHang where TrangThai <> 'Deleted'";
        ResultSet rs = connector.executeQuery(query);
        return extract(rs);
    }

    public String findUserName(String userName) {
        String query = "SELECT * FROM tbl_KhachHang where TaiKhoan = N'" + userName + "'";
        ResultSet rs = connector.executeQuery(query);
        String name = "";
        try {
            while (rs.next())
                name = rs.getString("TaiKhoan");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public int updateStatusDeleted(int id) {
        String query = "update tbl_KhachHang set TrangThai = 'Deleted' where MaKhachHang = N'" + id + "'";
        return connector.executeUpdate(query);
    }

    public List<Customer> searchByName(String name) {
        String query = "SELECT * FROM tbl_KhachHang where Ten like N'%" + name + "%'";
        ResultSet rs = connector.executeQuery(query);
        return extract(rs);
    }
}
