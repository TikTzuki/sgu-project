namespace Database
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class tbl_LoaiHang
    {
        [Key]
        [Column(Order = 0)]
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int MaLoaiThietBi { get; set; }

        [Key]
        [Column(Order = 1)]
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int MaHang { get; set; }

        [StringLength(255)]
        public string GhiChu { get; set; }

        public virtual tbl_Hang tbl_Hang { get; set; }

        public virtual tbl_LoaiThietBi tbl_LoaiThietBi { get; set; }
    }
}
