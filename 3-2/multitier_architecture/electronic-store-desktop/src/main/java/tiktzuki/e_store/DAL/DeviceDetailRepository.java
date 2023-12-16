package tiktzuki.e_store.DAL;

import java.sql.ResultSet;
import java.util.List;

import tiktzuki.e_store.DTO.Device;
import tiktzuki.e_store.DTO.DeviceDetail;
import tiktzuki.e_store.infrastructure.Connector;
import tiktzuki.e_store.infrastructure.MssqlConnector;
import tiktzuki.e_store.infrastructure.repository.Col;
import tiktzuki.e_store.infrastructure.repository.RepositoryImpl;

public class DeviceDetailRepository extends RepositoryImpl<DeviceDetail, Integer> {
    private Connector connector;

    public DeviceDetailRepository() {
        super(DeviceDetail.class, Integer.class);
        connector = new MssqlConnector();

    }

    public int insert(DeviceDetail entity) {
        return super.insert(entity);
    }

    public void deleteByDeviceId(int id) {
        StringBuilder query = new StringBuilder("DELETE FROM tbl_ChiTietThietBi" + " WHERE MaThietBi " + " = '" + id + "' ");
        connector.executeUpdate(query.toString());
    }

    public List<DeviceDetail> findByDeviceId(int id) {
        String query = "SELECT * FROM tbl_ChiTietThietBi" + " WHERE " + "MaThietBi= '" + id + "' ";
        ResultSet rs = connector.executeQuery(query);
        return super.extract(rs);
    }
}
