using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Database;
using CodeForDB;
namespace DoAn_BanHang.Controllers
{
    public class HistoryController : Controller
    {
        // GET: History
        public ActionResult History()
        {
                if (Session["User"] == null)
                {
                    Response.Redirect("/Login/Login");
                }
            tbl_KhachHang kh = (tbl_KhachHang)Session["User"];
            Code code = new Code();
            List<tbl_HoaDon> hd = code.Get_HoaDon(kh.MaKhachHang);
            return View(hd);
        }
        public JsonResult JSPageHistory(FormCollection data)
        {
            if (Session["User"] == null)
            {
                Response.Redirect("/Login/Login");
            }
            JsonResult js = new JsonResult();
            int sotrang = int.Parse(data["sotrang"]);
            tbl_KhachHang kh = (tbl_KhachHang)Session["User"];
            Code code = new Code();
            List<tbl_HoaDon> hd = code.Get_HoaDon(kh.MaKhachHang);
            string context = "";
            int dem =0;
            foreach(tbl_HoaDon item in hd)
            {
                dem++;
                if(dem>=sotrang*8-8 && dem <= sotrang * 8)
                {
                    string sdt = "0" + item.SDT;
                    context += "<tr><th class='text-center' scope='col'>" + item.NguoiNhan + "</th><th class='text-center' scope='col'>" + item.DiaChiNhan + "</th>";
                    context += "<th class='text-center' scope='col'>" + sdt + "</th><th class='text-center' scope='col'>" + item.NgayLap + "</th>";
                    if (item.TrangThaiThanhToan == 1)
                    {
                        context += "<th class='text-center' scope='col'>Đã thanh toán</th>";
                    }
                    else
                    {
                        context += "<th class='text-center' scope='col'>Chưa thanh toán</th>";
                    }
                    if (item.MaTrangThaiDonHang == 1)
                    {
                        context += "<th class='text-center' scope='col'>Đã nhận hàng</th>";
                    }
                    else
                    {
                        context += "<th class='text-center' scope='col'>Đã đặt</th>";
                    }
                    context += "<th class='text-center' scope='col'>" + item.TongTien.ToString("#,##0").Replace(",", ".") + "VNĐ</th>";
                    context += "<th><a href='/HistoryDetail/HistoryDetail/" + item.MaHoaDon + "' class='btn btn-primary'>Xem chi tiết</a></th></tr>";
                }
            }
            js.Data = new
            {
                context = context
            };
            return Json(js,JsonRequestBehavior.AllowGet);
        }
    }
}