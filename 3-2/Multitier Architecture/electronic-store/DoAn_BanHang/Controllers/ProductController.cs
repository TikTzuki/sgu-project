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
    public class ProductController : Controller
    {
        // GET: Product
        public ActionResult Product(string id)
        {
            ViewBag.IDL = id;

            if (String.IsNullOrEmpty(ViewBag.IDL))
            {
                ViewBag.IDL = "1";
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
                    ViewBag.IDL = "1";
                }
            }
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
            string[] arrListStr = data["testval"].Split('/');
            int IDL = int.Parse(data["IDL"]);
            string search = data["search"];
            int price = int.Parse(data["pr"]);
            int gia = int.Parse(data["gia"]);
            int ming = 0;
            int maxg = 99999999; 
            if (gia == 0)
            {
                ming = 0;
                maxg = 99999999;
            }
            else if (gia == 1)
            {
                ming = 0;
                maxg = 1000000;
            }
            else if (gia == 2)
            {
                ming = 1000000;
                maxg = 5000000;
            }
            else if (gia == 3)
            {
                ming = 5000000;
                maxg = 10000000;
            }
            else if (gia == 4)
            {
                ming = 10000000;
                maxg = 99999999;
            }
            int SLDV = 0;
            int SLTS = code.Get_ThongSo(IDL).ToList().Count();
            List<tbl_ThietBi> tbs = code.Get_ThietBi().Where(m => m.MaLoaiThietBi == IDL && XoaDau(m.TenThietBi).Contains(XoaDau(search)) && m.MaTrangThaiKinhDoanh == 1 && m.SoLuong > 1 && m.GiaBan>=ming && m.GiaBan<=maxg).OrderBy(m => m.GiaBan).ToList();
            if (price==2)
            {
                tbs = code.Get_ThietBi().Where(m => m.MaLoaiThietBi == IDL && XoaDau(m.TenThietBi).Contains(XoaDau(search)) && m.MaTrangThaiKinhDoanh == 1 && m.SoLuong > 1 && m.GiaBan >= ming && m.GiaBan <= maxg).OrderByDescending(m => m.GiaBan).ToList();
            }
            string context = "<div class='tab-pane fade active in' id='trang_1'>";
            foreach (tbl_ThietBi item in tbs)
            {
                int dem = 0;
                for (int i = 0; i < arrListStr.Length - 1; i++)
                {
                    if (code.Get_ChiTietThietBi(item.MaThietBi).Where(m => m.MaChiTietThongSo == int.Parse(arrListStr[i])).FirstOrDefault() != null)
                    {
                        dem++;
                    }
                }
                if (SLTS == dem)
                {
                    SLDV++;
                    context += "<div class='col-md-4 col-sm-12'><div class='thumbnail' style='background: linear-gradient( 45deg, rgba(29, 236, 197, 0.5), rgba(91, 14, 214, 0.5) 100% );'><div class='single-products'><div class='productinfo text-center'><img height='260px' src='/Content/images/uploads/"+item.HinhAnh+"' alt='' /><h2>" + item.GiaBan.ToString("#,##0").Replace(",", ".") + " VNĐ</h2><p>";
                    Char[] mang = item.TenThietBi.ToCharArray();
                    for (int i = 0; i < item.TenThietBi.Length && i < 25; i++)
                    {
                        context += mang[i];
                    }
                    if (item.TenThietBi.Length >= 25)
                    {
                        context += "...";
                    }
                    context += "</p><a href='javascript: addcart(" + item.MaThietBi + ",1)' class='btn add-to-cart' style='margin-right: 4px'><i class='fa fa-shopping-cart'></i>Add to cart</a><a href='/ProductDetail/ProductDetail/" + item.MaThietBi + "' class='btn add-to-cart'><i class='fa fa-eye'></i>Detail</a></div></div></div></div>";
                    if (SLDV % 9 == 0)
                    {
                        context += "</div><div class='tab-pane fade' id='trang_1'>";
                    }
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