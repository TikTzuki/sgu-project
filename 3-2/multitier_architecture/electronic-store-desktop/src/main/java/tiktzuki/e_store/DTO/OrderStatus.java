package tiktzuki.e_store.DTO;

import lombok.*;
import tiktzuki.e_store.infrastructure.repository.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("tbl_TrangThaiHoaDon")
public class OrderStatus {
    @Id
    @Col("MaTrangThaiHoaDon")
    Integer id;
    @Col("Ten")
    String name;

}
