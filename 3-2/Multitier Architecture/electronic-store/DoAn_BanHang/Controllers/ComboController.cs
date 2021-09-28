using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Database;
using CodeForDB;
using System.Text.RegularExpressions;

namespace DoAn_BanHang.Controllers
{
    public class ComboController : Controller 
    {
        // GET: Combo
        public ActionResult Combo()
        {
            return View();
        }
        public string XoaDau(string str)
        {
            string result = str.ToLower();
            result = Regex.Replace(result, "à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ|/g", "a");
            result = Regex.Replace(result, "è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ|/g", "e");
            result = Regex.Replace(result, "ì|í|ị|ỉ|ĩ|/g", "i");
            result = Regex.Replace(result, "ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ|/g", "o");
            result = Regex.Replace(result, "ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ|/g", "u");
            result = Regex.Replace(result, "ỳ|ý|ỵ|ỷ|ỹ|/g", "y");
            result = Regex.Replace(result, "đ", "d");
            return result;
        }
        public JsonResult JSSearch(FormCollection data)
        {
            JsonResult js = new JsonResult();
            Code code = new Code();
            string search = data["search"];
            int price = int.Parse(data["pr"]);
            int SLDV = 0;
            List<tbl_Combo> tbs = code.Get_Combo().Where(m => XoaDau(m.Ten).Contains(XoaDau(search))).OrderBy(m => m.TongTien).ToList();
            if (price == 2)
            {
                tbs = code.Get_Combo().Where(m => XoaDau(m.Ten).Contains(XoaDau(search))).OrderByDescending(m => m.TongTien).ToList();
            }
            string context = "<div class='tab-pane fade active in' id='trang_1'>";
            foreach (tbl_Combo item in tbs)
            {
                SLDV++;
                context += "<div class='col-md-4 col-sm-12'><div class='thumbnail' style='background: linear-gradient( 45deg, rgba(29, 236, 197, 0.5), rgba(91, 14, 214, 0.5) 100% );'><div class='single-products'><div class='productinfo text-center'><img src='" + item.HinhAnh + "' alt='' style='height: 250px'/><h2>" + item.TongTien.ToString("#,##0").Replace(",", ".") + " VNĐ</h2><p>";
                Char[] mang = item.Ten.ToCharArray();
                for (int i = 0; i < item.Ten.Length && i < 25; i++)
                {
                    context += mang[i];
                }
                if (item.Ten.Length >= 25)
                {
                    context += "...";
                }
                context += "</p><a href='javascript: addcart(" + item.Ten + ",1)' class='btn add-to-cart' style='margin-right: 4px'><i class='fa fa-shopping-cart'></i>Add to cart</a><a href='/ComboDetail/ComboDetail/" + item.MaCombo + "' class='btn add-to-cart'><i class='fa fa-eye'></i>Detail</a></div></div></div></div>";
                if (SLDV % 9 == 0)
                {
                    context += "</div><div class='tab-pane fade' id='trang_1'>";
                }
            }
            context += "</div>";
            js.Data = new
            {
                status = "OK",
                context = context,
                soluong = Math.Ceiling((double)SLDV / 9)
            };
            return Json(js, JsonRequestBehavior.AllowGet);
        }
    }
}