namespace Database
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class tbl_KhachHang
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public tbl_KhachHang()
        {
            tbl_HoaDon = new HashSet<tbl_HoaDon>();
        }

        [Key]
        public int MaKhachHang { get; set; }

        [Required]
        [StringLength(255)]
        public string Ten { get; set; }

        [Required]
        [StringLength(255)]
        public string TaiKhoan { get; set; }

        [Required]
        [StringLength(255)]
        public string MatKhau { get; set; }

        [Required]
        [StringLength(255)]
        public string Email { get; set; }

        [StringLength(255)]
        public string DiaChi { get; set; }

        [StringLength(255)]
        public string SoDienThoai { get; set; }

        [StringLength(25)]
        public string TrangThai { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<tbl_HoaDon> tbl_HoaDon { get; set; }
    }
}
