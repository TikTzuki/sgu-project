package tik.englishcenterstudent.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tik.englishcenterstudent.constants.RoutersConstants;
import tik.englishcenterstudent.dto.Router;
import tik.englishcenterstudent.models.Certificate;
import tik.englishcenterstudent.services.ExaminationSerivce;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class AppConfigs implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/sign-up").setViewName("sign-up");
        registry.addViewController(RoutersConstants.FORM_RESULT).setViewName(RoutersConstants.FORM_RESULT_REAL_PATH);
    }

    @Autowired
    ExaminationSerivce examinationSerivce;

    @Bean
    public List<Router> routers() {
        return Stream.of(
                new Router(RoutersConstants.REGISTER, "fab fa-artstation", "Register"),
                new Router(RoutersConstants.FIND_ROOM, "fas fa-asterisk", "Find Room"),
                new Router(RoutersConstants.WATCH_ROOM, "fas fa-asterisk", "Rooms"),
                new Router(RoutersConstants.EXAMINEE_RESULT, "fas fa-atom", "Result"),
                new Router(RoutersConstants.ANALYSIS_ROOM, "fab fa-audible", "Analysis Room"),
                new Router(RoutersConstants.ANALYSIS_EXAMINEE, "fab fa-bandcamp", "Analysis Examinee")
        ).collect(Collectors.toList());
    }

    @Bean
    public List<Certificate> certificates() {
        return examinationSerivce.getAllCertificates();
    }

}
