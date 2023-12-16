package tiktzuki.e_store.DTO;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tiktzuki.e_store.infrastructure.repository.Col;
import tiktzuki.e_store.infrastructure.repository.Id;
import tiktzuki.e_store.infrastructure.repository.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("tbl_PhieuNhap")
public class ReceivingNote {
    @Id
    @Col("MaPhieuNhap")
    private Integer id;
    @Col("MaNhanVien")
    private Integer staffId;
    @Col("MaNhaCungCap")
    private Integer providerId;
    @Col("NgayLap")
    private String createDate;
    @Col("TongTien")
    private Integer amount;

//	Provider provider;
//	Employee employee;
//	List<ReceivingDetail> receivingDetails;
}
