using System;
using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Entity;
using System.Linq;

namespace Database
{
    public partial class MyDB : DbContext
    {
        public MyDB()
            : base("name=MyDB1")
        {
        }

        public virtual DbSet<tbl_ChiTietHoaDon> tbl_ChiTietHoaDon { get; set; }
        public virtual DbSet<tbl_ChiTietPhieuNhap> tbl_ChiTietPhieuNhap { get; set; }
        public virtual DbSet<tbl_ChiTietThietBi> tbl_ChiTietThietBi { get; set; }
        public virtual DbSet<tbl_ChiTietThongSo> tbl_ChiTietThongSo { get; set; }
        public virtual DbSet<tbl_ChucVu> tbl_ChucVu { get; set; }
        public virtual DbSet<tbl_Hang> tbl_Hang { get; set; }
        public virtual DbSet<tbl_HoaDon> tbl_HoaDon { get; set; }
        public virtual DbSet<tbl_KhachHang> tbl_KhachHang { get; set; }
        public virtual DbSet<tbl_LoaiHang> tbl_LoaiHang { get; set; }
        public virtual DbSet<tbl_LoaiThietBi> tbl_LoaiThietBi { get; set; }
        public virtual DbSet<tbl_NhaCungCap> tbl_NhaCungCap { get; set; }
        public virtual DbSet<tbl_NhanVien> tbl_NhanVien { get; set; }
        public virtual DbSet<tbl_PhieuGiaoHang> tbl_PhieuGiaoHang { get; set; }
        public virtual DbSet<tbl_PhieuNhap> tbl_PhieuNhap { get; set; }
        public virtual DbSet<tbl_ThietBi> tbl_ThietBi { get; set; }
        public virtual DbSet<tbl_ThongSo> tbl_ThongSo { get; set; }
        public virtual DbSet<tbl_TrangThaiHoaDon> tbl_TrangThaiHoaDon { get; set; }
        public virtual DbSet<tbl_TrangThaiKinhDoanh> tbl_TrangThaiKinhDoanh { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<tbl_ChiTietThongSo>()
                .HasMany(e => e.tbl_ChiTietThietBi)
                .WithRequired(e => e.tbl_ChiTietThongSo)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<tbl_Hang>()
                .HasMany(e => e.tbl_LoaiHang)
                .WithRequired(e => e.tbl_Hang)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<tbl_Hang>()
                .HasMany(e => e.tbl_ThietBi)
                .WithRequired(e => e.tbl_Hang)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<tbl_HoaDon>()
                .Property(e => e.NgayNhan)
                .IsUnicode(false);

            modelBuilder.Entity<tbl_HoaDon>()
                .HasMany(e => e.tbl_ChiTietHoaDon)
                .WithRequired(e => e.tbl_HoaDon)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<tbl_LoaiThietBi>()
                .HasMany(e => e.tbl_LoaiHang)
                .WithRequired(e => e.tbl_LoaiThietBi)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<tbl_LoaiThietBi>()
                .HasMany(e => e.tbl_ThietBi)
                .WithRequired(e => e.tbl_LoaiThietBi)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<tbl_LoaiThietBi>()
                .HasMany(e => e.tbl_ThongSo)
                .WithRequired(e => e.tbl_LoaiThietBi)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<tbl_NhanVien>()
                .HasMany(e => e.tbl_PhieuNhap)
                .WithRequired(e => e.tbl_NhanVien)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<tbl_PhieuNhap>()
                .HasMany(e => e.tbl_ChiTietPhieuNhap)
                .WithRequired(e => e.tbl_PhieuNhap)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<tbl_ThietBi>()
                .HasMany(e => e.tbl_ChiTietHoaDon)
                .WithRequired(e => e.tbl_ThietBi)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<tbl_ThietBi>()
                .HasMany(e => e.tbl_ChiTietPhieuNhap)
                .WithRequired(e => e.tbl_ThietBi)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<tbl_ThietBi>()
                .HasMany(e => e.tbl_ChiTietThietBi)
                .WithRequired(e => e.tbl_ThietBi)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<tbl_ThongSo>()
                .HasMany(e => e.tbl_ChiTietThongSo)
                .WithRequired(e => e.tbl_ThongSo)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<tbl_TrangThaiHoaDon>()
                .HasMany(e => e.tbl_HoaDon)
                .WithRequired(e => e.tbl_TrangThaiHoaDon)
                .HasForeignKey(e => e.MaTrangThaiDonHang)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<tbl_TrangThaiKinhDoanh>()
                .HasMany(e => e.tbl_Hang)
                .WithRequired(e => e.tbl_TrangThaiKinhDoanh)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<tbl_TrangThaiKinhDoanh>()
                .HasMany(e => e.tbl_ThietBi)
                .WithRequired(e => e.tbl_TrangThaiKinhDoanh)
                .WillCascadeOnDelete(false);
        }
    }
}
