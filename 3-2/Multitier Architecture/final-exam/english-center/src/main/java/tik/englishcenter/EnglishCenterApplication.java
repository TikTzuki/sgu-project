package tik.englishcenter;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import tik.englishcenter.constants.Constants;
import tik.englishcenter.gui.LoginFrame;
import tik.englishcenter.models.*;
import tik.englishcenter.repositories.ExaminationRepository;
import tik.englishcenter.repositories.ExamineeRepository;
import tik.englishcenter.repositories.ManagerRepository;
import tik.englishcenter.repositories.RoomRepository;
import tik.englishcenter.services.ExaminationSerivce;
import tik.englishcenter.services.HyperUserDetailService;

@SpringBootApplication
public class EnglishCenterApplication {
    @Autowired
    HyperUserDetailService userManager;

    @Autowired
    ExaminationSerivce examinationSerivce;

    @Autowired
    ExaminationRepository examinationRepository;

    @Autowired
    ExamineeRepository examineeRepository;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    RoomRepository roomRepository;


    @Autowired
    LoginFrame loginFrame;

    public EnglishCenterApplication() {

    }

//    @PostConstruct
    public void startUp() {
        Manager manager = new Manager("manager", "0858267296", "M", "281231", "123");
        managerRepository.save(manager);

//        init user
        User user = new User(
                "tik",
                "password",
                new ArrayList<>()
        );
        List<Auth> auths = Stream.of(new Auth(user, Constants.ROLE_USER)).collect(Collectors.toList());
        user.setAuths(auths);
        userManager.createUser(user);
//        init certificates
        List<Certificate> certificates = examinationSerivce.createCertificates(
                Stream.of(
                        new Certificate("A2"),
                        new Certificate("B1")
                ).collect(Collectors.toList())
        );
//        init examination
        Examination ex = new Examination(
                "Init Examination",
                new Date(),
                new Date()
        );
        ex.certificateSequences = certificates.stream()
                .map(cert -> new CertificateSequence(cert, ex)).collect(Collectors.toList());

//      int room
        Room newRoom = new Room(certificates.get(0), ex);
//        init examinees
        List<Examinee> examinees = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            String name = RandomStringUtils.random(20, true, false);
            String phone = RandomStringUtils.random(10, false, true);
            String identity = RandomStringUtils.randomNumeric(9, 12);
            String gender = i % 3 == 0 ? "F" : "M";
            Examinee examinee = new Examinee(name, phone, gender, identity, ex, newRoom.certificate.id);
            examinees.add(examinee);
        }
        ex.certificateSequences.get(0).roomSequence++;
        newRoom.setName(String.format("%sP%2$3s", certificates.get(0).id, ex.certificateSequences.get(0).roomSequence).replace(' ', '0'));

        examinationRepository.save(ex);
        roomRepository.save(newRoom);
        examineeRepository.saveAll(examinees);
    }


    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(EnglishCenterApplication.class)
                .headless(false).run(args);

        EventQueue.invokeLater(() -> {
            EnglishCenterApplication ex = ctx.getBean(EnglishCenterApplication.class);
            ex.loginFrame.init();
        });
    }

}
