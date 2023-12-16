package tiktzuki.e_store.DTO;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tiktzuki.e_store.infrastructure.repository.Col;
import tiktzuki.e_store.infrastructure.repository.CoupleId;
import tiktzuki.e_store.infrastructure.repository.Id;
import tiktzuki.e_store.infrastructure.repository.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("tbl_ChiTietPhieuNhap")
public class ReceivingDetail {
    @Id
    @Col("MaChiTietPhieuNhap")
    private Integer receivingDetailId;
    @CoupleId
    @Col("MaPhieuNhap")
    private Integer receivingId;
    @CoupleId
    @Col("MaThietBi")
    private Integer deviceId;
    @Col("SoLuong")
    private Integer quantity;
    @Col("DonGia")
    private Integer price;
    @Col("ThanhTien")
    private Integer amount;

//	Device device;
//	ReceivingNote receivingNote;
}
