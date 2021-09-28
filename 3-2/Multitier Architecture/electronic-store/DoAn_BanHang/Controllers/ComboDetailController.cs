using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace DoAn_BanHang.Controllers
{
    public class ComboDetailController : Controller
    {
        // GET: ComboDetail
        public ActionResult ComboDetail(string id)
        {
            ViewBag.ID = id;

            if (String.IsNullOrEmpty(ViewBag.ID))
            {
                Response.Redirect("/Combo/Combo");
            }
            else
            {
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
                    Response.Redirect("/Combo/Combo");
                }
            }
            return View();
        }
    }
}