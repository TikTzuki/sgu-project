namespace Database
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class tbl_PhieuNhap
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public tbl_PhieuNhap()
        {
            tbl_ChiTietPhieuNhap = new HashSet<tbl_ChiTietPhieuNhap>();
        }

        [Key]
        public int MaPhieuNhap { get; set; }

        public int MaNhanVien { get; set; }

        [Required]
        [StringLength(255)]
        public string NgayLap { get; set; }

        public int TongTien { get; set; }

        public int MaNhaCungCap { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_ChiTietPhieuNhap> tbl_ChiTietPhieuNhap { get; set; }

        public virtual tbl_NhaCungCap tbl_NhaCungCap { get; set; }

        public virtual tbl_NhanVien tbl_NhanVien { get; set; }
    }
}
