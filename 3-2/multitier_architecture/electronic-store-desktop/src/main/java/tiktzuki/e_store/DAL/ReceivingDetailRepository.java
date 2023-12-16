package tiktzuki.e_store.DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import tiktzuki.e_store.DTO.Device;
import tiktzuki.e_store.DTO.ReceivingDetail;
import tiktzuki.e_store.infrastructure.Connector;
import tiktzuki.e_store.infrastructure.MssqlConnector;
import tiktzuki.e_store.infrastructure.repository.RepositoryImpl;

public class ReceivingDetailRepository extends RepositoryImpl<ReceivingDetail, Integer> {
    private Connector connector;

    public ReceivingDetailRepository() {
        super(ReceivingDetail.class, Integer.class);
        connector = new MssqlConnector();

    }

    public int insert(ReceivingDetail receivingDetail) {
        return super.insert(receivingDetail);
    }

    public List<ReceivingDetail> findListByReceivingId(int id) {
        String query = "SELECT * FROM tbl_ChiTietPhieuNhap" + " WHERE " + "MaPhieuNhap = '" + id + "' ";
        ResultSet rs = connector.executeQuery(query);
        return super.extract(rs);
    }

    public int update(ReceivingDetail receivingDetail) {
        return super.update(receivingDetail);
    }

    public void deleteByReceivingId(int id) {
        StringBuilder query = new StringBuilder("DELETE FROM tbl_ChiTietPhieuNhap" + " WHERE MaPhieuNhap " + " = '" + id + "' ");
        connector.executeUpdate(query.toString());
    }

}
