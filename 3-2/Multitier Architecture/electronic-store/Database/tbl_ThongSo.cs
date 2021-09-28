namespace Database
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class tbl_ThongSo
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public tbl_ThongSo()
        {
            tbl_ChiTietThongSo = new HashSet<tbl_ChiTietThongSo>();
        }

        [Key]
        public int MaThongSo { get; set; }

        [Required]
        [StringLength(255)]
        public string Ten { get; set; }

        public int MaLoaiThietBi { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_ChiTietThongSo> tbl_ChiTietThongSo { get; set; }

        public virtual tbl_LoaiThietBi tbl_LoaiThietBi { get; set; }
    }
}
