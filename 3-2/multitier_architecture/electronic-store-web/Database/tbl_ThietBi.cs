namespace Database
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class tbl_ThietBi
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public tbl_ThietBi()
        {
            tbl_ChiTietHoaDon = new HashSet<tbl_ChiTietHoaDon>();
            tbl_ChiTietPhieuNhap = new HashSet<tbl_ChiTietPhieuNhap>();
            tbl_ChiTietThietBi = new HashSet<tbl_ChiTietThietBi>();
        }

        [Key]
        public int MaThietBi { get; set; }

        [Required]
        [StringLength(255)]
        public string TenThietBi { get; set; }

        public int MaLoaiThietBi { get; set; }

        public int SoLuong { get; set; }

        public int GiaBan { get; set; }

        public int MaHang { get; set; }

        public int MaTrangThaiKinhDoanh { get; set; }

        [StringLength(255)]
        public string MoTa { get; set; }

        [StringLength(255)]
        public string HinhAnh { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_ChiTietHoaDon> tbl_ChiTietHoaDon { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_ChiTietPhieuNhap> tbl_ChiTietPhieuNhap { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_ChiTietThietBi> tbl_ChiTietThietBi { get; set; }

        public virtual tbl_Hang tbl_Hang { get; set; }

        public virtual tbl_LoaiThietBi tbl_LoaiThietBi { get; set; }

        public virtual tbl_TrangThaiKinhDoanh tbl_TrangThaiKinhDoanh { get; set; }
    }
}
