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
    public class CartController : Controller
    {
        // GET: Cart 
        public ActionResult Cart()
        {
            List<CartItem> giohang = Session["Cart"] as List<CartItem>;
            return View(giohang);
        }
        public JsonResult JSDecCart(FormCollection data)
        {
            JsonResult js = new JsonResult();
            if (String.IsNullOrEmpty(data["id"]))
            {
                Response.Redirect("/Home/Index");
            }
            else
            {
                int IDSP = int.Parse(data["id"]);
                Code code = new Code();
                tbl_ThietBi sp = code.Get_ThietBi().Where(m => m.MaThietBi == IDSP).FirstOrDefault();
                if (sp == null)
                {
                    js.Data = new
                    {
                        status = "ERR"
                    };
                }
                else
                {
                    int soluong = sp.SoLuong;

                    if (Session["Cart"] == null)
                    {
                        js.Data = new
                        {
                            status = "ERR"
                        };
                    }
                    List<CartItem> giohang = Session["Cart"] as List<CartItem>;
                    if (giohang.FirstOrDefault(m => m.SanPhamID == IDSP) == null)
                    {
                        js.Data = new
                        {
                            status = "ERR"
                        };
                    }
                    else
                    {
                        CartItem cardItem = giohang.FirstOrDefault(m => m.SanPhamID == IDSP);
                        Code db = new Code();
                        tbl_ThietBi a = db.Get_ThietBi().Where(m => m.MaThietBi == IDSP).FirstOrDefault();
                        if (a.SoLuong <= 0)
                        {
                            giohang.Remove(cardItem);
                            js.Data = new
                            {
                                status = "MAX",
                                tongtien = giohang.Sum(m => m.ThanhTien).ToString("#,##0").Replace(",", ".") + " VNĐ"
                            };
                        }
                        else
                        {
                            if (cardItem.SoLuong - 1 <= a.SoLuong && cardItem.SoLuong - 1 >= 1)
                            {

                                cardItem.SoLuong -= 1;
                                js.Data = new
                                {
                                    status = "OK",
                                    tongtien = giohang.Sum(m => m.ThanhTien).ToString("#,##0").Replace(",", ".") + " VNĐ",
                                    soluong = cardItem.SoLuong,
                                    thanhtien = cardItem.ThanhTien.ToString("#,##0").Replace(",", ".") + " VNĐ"
                                };
                            }
                            else
                            {
                                if (soluong < cardItem.SoLuong - 1)
                                {
                                    cardItem.SoLuong = soluong;
                                    js.Data = new
                                    {
                                        status = "MAXXX",
                                        tongtien = giohang.Sum(m => m.ThanhTien).ToString("#,##0").Replace(",", ".") + " VNĐ",
                                        soluong = cardItem.SoLuong,
                                        thanhtien = cardItem.ThanhTien.ToString("#,##0").Replace(",", ".") + " VNĐ"
                                    };
                                }
                                else
                                {
                                    js.Data = new
                                    {
                                        status = "MAXX"
                                    };
                                }
                            }
                        }
                    }
                }
            }
            return Json(js, JsonRequestBehavior.AllowGet);
        }
        public JsonResult JSAddCart(FormCollection data)
        {
            JsonResult js = new JsonResult();
            if (String.IsNullOrEmpty(data["soluong"]) || int.Parse(data["soluong"]) <= 0)
            {
                Response.Redirect("/Home/Index");
            }
            else
            {
                int IDSP = int.Parse(data["id"]);
                Code code = new Code();
                tbl_ThietBi sp = code.Get_ThietBi().Where(m => m.MaThietBi == IDSP).FirstOrDefault();
                if (sp == null)
                {
                    js.Data = new
                    {
                        status = "ERR"
                    };
                }
                else
                {
                    int soluong = sp.SoLuong;
                    if (soluong > 0)
                    {
                        if (Session["Cart"] == null) // Nếu giỏ hàng chưa được khởi tạo
                        {
                            Session["Cart"] = new List<CartItem>();  // Khởi tạo Session["giohang"] là 1 List<CartItem>
                        }
                        List<CartItem> giohang = Session["Cart"] as List<CartItem>;  // Gán qua biến giohang dễ code
                        // Kiểm tra xem sản phẩm khách đang chọn đã có trong giỏ hàng chưa
                        if (giohang.FirstOrDefault(m => m.SanPhamID == IDSP) == null) // ko co sp nay trong gio hang
                        {
                            if (int.Parse(data["soluong"])<= soluong)
                            {
                                CartItem newItem = new CartItem();
                                newItem.SanPhamID = sp.MaThietBi;
                                newItem.TenSanPham = sp.TenThietBi;
                                newItem.SoLuong = int.Parse(data["soluong"]);
                                newItem.Hinh = sp.HinhAnh;
                                newItem.DonGia = sp.GiaBan;
                                giohang.Add(newItem);  // Thêm CartItem vào giỏ 
                                js.Data = new
                                {
                                    status = "OK",
                                    tongtien = giohang.Sum(m => m.ThanhTien).ToString("#,##0").Replace(",", ".") + " VNĐ",
                                    soluong = newItem.SoLuong,
                                    thanhtien = newItem.ThanhTien.ToString("#,##0").Replace(",", ".") + " VNĐ"
                                };
                            }
                            else
                            {
                                js.Data = new
                                {
                                    status = "MAX"
                                };
                            }

                        }
                        else
                        {
                            // Nếu sản phẩm khách chọn đã có trong giỏ hàng thì không thêm vào giỏ nữa mà tăng số lượng lên.
                            CartItem cardItem = giohang.FirstOrDefault(m => m.SanPhamID == IDSP);
                            Code db = new Code();
                            tbl_ThietBi a = db.Get_ThietBi().Where(m => m.MaThietBi == IDSP).FirstOrDefault();
                            if (cardItem.SoLuong + int.Parse(data["soluong"])<= a.SoLuong)
                            {
                                cardItem.SoLuong += int.Parse(data["soluong"]);
                                js.Data = new
                                {
                                    status = "OK",
                                    tongtien = giohang.Sum(m => m.ThanhTien).ToString("#,##0").Replace(",", ".") + " VNĐ",
                                    soluong = cardItem.SoLuong,
                                    thanhtien = cardItem.ThanhTien.ToString("#,##0").Replace(",", ".") + " VNĐ"

                                };
                            }
                            else
                            {
                                cardItem.SoLuong = a.SoLuong;
                                js.Data = new
                                {
                                    status = "MAXX",
                                    tongtien = giohang.Sum(m => m.ThanhTien).ToString("#,##0").Replace(",", ".") + " VNĐ",
                                    soluong = cardItem.SoLuong,
                                    thanhtien = cardItem.ThanhTien.ToString("#,##0").Replace(",", ".") + " VNĐ"
                                };
                            }
                        }
                    }
                    else
                    {
                        string tongtien = "";
                        if (Session["Cart"] != null)
                        {
                            List<CartItem> giohang = Session["Cart"] as List<CartItem>;
                            CartItem cardItem = giohang.FirstOrDefault(m => m.SanPhamID == IDSP);
                            giohang.Remove(cardItem);
                            tongtien = giohang.Sum(m => m.ThanhTien).ToString("#,##0").Replace(",", ".") + " VNĐ";
                        }
                        js.Data = new
                        {
                            status = "MAX",
                            tongtien = tongtien,
                        };
                    }
                }
            }
            return Json(js, JsonRequestBehavior.AllowGet);
        }
        public JsonResult JSRemovesCart(FormCollection data)
        {
            List<CartItem> giohang = Session["Cart"] as List<CartItem>;
            int SanPhamID = int.Parse(data["id"]);
            if (String.IsNullOrEmpty(data["id"]))
            {
                Response.Redirect("/Home/Index");
            }
            CartItem itemXoa = giohang.FirstOrDefault(m => m.SanPhamID == SanPhamID);
            JsonResult js = new JsonResult();
            if (itemXoa != null)
            {
                giohang.Remove(itemXoa);
                js.Data = new
                {
                    status = "OK",
                    tongtien = giohang.Sum(m => m.ThanhTien).ToString("#,##0").Replace(",", ".") + " VNĐ"
                };
            }
            else
            {
                js.Data = new
                {
                    status = "ER"
                };
            }
            return Json(js, JsonRequestBehavior.AllowGet);
        }
    }
}