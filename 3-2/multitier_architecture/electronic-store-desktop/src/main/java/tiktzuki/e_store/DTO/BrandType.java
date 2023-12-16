package tiktzuki.e_store.DTO;

import lombok.*;
import tiktzuki.e_store.infrastructure.repository.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("tbl_LoaiHang")
public class BrandType {
    @CoupleId
    @Col("MaLoaiThietBi")
    Integer deviceTypeId;
    @CoupleId
    @Col("MaHang")
    Integer brandId;
    @Col("GhiChu")
    String description;

}
