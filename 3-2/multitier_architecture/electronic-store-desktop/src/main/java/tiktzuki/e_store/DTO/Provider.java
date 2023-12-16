package tiktzuki.e_store.DTO;

import lombok.*;
import tiktzuki.e_store.infrastructure.repository.Col;
import tiktzuki.e_store.infrastructure.repository.Id;
import tiktzuki.e_store.infrastructure.repository.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("tbl_NhaCungCap")
public class Provider {
    @Id
    @Col("MaNhaCungCap")
    Integer id;
    @Col("Ten")
    String name;
    @Col("DiaChi")
    String address;
    @Col("SDT")
    String phone;
}
