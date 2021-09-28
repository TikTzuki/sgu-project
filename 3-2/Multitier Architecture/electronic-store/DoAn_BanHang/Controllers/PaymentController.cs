using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using DoAn_BanHang.Models;
using Database;
using CodeForDB;
namespace DoAn_BanHang.Controllers
{
    public class PaymentController : Controller
    {
        // GET: Payment
        public ActionResult Payment()
        {

            if (Session["User"] == null)
            {
                Response.Redirect("/Login/Login");
            }
            if (Session["Cart"] == null)
            {
                Response.Redirect("/Product/Product/1");
            }

            List<CartItem> giohang = Session["Cart"] as List<CartItem>;
            if (giohang.Count() == 0)
            {
                Response.Redirect("/Product/Product/1");
            }
            return View(giohang);
        }
        public JsonResult JSPayCheck(FormCollection form)
        {
            JsonResult js = new JsonResult();
            bool isNumeric = true;
            string finame = form["finame"];
            string miname = form["miname"];
            string laname = form["laname"];
            string add1 = form["add1"];
            string add2 = form["add2"];
            string country = form["country"];
            string state = form["state"];
            string city = form["city"];
            string phone = form["phone"];
            if (string.IsNullOrEmpty(finame) || string.IsNullOrEmpty(laname) || string.IsNullOrEmpty(add1) || string.IsNullOrEmpty(country) || string.IsNullOrEmpty(state) || string.IsNullOrEmpty(city) || string.IsNullOrEmpty(phone))
            {
                js.Data = new
                {
                    status = "EMPTY"
                };
            }
            else
            {
                foreach (char c in phone)
                {
                    if (!Char.IsNumber(c))
                    {
                        isNumeric = false;
                        break;
                    }
                }
                if (isNumeric == false)
                {
                    js.Data = new
                    {
                        status = "ERRPHONE"
                    };
                }
                else
                {
                    if (phone.Length != 10)
                    {
                        js.Data = new
                        {
                            status = "ERRPHONE"
                        };
                    }else
                    {
                        string address = add1 + " " + add2 + " " + state + " " + city + " " + country;
                        string name = finame + " " + miname + " " + laname;
                        if (Session["Cart"] == null)
                        {
                            Response.Redirect("/Product/Product/1");
                        }
                        Code code = new Code();
                        List<CartItem> giohang = Session["Cart"] as List<CartItem>;

                        if (giohang.Count() == 0)
                        {
                            Response.Redirect("/Product/Product/1");
                        }
                        bool status = true;
                        foreach (CartItem item in giohang)
                        {
                            if (item.SoLuong > code.Get_ThietBi().FirstOrDefault(m => m.MaThietBi == item.SanPhamID).SoLuong)
                            {
                                status = false;
                                item.SoLuong = code.Get_ThietBi().FirstOrDefault(m => m.MaThietBi == item.SanPhamID).SoLuong;
                            }
                        }
                        if (status == true)
                        {
                            if (Session["User"] == null)
                            {
                                 Response.Redirect("/Login/Login");
                            }
                            else
                            {
                                tbl_KhachHang u = (tbl_KhachHang)Session["User"];
                                tbl_HoaDon hd = new tbl_HoaDon();
                                hd.MaKhachHang = u.MaKhachHang;
                                hd.NgayLap = DateTime.Now.Day + "/" + DateTime.Now.Month + "/" + DateTime.Now.Year;
                                hd.MaTrangThaiDonHang = 2;
                                hd.DiaChiNhan = address;
                                hd.NguoiNhan = name;
                                hd.SDT = int.Parse(phone);
                                hd.TongTien = giohang.Sum(m => m.ThanhTien);
                                hd.TrangThaiThanhToan = 2;
                                code.AddObject(hd);
                                foreach (CartItem item in giohang)
                                {
                                    tbl_ChiTietHoaDon cthd = new tbl_ChiTietHoaDon();
                                    cthd.MaHoaDon = hd.MaHoaDon;
                                    cthd.MaThietBi = item.SanPhamID;
                                    cthd.SoLuong = item.SoLuong;
                                    cthd.ThanhTien = item.ThanhTien;
                                    cthd.DonGia = item.DonGia;
                                    code.AddObject(cthd);
                                    code.Save();
                                    tbl_ThietBi tb = code.Get_ThietBi().FirstOrDefault(m=>m.MaThietBi==item.SanPhamID);
                                    tb.SoLuong = tb.SoLuong - item.SoLuong;
                                    code.Save();
                                }
                                code.Save();
                                Session.Remove("Cart");
                                js.Data = new
                                {
                                    status = "OK"
                                };
                            }
                        }
                        else
                        {
                            js.Data = new
                            {
                                status = "ER"
                            };
                        }
                    }
                }
            }
            return Json(js, JsonRequestBehavior.AllowGet);
        }
        public JsonResult JSCheckCart()
        {
            JsonResult js = new JsonResult();
            if (Session["Cart"] == null)
            {
                js.Data = new
                {
                    status = "ERR"
                };
            }
            else
            {
                Code code = new Code();
                List<CartItem> giohang = Session["Cart"] as List<CartItem>;

                if (giohang.Count() == 0)
                {
                    js.Data = new
                    {
                        status = "ERR"
                    };
                }
                else
                {
                    bool status = true;
                    foreach (CartItem item in giohang)
                    {
                        if (item.SoLuong > code.Get_ThietBi().FirstOrDefault(m => m.MaThietBi == item.SanPhamID).SoLuong)
                        {
                            status = false;
                            item.SoLuong = code.Get_ThietBi().FirstOrDefault(m => m.MaThietBi == item.SanPhamID).SoLuong;
                        }
                    }
                    if (status == true)
                    {
                        js.Data = new
                        {
                            status = "OK"
                        };
                    }
                    else
                    {
                        js.Data = new
                        {
                            status = "ER"
                        };
                    }
                }
            }
            return Json(js, JsonRequestBehavior.AllowGet);
        }
    }
}