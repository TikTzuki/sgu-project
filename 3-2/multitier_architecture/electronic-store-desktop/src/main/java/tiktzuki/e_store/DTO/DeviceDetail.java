package tiktzuki.e_store.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tiktzuki.e_store.infrastructure.repository.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("tbl_ChiTietThietBi")
public class DeviceDetail {
    @CoupleId
    @Col("MaThietBi")
    private Integer deviceId;
    @CoupleId
    @Col("MaChiTietThongso")
    private Integer specificationDetailId;
    @Col("GhiChu")
    String description;
}
