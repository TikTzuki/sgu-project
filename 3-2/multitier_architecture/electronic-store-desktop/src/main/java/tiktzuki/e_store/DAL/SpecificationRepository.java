package tiktzuki.e_store.DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tiktzuki.e_store.DTO.Specification;
import tiktzuki.e_store.infrastructure.Connector;
import tiktzuki.e_store.infrastructure.MssqlConnector;
import tiktzuki.e_store.infrastructure.repository.RepositoryImpl;

public class SpecificationRepository extends RepositoryImpl<Specification, Integer> {
    private Connector connector;

    public SpecificationRepository() {
        super(Specification.class, Integer.class);
        connector = new MssqlConnector();
    }

    public List<Specification> findById(int id) {
        String query = "SELECT * FROM tbl_ThongSo" + " WHERE " + "MaLoaiThietBi = '" + id + "' ";
        ResultSet rs = connector.executeQuery(query);
        return super.extract(rs);
    }

    public List<Specification> getAll() {
        List<Specification> specifications = new ArrayList<Specification>();
        specifications = super.findAll();
        return specifications;
    }

    public int findIdByName(String name) {
        String query = "SELECT * FROM tbl_ThongSo " + " Where Ten = N'" + name + "'";
        ResultSet rs = connector.executeQuery(query);
        int id = 0;
        try {
            while (rs.next())
                id = rs.getInt("MaThongSo");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String findNameById(int id) {
        String query = "SELECT * FROM tbl_ThongSo where MaThongSo = N'" + id + "'";
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
