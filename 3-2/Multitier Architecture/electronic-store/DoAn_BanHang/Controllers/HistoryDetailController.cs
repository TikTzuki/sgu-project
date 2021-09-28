using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Database;
using CodeForDB;
namespace DoAn_BanHang.Controllers
{
    public class HistoryDetailController : Controller
    {
        // GET: HistoryDetail
        public ActionResult HistoryDetail(string id)
        {
            if (Session["User"] == null)
            {
                Response.Redirect("/Login/Login");
            }
            bool isNumeric = true;
            foreach (char c in id)
            {
                if (!Char.IsNumber(c))
                {
                    isNumeric = false;
                    break;
                }
            }
            if (isNumeric == false)
            {
                Response.Redirect("/History/History");
            }
            tbl_KhachHang kh = (tbl_KhachHang)Session["User"];
            Code code = new Code();
            List<tbl_ChiTietHoaDon> cthd = code.Get_ChiTietHoaDon(int.Parse(id));
            return View(cthd);
        }
    }
}