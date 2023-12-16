package tiktzuki.e_store.DTO;

import java.util.List;

import lombok.*;
import tiktzuki.e_store.infrastructure.repository.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("tbl_NhanVien")
public class Employee {
    @Id
    @Col("MaNhanVien")
    Integer id;
    @Col("Ten")
    String name;
    @Col("GioiTinh")
    String gender;
    @Col("TaiKhoan")
    String username;
    @Col("MatKhau")
    String password;
    @Col("Email")
    String email;
    @Col("SDT")
    String phone;
    @Col("DiaChi")
    String address;
    @Col("CMND")
    String identifyCard;
    @Col("TrangThai")
    String status;
    @Col("MaChucVu")
    Integer roleId;

//	Role role;
//	List<Order> orders;
//	List<ReceivingNote> receivingNotes;
}
