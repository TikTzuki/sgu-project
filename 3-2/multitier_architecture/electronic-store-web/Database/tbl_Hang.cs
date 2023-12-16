namespace Database
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class tbl_Hang
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public tbl_Hang()
        {
            tbl_LoaiHang = new HashSet<tbl_LoaiHang>();
            tbl_ThietBi = new HashSet<tbl_ThietBi>();
        }

        [Key]
        public int MaHang { get; set; }

        [Required]
        [StringLength(255)]
        public string Ten { get; set; }

        public int MaTrangThaiKinhDoanh { get; set; }

        public virtual tbl_TrangThaiKinhDoanh tbl_TrangThaiKinhDoanh { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_LoaiHang> tbl_LoaiHang { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_ThietBi> tbl_ThietBi { get; set; }
    }
}
