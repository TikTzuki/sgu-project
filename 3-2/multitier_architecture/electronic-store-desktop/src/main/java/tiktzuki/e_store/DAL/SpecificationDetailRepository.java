package tiktzuki.e_store.DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tiktzuki.e_store.DTO.Device;
import tiktzuki.e_store.DTO.SpecificationDetail;
import tiktzuki.e_store.infrastructure.Connector;
import tiktzuki.e_store.infrastructure.MssqlConnector;
import tiktzuki.e_store.infrastructure.repository.RepositoryImpl;

public class SpecificationDetailRepository extends RepositoryImpl<SpecificationDetail, Integer> {
    private Connector connector;

    public SpecificationDetailRepository() {
        super(SpecificationDetail.class, Integer.class);
        connector = new MssqlConnector();
    }

    public List<SpecificationDetail> findById(int id) {
        String query = "SELECT * FROM tbl_ChiTietThongSo" + " WHERE " + "MaThongSo = '" + id + "' ";
        ResultSet rs = connector.executeQuery(query);
        return super.extract(rs);
    }

    public List<SpecificationDetail> getAll() {
        List<SpecificationDetail> specificationDetails = new ArrayList<>();
        specificationDetails = super.findAll();
        return specificationDetails;
    }

    public int findIdByName(String name) {
        String query = "SELECT * FROM tbl_ChiTietThongSo " + " Where Ten = N'" + name + "'";
        ResultSet rs = connector.executeQuery(query);
        int id = 0;
        try {
            while (rs.next())
                id = rs.getInt("MaChiTietThongSo");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String findNameById(int id) {
        String query = "SELECT * FROM tbl_ChiTietThongSo where MaChiTietThongSo = N'" + id + "'";
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

    public int findSpecIDById(int id) {
        String query = "SELECT * FROM tbl_ChiTietThongSo where MaChiTietThongSo = N'" + id + "'";
        ResultSet rs = connector.executeQuery(query);
        int specId = 0;
        try {
            while (rs.next())
                specId = rs.getInt("MaThongSo");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specId;
    }
}
