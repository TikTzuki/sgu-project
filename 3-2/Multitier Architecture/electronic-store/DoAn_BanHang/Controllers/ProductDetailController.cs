using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace DoAn_BanHang.Controllers
{
    public class ProductDetailController : Controller
    {
        // GET: ProductDetail
        public ActionResult ProductDetail(string id)
        {
            ViewBag.ID = id;

            if (String.IsNullOrEmpty(ViewBag.ID))
            {
                Response.Redirect("/Product/Product/1");
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
                    Response.Redirect("/Product/Product/1");
                }
            }
            return View();
        }
    }
}