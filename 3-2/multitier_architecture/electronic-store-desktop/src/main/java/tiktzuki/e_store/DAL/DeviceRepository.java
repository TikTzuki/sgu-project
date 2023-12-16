package tiktzuki.e_store.DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tiktzuki.e_store.DTO.Device;
import tiktzuki.e_store.DTO.ReceivingNote;
import tiktzuki.e_store.infrastructure.Connector;
import tiktzuki.e_store.infrastructure.MssqlConnector;
import tiktzuki.e_store.infrastructure.repository.RepositoryImpl;

public class DeviceRepository extends RepositoryImpl<Device, Integer> {
    private Connector connector;

    public DeviceRepository() {
        super(Device.class, Integer.class);
        connector = new MssqlConnector();
    }

    public List<Device> findAll() {
        List<Device> devices = new ArrayList<>();
        devices = super.findAll();
        return devices;
    }

    public List<Device> findAllNotDelete() {
        String query = "SELECT * FROM tbl_ThietBi where MaTrangThaiKinhDoanh <> '3'";
        ResultSet rs = connector.executeQuery(query);
        return extract(rs);
    }

    public int insert(Device entity) {
        return super.insert(entity);
    }

    public int update(Device entity) {
        return super.update(entity);
    }

    public int updateQuantity(Device entity) {
        StringBuilder query = new StringBuilder("UPDATE tbl_ThietBi SET SoLuong = '" + entity.getQuantity() + "' WHERE MaThietBi= '" + entity.getId() + "' ");
        return connector.executeUpdate(query.toString());
    }

    public void deleteByDeviceId(int id) {
        StringBuilder query = new StringBuilder("DELETE FROM tbl_ThietBi" + " WHERE MaThietBi " + " = '" + id + "' ");
        connector.executeUpdate(query.toString());
    }

    public int updateStatusDeleted(int id) {
        String query = "update tbl_ThietBi set MaTrangThaiKinhDoanh = '3' where MaThietBi = N'" + id + "'";
        return connector.executeUpdate(query);
    }

    public int findLastId() {
        String query = "SELECT TOP 1 MaThietBi FROM tbl_ThietBi  " + " order by MaThietBi desc ";
        ResultSet rs = connector.executeQuery(query);
        int id = 0;
        try {
            while (rs.next())
                id = rs.getInt("MaThietBi");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int findIdByName(String name) {
        String query = "SELECT * FROM tbl_ThietBi where TenThietBi = '" + name + "'";
        ResultSet rs = connector.executeQuery(query);
        int id = 0;
        try {
            while (rs.next())
                id = rs.getInt("MaThietBi");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int getQuantityById(int id) {
        String query = "SELECT * FROM tbl_ThietBi where MaThietBi = '" + id + "'";
        ResultSet rs = connector.executeQuery(query);
        int quantity = 0;
        try {
            while (rs.next())
                quantity = rs.getInt("SoLuong");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quantity;
    }

    public Device findById(int id) {
        Device device;
        device = super.findById(id).orElse(new Device());
        return device;
    }

    public String findNameById(int id) {
        String query = "SELECT * FROM tbl_ThietBi where MaThietBi = N'" + id + "'";
        ResultSet rs = connector.executeQuery(query);
        String name = "";
        try {
            while (rs.next())
                name = rs.getString("TenThietBi");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public List<Device> searchByName(String name) {
        String query = "SELECT * FROM tbl_ThietBi where TenThietBi like N'%" + name + "%'";
        ResultSet rs = connector.executeQuery(query);
        return extract(rs);
    }
}
