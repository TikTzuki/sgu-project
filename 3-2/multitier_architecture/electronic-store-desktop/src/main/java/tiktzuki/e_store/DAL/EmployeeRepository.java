package tiktzuki.e_store.DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import tiktzuki.e_store.DTO.Employee;
import tiktzuki.e_store.infrastructure.Connector;
import tiktzuki.e_store.infrastructure.MssqlConnector;
import tiktzuki.e_store.infrastructure.repository.RepositoryImpl;

public class EmployeeRepository extends RepositoryImpl<Employee, Integer> {
    private Connector connector;

    public EmployeeRepository() {
        super(Employee.class, Integer.class);
        connector = new MssqlConnector();

    }

    public String findNameById(int id) {
        String query = "SELECT * FROM tbl_NhanVien where MaNhanVien = N'" + id + "'";
        ResultSet rs = connector.executeQuery(query);
        String name = "";
        try {
            while (rs.next())
                name = rs.getString("Ten");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public List<Employee> getAll() {
        return super.findAll();
    }

    public int insert(Employee employee) {
        return super.insert(employee);
    }

    public int update(Employee employee) {
        return super.update(employee);
    }

    public int updateStatusDeleted(int id) {
        String query = "update tbl_NhanVien set TrangThai = 'Deleted' where MaNhanVien = N'" + id + "'";
        return connector.executeUpdate(query);
    }

    public String findUserName(String userName) {
        String query = "SELECT * FROM tbl_NhanVien where TaiKhoan = N'" + userName + "'";
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

    public Employee findByName(String userName) {
        String query = "SELECT * FROM tbl_NhanVien where TaiKhoan = N'" + userName + "' ";
        ResultSet rs = connector.executeQuery(query);
        List<Employee> emps = extract(rs);
        return emps.size() == 0 ? null : emps.get(0);
    }

    public List<Employee> searchByName(String name) {
        String query = "SELECT * FROM tbl_NhanVien where Ten like N'%" + name + "%'";
        ResultSet rs = connector.executeQuery(query);
        return extract(rs);
    }

    public List<Employee> findAllNotDeleted() {
        String query = "SELECT * FROM tbl_NhanVien where TrangThai <> 'Deleted'";
        ResultSet rs = connector.executeQuery(query);
        return extract(rs);
    }
}
