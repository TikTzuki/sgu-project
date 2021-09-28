using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Database;
using CodeForDB;
using DoAn_BanHang.Models;
using System.Text.RegularExpressions;

namespace DoAn_BanHang.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            return View();
        }
        public JsonResult DangXuat(FormCollection data)
        {
            if (data["logout"] == null)
            {
                Response.Redirect("/Home/Index");
            }
            JsonResult js = new JsonResult();
            if (Session["User"] != null)
            {
                Session.Remove("User");
            }
            js.Data = new
            {
                status = "OK"
            };
            return Json(js, JsonRequestBehavior.AllowGet);
        }
    }
}