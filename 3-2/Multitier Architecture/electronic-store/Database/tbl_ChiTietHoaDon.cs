namespace Database
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class tbl_ChiTietHoaDon
    {
        [Key]
        [Column(Order = 0)]
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int MaHoaDon { get; set; }

        [Key]
        [Column(Order = 1)]
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int MaThietBi { get; set; }

        public int SoLuong { get; set; }

        public int ThanhTien { get; set; }

        public int DonGia { get; set; }

        public virtual tbl_HoaDon tbl_HoaDon { get; set; }

        public virtual tbl_ThietBi tbl_ThietBi { get; set; }
    }
}
