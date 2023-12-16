package taci;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hanoitower.UsingAstar;
import infrastructure.Snapshot;
import queenpuzzle.UsingAStartQueenPuzzle;
import taci.UsingAStartEightPuzzle;

@SpringBootApplication
@RestController(value = "/")
public class AuthorizationServerApplication {

    @GetMapping
    @CrossOrigin
    public ResponseEntity<List<Snapshot>> luanchTaci() {
        UsingAStartEightPuzzle a = new UsingAStartEightPuzzle();
        a.resolve();
        return new ResponseEntity<List<Snapshot>>(a.snapshots, HttpStatus.OK);
    }

    @GetMapping("taci")
    @CrossOrigin
    public ResponseEntity<List<Snapshot>> luanch() {
        UsingAStartEightPuzzle a = new UsingAStartEightPuzzle();
        a.resolve();
        return new ResponseEntity<List<Snapshot>>(a.snapshots, HttpStatus.OK);
    }

    @GetMapping("hanoi-tower")
    @CrossOrigin
    public ResponseEntity<List<Snapshot>> resoleHaNoiTowerPuzzle() {
        UsingAstar a = new UsingAstar();
        a.resolve();
        return new ResponseEntity<List<Snapshot>>(a.snapshots, HttpStatus.OK);
    }

    @GetMapping("queen")
    @CrossOrigin
    public ResponseEntity<List<Snapshot>> resolveQueen(@RequestParam int step) {
        UsingAStartQueenPuzzle a = new UsingAStartQueenPuzzle();
        a.resolve(step);
        return new ResponseEntity<List<Snapshot>>(a.snapshots, HttpStatus.OK);
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }

}
