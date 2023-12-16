package tiktzuki.e_store.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tiktzuki.e_store.infrastructure.repository.Col;
import tiktzuki.e_store.infrastructure.repository.Id;
import tiktzuki.e_store.infrastructure.repository.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("tbl_PhieuGiaoHang")
public class ShippingNote {
    @Id
    @Col("MaPhieuGiaoHang")
    Integer id;
    @Col("NguoiNhan")
    String receiver;
    @Col("DiaChi")
    String address;
    @Col("SoDienThoai")
    String phoneNumber;
    @Col("NgayGiao")
    String scheduleDate;
    @Col("MaHoaDon")
    Integer orderId;

    public ShippingNote(Integer id,
                        String receiver,
                        String address,
                        String phoneNumber,
                        String scheduleDate,
                        Integer orderId) {
        super();
        this.id = id;
        this.receiver = receiver;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.scheduleDate = scheduleDate;
        this.orderId = orderId;
    }


    Order order;
}
