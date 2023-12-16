package tiktzuki.e_store.DTO;

import lombok.*;
import tiktzuki.e_store.infrastructure.repository.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("tbl_TrangThaiKinhDoanh")
public class DeviceStatus {

    @Id
    @Col("MaTrangThaiKinhDoanh")
    Integer id;
    @Col("Ten")
    String name;
}
