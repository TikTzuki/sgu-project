package tiktzuki.e_store.DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import tiktzuki.e_store.DTO.Employee;
import tiktzuki.e_store.DTO.Role;
import tiktzuki.e_store.infrastructure.Connector;
import tiktzuki.e_store.infrastructure.MssqlConnector;
import tiktzuki.e_store.infrastructure.repository.RepositoryImpl;

public class RoleRepository extends RepositoryImpl<Role, Integer> {
    private Connector connector;

    public RoleRepository() {
        super(Role.class, Integer.class);
        connector = new MssqlConnector();

    }

    public String findNameById(int id) {
        String query = "SELECT * FROM tbl_ChucVu where MaChucVu = N'" + id + "'";
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

    public int findIdByName(String name) {
        String query = "SELECT * FROM tbl_ChucVu where Ten = N'" + name + "'";
        ResultSet rs = connector.executeQuery(query);
        int id = 0;
        try {
            while (rs.next())
                id = rs.getInt("MaChucVu");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public List<Role> findAll() {
        return super.findAll();
    }

}
