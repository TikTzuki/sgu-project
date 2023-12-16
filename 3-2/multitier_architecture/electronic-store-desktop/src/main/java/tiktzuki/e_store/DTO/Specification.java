package tiktzuki.e_store.DTO;

import lombok.*;
import tiktzuki.e_store.infrastructure.repository.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("tbl_ThongSo")
public class Specification {
    @Id
    @Col("MaThongSo")
    Integer id;
    @Col("Ten")
    String name;
    @Col("MaLoaiThietBi")
    Integer deviceTypeId;
}
