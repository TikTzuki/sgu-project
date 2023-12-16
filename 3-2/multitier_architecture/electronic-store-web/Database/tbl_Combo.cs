namespace Database
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class tbl_Combo
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public tbl_Combo()
        {
            tbl_ChiTietCombo = new HashSet<tbl_ChiTietCombo>();
        }

        [Key]
        public int MaCombo { get; set; }

        public int PhanTramGiamGia { get; set; }

        public int TongTien { get; set; }

        public int? MaTrangThaiKinhDoanh { get; set; }

        [StringLength(255)]
        public string Ten { get; set; }

        [StringLength(255)]
        public string HinhAnh { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_ChiTietCombo> tbl_ChiTietCombo { get; set; }

        public virtual tbl_TrangThaiKinhDoanh tbl_TrangThaiKinhDoanh { get; set; }
    }
}
