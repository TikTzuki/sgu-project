package tiktzuki.e_store.DTO;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tiktzuki.e_store.DAL.BrandRepository;
import tiktzuki.e_store.infrastructure.repository.Col;
import tiktzuki.e_store.infrastructure.repository.Id;
import tiktzuki.e_store.infrastructure.repository.Repository;
import tiktzuki.e_store.infrastructure.repository.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("tbl_Hang")
public class Brand {

    @Id
    @Col("MaHang")
    Integer id;
    @Col("MaTrangThaiKinhDoanh")
    Integer deviceStatusId;
    @Col("Ten")
    String name;

}
