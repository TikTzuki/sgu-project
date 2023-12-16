namespace Database
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class tbl_HoaDon
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public tbl_HoaDon()
        {
            tbl_ChiTietHoaDon = new HashSet<tbl_ChiTietHoaDon>();
            MaNhanVien = 1;
        }

        [Key]
        public int MaHoaDon { get; set; }

        public int MaKhachHang { get; set; }

        public int MaNhanVien { get; set; }

        public int TongTien { get; set; }

        [Required]
        [StringLength(255)]
        public string NgayLap { get; set; }

        public int MaTrangThaiDonHang { get; set; }

        [StringLength(50)]
        public string NgayNhan { get; set; }

        [StringLength(255)]
        public string DiaChiNhan { get; set; }

        public int TrangThaiThanhToan { get; set; }

        [StringLength(255)]
        public string NguoiNhan { get; set; }

        public int SDT { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_ChiTietHoaDon> tbl_ChiTietHoaDon { get; set; }

        public virtual tbl_KhachHang tbl_KhachHang { get; set; }

        public virtual tbl_NhanVien tbl_NhanVien { get; set; }

        public virtual tbl_TrangThaiHoaDon tbl_TrangThaiHoaDon { get; set; }
    }
}
