namespace Database
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class tbl_ChiTietThongSo
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public tbl_ChiTietThongSo()
        {
            tbl_ChiTietThietBi = new HashSet<tbl_ChiTietThietBi>();
        }

        [Key]
        public int MaChiTietThongSo { get; set; }

        [Required]
        [StringLength(255)]
        public string Ten { get; set; }

        public int MaThongSo { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_ChiTietThietBi> tbl_ChiTietThietBi { get; set; }

        public virtual tbl_ThongSo tbl_ThongSo { get; set; }
    }
}
