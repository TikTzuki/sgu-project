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
@Table("tbl_KhachHang")
public class Customer {
    @Id
    @Col("MaKhachHang")
    Integer id;
    @Col("Ten")
    String name;
    @Col("TaiKhoan")
    String username;
    @Col("MatKhau")
    String password;
    @Col("Email")
    String email;
    @Col("DiaChi")
    String address;
    @Col("SoDienThoai")
    String phone;
    @Col("TrangThai")
    String status;
}
