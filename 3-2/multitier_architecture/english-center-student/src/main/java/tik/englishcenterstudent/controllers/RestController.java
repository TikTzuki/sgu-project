package tik.englishcenterstudent.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tik.englishcenterstudent.constants.RoutersConstants;
import tik.englishcenterstudent.models.Room;
import tik.englishcenterstudent.services.RoomService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    RoomService roomService;

    @GetMapping(RoutersConstants.ROOM + "/json")
    public ResponseEntity<Map<Object, Object>> getRoom(
            @RequestParam(value = "examinationId", required = false) Integer examinationId
    ) {
        Map<Object, Object> map = new HashMap<>();
        List<Room> rooms = roomService.getRoomsByExaminationId(examinationId);

        List<Room> response = rooms.stream().map(room ->
                new Room(room.id, room.name, null, null, new ArrayList<>(), room.createDate, room.updatedDate)
        ).collect(Collectors.toList());

        map.put("data", response);
        System.out.println(rooms);
        return ResponseEntity.ok(map);
    }
}
