namespace Database
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class tbl_ChiTietTrangThaiHoaDon
    {
        [Key]
        [Column(Order = 0)]
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int MaHoaDon { get; set; }

        [Key]
        [Column(Order = 1)]
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int MaTrangThaiHoaDon { get; set; }

        [Required]
        [StringLength(255)]
        public string NgayThucHien { get; set; }

        public virtual tbl_HoaDon tbl_HoaDon { get; set; }

        public virtual tbl_TrangThaiHoaDon tbl_TrangThaiHoaDon { get; set; }
    }
}
