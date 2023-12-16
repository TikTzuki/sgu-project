package tiktzuki.e_store.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tiktzuki.e_store.infrastructure.repository.Col;
import tiktzuki.e_store.infrastructure.repository.CoupleId;
import tiktzuki.e_store.infrastructure.repository.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("tbl_ChiTietHoaDon")
public class OrderDetail {
    @CoupleId
    @Col("MaHoaDon")
    public Integer orderId;
    @CoupleId
    @Col("MaThietBi")
    public Integer deviceId;
    @Col("SoLuong")
    public Integer quantity;
    @Col("ThanhTien")
    public Integer amount;
    @Col("DonGia")
    public Integer price;

    public OrderDetail(Integer orderId, Integer deviceId, Integer quantity, Integer amount, Integer price) {
        super();
        this.orderId = orderId;
        this.deviceId = deviceId;
        this.quantity = quantity;
        this.amount = amount;
        this.price = price;
    }

    Device device;
}
