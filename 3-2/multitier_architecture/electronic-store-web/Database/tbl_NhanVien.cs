namespace Database
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class tbl_NhanVien
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public tbl_NhanVien()
        {
            tbl_HoaDon = new HashSet<tbl_HoaDon>();
            tbl_PhieuNhap = new HashSet<tbl_PhieuNhap>();
        }

        [Key]
        public int MaNhanVien { get; set; }

        [Required]
        [StringLength(255)]
        public string Ten { get; set; }

        [Required]
        [StringLength(255)]
        public string GioiTinh { get; set; }

        [Required]
        [StringLength(255)]
        public string TaiKhoan { get; set; }

        [Required]
        [StringLength(255)]
        public string MatKhau { get; set; }

        [Required]
        [StringLength(255)]
        public string Email { get; set; }

        public int SDT { get; set; }

        [Required]
        [StringLength(255)]
        public string DiaChi { get; set; }

        public int CMND { get; set; }

        public int MaChucVu { get; set; }

        [StringLength(255)]
        public string TrangThai { get; set; }

        public virtual tbl_ChucVu tbl_ChucVu { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_HoaDon> tbl_HoaDon { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_PhieuNhap> tbl_PhieuNhap { get; set; }
    }
}
