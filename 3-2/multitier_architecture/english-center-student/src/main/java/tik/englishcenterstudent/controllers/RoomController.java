package tik.englishcenterstudent.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tik.englishcenterstudent.constants.RoutersConstants;
import tik.englishcenterstudent.dto.Router;
import tik.englishcenterstudent.models.Certificate;
import tik.englishcenterstudent.models.Examination;
import tik.englishcenterstudent.models.Room;
import tik.englishcenterstudent.services.ExaminationSerivce;
import tik.englishcenterstudent.services.ExamineeService;
import tik.englishcenterstudent.services.RoomService;

import java.util.List;

@Controller
public class RoomController {
    @Autowired
    List<Router> routers;

    @Autowired
    RoomService roomService;

    @Autowired
    ExaminationSerivce examinationSerivce;

    @Autowired
    ExamineeService examineeService;

    @GetMapping(RoutersConstants.FIND_ROOM)
    public String findRoom(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "phone", required = false) String phone,
            Model model
    ) {
        List<Room> rooms = examineeService.getRoomOfExaminee(name, phone);
        model.addAttribute("rooms", rooms);
        model.addAttribute("routers", routers);
        return RoutersConstants.FIND_ROOM_REAL_PATH;
    }

    @GetMapping(RoutersConstants.WATCH_ROOM)
    public String viewRoom(
            @RequestParam(value = "roomId", required = false) Integer roomId,
            Model model
    ) {
        Room room = null;

        List<Examination> examinations = examinationSerivce.getAllExamination();
        if (roomId == null) {
            Examination lastExamination = examinations.get(examinations.size() - 1);
            room = lastExamination.rooms.get(lastExamination.rooms.size() - 1);
        } else {
            room = roomService.getRoom(roomId);
        }

        model.addAttribute("room", room);
        model.addAttribute("examinations", examinations);
        model.addAttribute("routers", routers);
        return RoutersConstants.WATCH_ROOM_REAL_PATH;
    }

    @GetMapping(RoutersConstants.ANALYSIS_ROOM)
    public String analysisRoom(Model model) {
        List<Certificate> certificates = examinationSerivce.getAllCertificates();
        model.addAttribute("certificates", certificates);
        model.addAttribute("routers", routers);
        return RoutersConstants.ANALYSIS_ROOM_REAL_PATH;
    }
}
