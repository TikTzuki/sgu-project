namespace Database
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class tbl_PhieuGiaoHang
    {
        [Key]
        public int MaPhieuGiaoHang { get; set; }

        [Required]
        [StringLength(255)]
        public string NguoiNhan { get; set; }

        [Required]
        [StringLength(255)]
        public string DiaChi { get; set; }

        [Required]
        [StringLength(255)]
        public string SoDienThoai { get; set; }

        [Required]
        [StringLength(255)]
        public string NgayGiao { get; set; }

        public int MaHoaDon { get; set; }
    }
}
