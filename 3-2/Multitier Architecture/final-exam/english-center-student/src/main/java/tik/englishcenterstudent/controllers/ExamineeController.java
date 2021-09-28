package tik.englishcenterstudent.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import tik.englishcenterstudent.constants.RoutersConstants;
import tik.englishcenterstudent.dto.Router;
import tik.englishcenterstudent.models.Certificate;
import tik.englishcenterstudent.models.Examination;
import tik.englishcenterstudent.models.Examinee;
import tik.englishcenterstudent.services.ExaminationSerivce;
import tik.englishcenterstudent.services.ExamineeService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class ExamineeController {
    @Autowired
    List<Router> routers;

    @Autowired
    ExamineeService examineeService;

    @Autowired
    ExaminationSerivce examinationSerivce;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping(RoutersConstants.REGISTER)
    public String examinee(Model model) {
        List<Examination> examinations = examinationSerivce.getAllExamination();
        Examination currentExamination = examinations.get(examinations.size() - 1);
        model.addAttribute("examination", currentExamination);
        Examinee examinee = new Examinee();
        examinee.examination = currentExamination;
        model.addAttribute("examinee", examinee);
        model.addAttribute("routers", routers);
        return RoutersConstants.REGISTER_REAL_PATH;
    }

    @PostMapping(RoutersConstants.REGISTER)
    public String createExaminee(@ModelAttribute @Valid Examinee examinee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Examination> examinations = examinationSerivce.getAllExamination();
            model.addAttribute("examination", examinations.get(examinations.size() - 1));
            model.addAttribute("examinee", examinee);
            model.addAttribute("routers", routers);
            return RoutersConstants.REGISTER_REAL_PATH;
        }
        List<Examinee> examinees = Stream.of(examinee).collect(Collectors.toList());
        try {
            examineeService.createExaminee(examinees);
            model.addAttribute("success", true);
            model.addAttribute("data", examinee.name + " Registed!");
        } catch (HttpMessageConversionException exception) {
            model.addAttribute("success", false);
            model.addAttribute("data", exception.getMessage());
        }


        model.addAttribute("callBackUrl", "/");
        return RoutersConstants.FORM_RESULT_REAL_PATH;
    }

    @GetMapping(RoutersConstants.EXAMINEE_RESULT)
    public String examineeResult(@RequestParam(defaultValue = "null", required = false) String examineeId, Model model) {

        model.addAttribute("examinee", examineeService.getByExamineeId(examineeId));

        model.addAttribute("routers", routers);
        return RoutersConstants.EXAMINEE_RESULT_REAL_PATH;
    }

    @GetMapping(RoutersConstants.ANALYSIS_EXAMINEE)
    public String analysisExaminee(Model model) {
        List<Certificate> certificates = examinationSerivce.getAllCertificates();
        model.addAttribute("certificates", certificates);
        model.addAttribute("routers", routers);
        return RoutersConstants.ANALYSIS_EXAMINEE_REAL_PATH;
    }
}
