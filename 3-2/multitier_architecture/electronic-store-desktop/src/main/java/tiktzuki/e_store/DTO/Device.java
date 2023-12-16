package tiktzuki.e_store.DTO;

import java.util.List;

import lombok.*;
import tiktzuki.e_store.infrastructure.repository.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("tbl_ThietBi")
public class Device {
    @Id
    @Col("MaThietBi")
    Integer id;
    @Col("TenThietBi")
    String name;
    @Col("MaLoaiThietBi")
    Integer deviceTypeId;
    @Col("SoLuong")
    Integer quantity;
    @Col("GiaBan")
    Integer price;
    @Col("MaHang")
    Integer brandId;
    @Col("MaTrangThaiKinhDoanh")
    Integer deviceStatusId;
    @Col("MoTa")
    String description;
    @Col("HinhAnh")
    String image;

    public List<DeviceDetail> deviceDetails;
}
