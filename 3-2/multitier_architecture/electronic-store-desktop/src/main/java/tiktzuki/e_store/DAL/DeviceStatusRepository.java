package tiktzuki.e_store.DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tiktzuki.e_store.DTO.Device;
import tiktzuki.e_store.DTO.DeviceStatus;
import tiktzuki.e_store.infrastructure.Connector;
import tiktzuki.e_store.infrastructure.MssqlConnector;
import tiktzuki.e_store.infrastructure.repository.RepositoryImpl;

public class DeviceStatusRepository extends RepositoryImpl<DeviceStatus, Integer> {
    private Connector connector;

    public DeviceStatusRepository() {
        super(DeviceStatus.class, Integer.class);
        connector = new MssqlConnector();

    }

    public List<DeviceStatus> getAll() {
        List<DeviceStatus> deviceStatus = new ArrayList<DeviceStatus>();
        deviceStatus = super.findAll();
        return deviceStatus;
    }

    public List<DeviceStatus> getAllNotDelete() {
        String query = "SELECT * FROM tbl_TrangThaiKinhDoanh where MaTrangThaiKinhDoanh <> '3'";
        ResultSet rs = connector.executeQuery(query);
        return extract(rs);
    }

    public int findIdByName(String name) {
        String query = "SELECT * FROM tbl_TrangThaiKinhDoanh " + " Where Ten = N'" + name + "'";
        ResultSet rs = connector.executeQuery(query);
        int id = 0;
        try {
            while (rs.next())
                id = rs.getInt("MaTrangThaiKinhDoanh");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String findNameById(int id) {
        String query = "SELECT * FROM tbl_TrangThaiKinhDoanh where MaTrangThaiKinhDoanh = N'" + id + "'";
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
