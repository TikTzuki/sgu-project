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
@Table("tbl_ChiTietThongSo")
public class SpecificationDetail {
    @Id
    @Col("MaChiTietThongSo")
    private Integer id;
    @Col("MaThongSo")
    private Integer specificationId;
    @Col("Ten")
    private String name;
}
