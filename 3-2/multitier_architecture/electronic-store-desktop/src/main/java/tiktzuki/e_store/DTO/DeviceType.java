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
@Table("tbl_LoaiThietBi")
public class DeviceType {
    @Id
    @Col("MaLoaiThietBi")
    private Integer id;
    @Col("TenLoaiThietBi")
    private String name;
    @Col("MaTrangThaiKinhDoanh")
    private Integer statusId;
}
