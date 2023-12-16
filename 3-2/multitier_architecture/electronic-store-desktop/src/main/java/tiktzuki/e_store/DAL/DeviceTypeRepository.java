package tiktzuki.e_store.DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tiktzuki.e_store.DTO.DeviceType;
import tiktzuki.e_store.infrastructure.Connector;
import tiktzuki.e_store.infrastructure.MssqlConnector;
import tiktzuki.e_store.infrastructure.repository.RepositoryImpl;

public class DeviceTypeRepository extends RepositoryImpl<DeviceType, Integer> {
    private Connector connector;

    public DeviceTypeRepository() {
        super(DeviceType.class, Integer.class);
        connector = new MssqlConnector();

    }

    public List<DeviceType> getAll() {
        List<DeviceType> deviceTypes = new ArrayList<DeviceType>();
        deviceTypes = super.findAll();
        return deviceTypes;
    }

    public int findIdByName(String name) {
        String query = "SELECT * FROM tbl_LoaiThietBi " + " Where TenLoaiThietBi = N'" + name + "'";
        ResultSet rs = connector.executeQuery(query);
        int id = 0;
        try {
            while (rs.next())
                id = rs.getInt("MaLoaiThietBi");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String findNameById(int id) {
        String query = "SELECT * FROM tbl_LoaiThietBi where MaLoaiThietBi = N'" + id + "'";
        ResultSet rs = connector.executeQuery(query);
        String name = "";
        try {
            while (rs.next())
                name = rs.getString("TenLoaiThietBi");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }
}
