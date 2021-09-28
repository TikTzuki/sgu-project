namespace Database
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class tbl_TrangThaiKinhDoanh
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public tbl_TrangThaiKinhDoanh()
        {
            tbl_Hang = new HashSet<tbl_Hang>();
            tbl_LoaiThietBi = new HashSet<tbl_LoaiThietBi>();
            tbl_ThietBi = new HashSet<tbl_ThietBi>();
        }

        [Key]
        public int MaTrangThaiKinhDoanh { get; set; }

        [Required]
        [StringLength(255)]
        public string Ten { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_Hang> tbl_Hang { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_LoaiThietBi> tbl_LoaiThietBi { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_ThietBi> tbl_ThietBi { get; set; }
    }
}
