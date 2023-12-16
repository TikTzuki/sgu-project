namespace Database
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class tbl_LoaiThietBi
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public tbl_LoaiThietBi()
        {
            tbl_LoaiHang = new HashSet<tbl_LoaiHang>();
            tbl_ThietBi = new HashSet<tbl_ThietBi>();
            tbl_ThongSo = new HashSet<tbl_ThongSo>();
        }

        [Key]
        public int MaLoaiThietBi { get; set; }

        [Required]
        [StringLength(255)]
        public string TenLoaiThietBi { get; set; }

        public int MaTrangThaiKinhDoanh { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_LoaiHang> tbl_LoaiHang { get; set; }

        public virtual tbl_TrangThaiKinhDoanh tbl_TrangThaiKinhDoanh { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_ThietBi> tbl_ThietBi { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_ThongSo> tbl_ThongSo { get; set; }
    }
}
