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
    public class LoginController : Controller
    {
        // GET: Login
        public ActionResult Login()
        {
            if (Session["User"] != null)
            {
                Response.Redirect("/Home/Index");
            }
            return View();
        }
        public JsonResult JSDangNhap(FormCollection data)
        {
            JsonResult js = new JsonResult();
            string tk = data["tk"];
            string mk = data["mk"];
            if (string.IsNullOrEmpty(tk) || string.IsNullOrEmpty(mk))
            {
                js.Data = new
                {
                    status = "ERR"
                };
            }
            else
            {
                if (tk.Length < 8 || mk.Length < 8)
                {
                    js.Data = new
                    {
                        status = "ERR"
                    };
                }
                else
                {
                    Code code = new Code();
                    if (code.Get_KhachHang(tk) == null)
                    {
                        js.Data = new
                        {
                            status = "ERR"
                        };
                    }
                    else
                    {
                        if (code.Get_KhachHang(tk).MatKhau == Encryptor.MD5Hash(mk))
                        {
                            Session["User"] = code.Get_KhachHang(tk);
                            Session.Timeout = 60;
                            js.Data = new
                            {
                                status = "OK"
                            };
                        }
                        else
                        {
                            js.Data = new
                            {
                                status = "ERR"
                            };
                        }
                    }
                }
            }
            return Json(js, JsonRequestBehavior.AllowGet);
        }
        public JsonResult JSDangKi(FormCollection data)
        {
            if (data["rtk"] == null)
            {
                Response.Redirect("/Home/Index");
            }
            Code code = new Code();
            string tk = data["rtk"];
            string mk = data["rmk"];
            string rmk = data["remk"];
            string email = data["remail"];
            string ten = data["rten"];
            JsonResult js = new JsonResult();
            if (String.IsNullOrEmpty(tk) || String.IsNullOrEmpty(mk) || String.IsNullOrEmpty(rmk) || String.IsNullOrEmpty(email))
            {
                js.Data = new
                {
                    status = "ERR"
                };
            }
            else
            {
                if (mk != rmk)
                {
                    js.Data = new
                    {
                        status = "ERMK"
                    };
                }
                else
                {
                    var RexPW = new Regex(@"^(?=.*\d)(?=.*[A-Z])(?=.*\W).{8,32}$");
                    var RexUID = new Regex(@"^[a-z_][a-z0-9_\.\s]{8,32}$");
                    var RexEmail = new Regex(@"^[a-z][a-z0-9_\.]{4,31}@[a-z0-9]{2,}(\.[a-z0-9]{2,4}){1,2}$");
                    if (!RexPW.IsMatch(mk))
                    {
                        js.Data = new
                        {
                            status = "ER"
                        };
                    }
                    else if (!RexUID.IsMatch(tk))
                    {
                        js.Data = new
                        {
                            status = "ER"
                        };
                    }
                    else if (!RexEmail.IsMatch(email))
                    {
                        js.Data = new
                        {
                            status = "ER"
                        };
                    }
                    else if (ten.Length < 2)
                    {
                        js.Data = new
                        {
                            status = "ER"
                        };
                    }
                    else
                    {
                        if (code.Get_KhachHang(tk) != null)
                        {
                            js.Data = new
                            {
                                status = "ERTK"
                            };
                        }
                        else
                        {
                            tbl_KhachHang kh = new tbl_KhachHang();
                            kh.TaiKhoan = tk;
                            kh.MatKhau = Encryptor.MD5Hash(mk);
                            kh.Email = email;
                            kh.Ten = ten;
                            code.AddObject(kh);
                            code.Save();
                            js.Data = new
                            {
                                status = "OK"
                            };
                        }
                    }
                }
            }

            return Json(js, JsonRequestBehavior.AllowGet);
        }
    }
}