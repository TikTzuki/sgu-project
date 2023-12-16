using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Database;
namespace CodeForDB
{
    public class Code
    {
        MyDB db = new MyDB();
        public List<tbl_ThietBi> Get_ThietBi()
        {
            return db.tbl_ThietBi.ToList();
        }
        public List<tbl_LoaiThietBi> Get_LoaiThietBi()
        {
            return db.tbl_LoaiThietBi.ToList();
        }
        public List<tbl_ThongSo> Get_ThongSo(int MaLoaiThietBi)
        {
            return db.tbl_ThongSo.Where(m=>m.MaLoaiThietBi==MaLoaiThietBi).ToList();
        }
        public List<tbl_ChiTietThongSo> Get_ChiTietThongSo(int MaThongSo)
        {
            return db.tbl_ChiTietThongSo.Where(m => m.MaThongSo == MaThongSo).ToList();
        }
        public tbl_ChiTietThongSo Get_ChiTietThongSoThietBi(int MaChiTietThietBi)
        {
            return db.tbl_ChiTietThongSo.Where(m => m.MaChiTietThongSo == MaChiTietThietBi).FirstOrDefault();
        }
        public tbl_ThongSo Get_ThongSoThietBi(int MaThongSo)
        {
            return db.tbl_ThongSo.Where(m => m.MaThongSo == MaThongSo).FirstOrDefault();
        }
        public List<tbl_ChiTietThietBi> Get_ChiTietThietBi(int MaThietBi)
        {
            return db.tbl_ChiTietThietBi.Where(m => m.MaThietBi == MaThietBi).ToList();
        }
        public tbl_ThietBi Get_ThietBi(int MaThietBi)
        {
            return db.tbl_ThietBi.Where(m => m.MaThietBi == MaThietBi).FirstOrDefault();
        }
        public tbl_Hang Get_Hang(int MaHang)
        {
            return db.tbl_Hang.Where(m => m.MaHang == MaHang).FirstOrDefault();
        }
        public tbl_KhachHang Get_KhachHang(string TaiKhoan)
        {
            return db.tbl_KhachHang.Where(m => m.TaiKhoan == TaiKhoan).FirstOrDefault();
        }
        public List<tbl_HoaDon> Get_HoaDon(int MaKhachHang)
        {
            return db.tbl_HoaDon.Where(m => m.MaKhachHang == MaKhachHang).ToList();
        }
        public List<tbl_ChiTietHoaDon> Get_ChiTietHoaDon(int MaHoaDon)
        {
            return db.tbl_ChiTietHoaDon.Where(m => m.MaHoaDon == MaHoaDon).ToList();
        }
        public void AddObject<T>(T obj)
        {
            db.Set(obj.GetType()).Add(obj);
        }
        public void DeleteObject<T>(T obj)
        {
            db.Set(obj.GetType()).Remove(obj);
        }
        public void Save()
        {
            db.SaveChanges();
        }
        public void DeleteList<T>(List<T> objs)
        {
            db.Set(objs[0].GetType()).RemoveRange(objs);
        }

    }
}
