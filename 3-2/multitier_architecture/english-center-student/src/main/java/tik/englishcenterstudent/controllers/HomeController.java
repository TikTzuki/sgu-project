package tik.englishcenterstudent.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import tik.englishcenterstudent.constants.RoutersConstants;
import tik.englishcenterstudent.dto.Router;
import tik.englishcenterstudent.dto.SignUpRequest;
import tik.englishcenterstudent.services.HyperUserDetailService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    List<Router> routers;


    @GetMapping("/")
    public RedirectView redirect(RedirectAttributes redirectAttrs) {
        return new RedirectView("home");
    }

    @GetMapping("/home")
    public String home(HttpServletResponse response, Authentication authentication, Model model) {
        model.addAttribute("routers", routers);
        return "home";
    }

    @GetMapping("/test")
    public ResponseEntity<Map<Object, Object>> test() {
        Map<Object, Object> response = new HashMap<>();
        response.put("name", "Tik");
        response.put("createDate", new Date().getTime());
        return ResponseEntity.ok(response);
    }

    @Autowired
    HyperUserDetailService userDetailService;

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute SignUpRequest signUpRequest, Model model) {
        String message = userDetailService.createUser(signUpRequest);
        if (message != null) {
            model.addAttribute("success", false);
            model.addAttribute("data", message);
        } else {
            model.addAttribute("success", true);
            model.addAttribute("data", signUpRequest.getUsername() + " Created");
        }
        model.addAttribute("callBackUrl", "/");
        return RoutersConstants.FORM_RESULT_REAL_PATH;
    }

    @GetMapping("/sign-up")
    public String test(Model model) {
        model.addAttribute("signUpRequest", new SignUpRequest());
        return RoutersConstants.SIGN_UP_REAL_PATH;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("signUpRequest", new SignUpRequest());
        return RoutersConstants.LOGIN_REAL_PATH;
    }

}
