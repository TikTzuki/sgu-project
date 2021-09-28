namespace Database
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class tbl_ChiTietThietBi
    {
        [Key]
        [Column(Order = 0)]
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int MaThietBi { get; set; }

        [Key]
        [Column(Order = 1)]
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int MaChiTietThongSo { get; set; }

        [StringLength(255)]
        public string GhiChu { get; set; }

        public virtual tbl_ChiTietThongSo tbl_ChiTietThongSo { get; set; }

        public virtual tbl_ThietBi tbl_ThietBi { get; set; }
    }
}
