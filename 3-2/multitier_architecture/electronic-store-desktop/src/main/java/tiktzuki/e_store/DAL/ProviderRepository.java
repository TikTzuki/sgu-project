package tiktzuki.e_store.DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import tiktzuki.e_store.DTO.Provider;
import tiktzuki.e_store.infrastructure.Connector;
import tiktzuki.e_store.infrastructure.MssqlConnector;
import tiktzuki.e_store.infrastructure.repository.RepositoryImpl;

public class ProviderRepository extends RepositoryImpl<Provider, Integer> {
    private Connector connector;

    public ProviderRepository() {
        super(Provider.class, Integer.class);
        connector = new MssqlConnector();

    }

    public List<Provider> findAll() {
        return super.findAll();
    }

    public int findIdByName(String name) {
        String query = "SELECT * FROM tbl_NhaCungCap " + " Where Ten = N'" + name + "'";
        ResultSet rs = connector.executeQuery(query);
        int id = 0;
        try {
            while (rs.next())
                id = rs.getInt("MaNhaCungCap");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String findNameById(int id) {
        String query = "SELECT * FROM tbl_NhaCungCap where MaNhaCungCap = N'" + id + "'";
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
}
