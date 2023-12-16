package tiktzuki.e_store.DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import tiktzuki.e_store.DTO.ReceivingNote;
import tiktzuki.e_store.infrastructure.Connector;
import tiktzuki.e_store.infrastructure.MssqlConnector;
import tiktzuki.e_store.infrastructure.repository.RepositoryImpl;

public class ReceivingNoteRepository extends RepositoryImpl<ReceivingNote, Integer> {
    private Connector connector;

    public ReceivingNoteRepository() {
        super(ReceivingNote.class, Integer.class);
        connector = new MssqlConnector();

    }

    public List<ReceivingNote> findAll() {
        return super.findAll();
    }

    public int insert(ReceivingNote receivingNote) {
        return super.insert(receivingNote);
    }

    public int update(ReceivingNote receivingNote) {
        return super.update(receivingNote);
    }

    public int findLastId() {
        String query = "SELECT TOP 1 MaPhieuNhap FROM tbl_PhieuNhap  " + " order by MaPhieuNhap desc ";
        ResultSet rs = connector.executeQuery(query);
        int id = 0;
        try {
            while (rs.next())
                id = rs.getInt("MaPhieuNhap");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public ReceivingNote findById(int id) {
        ReceivingNote receivingNote = super.findById(id).orElse(new ReceivingNote());
        return receivingNote;
    }

    public List<ReceivingNote> searchByDate(String date) {
        String query = "SELECT * FROM tbl_PhieuNhap where NgayLap like N'%" + date + "%'";
        ResultSet rs = connector.executeQuery(query);
        return extract(rs);
    }
}
