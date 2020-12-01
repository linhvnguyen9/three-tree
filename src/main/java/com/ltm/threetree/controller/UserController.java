package com.ltm.threetree.controller;

import com.ltm.threetree.entity.Player;
import com.ltm.threetree.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/players")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping(produces = "appilcation/json", value = "/login")
    public ResponseEntity<?> checkPlayer(@RequestBody Player player){
        Player check = userService.checkLogin(player);
        if(Objects.nonNull(check)){
            return new ResponseEntity<>(player,HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping(produces = "application/json", value = "/register")
    public ResponseEntity<?> addPlayer(@RequestBody Player player){
        Player created = userService.addPlayer(player);
        logger.info("created: {}");

        try {
            logger.info("created successfully");
            return new ResponseEntity<>(created, HttpStatus.OK);
        }catch (NullPointerException e){
            logger.info("error: {}", e);
            return new ResponseEntity<>(HttpStatus.SEE_OTHER.getReasonPhrase(),HttpStatus.SEE_OTHER);
        }
    }

    @GetMapping(produces = "application/json", value = "{id}")
    public ResponseEntity<?> findPlayerById(@PathVariable("id") String id){
        Player player =userService.findPlayerById(id);
        logger.info("find: {}");

        if(Objects.nonNull(player)){
            logger.info("Find successfully");
            return new ResponseEntity<>(player, HttpStatus.OK);
        }else{
            logger.info("Not successfully");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> findAllPlayerByMoney(){
        List<Player> playerList = userService.findAllPlayer();
        return ResponseEntity.ok(playerList);
    }

    @PutMapping(produces = "application/json", value = "/edit-player/{id}")
    public ResponseEntity<?> editPlayer(@RequestBody Player player, @PathVariable("id") String id){
        Player updated = userService.editPlayer(player);
        logger.info("updated: {}");
        try {
            logger.info("updated successfully");
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }catch (NullPointerException e){
            logger.info("error: {}", e);
            return new ResponseEntity<>(HttpStatus.SEE_OTHER.getReasonPhrase(),HttpStatus.SEE_OTHER);
        }
    }

    @PutMapping(produces = "application/json", value = "/update-money/{id}")
    public ResponseEntity<?> updateMoney(@RequestBody Player player, @PathVariable("id") String id){
        Player updated = userService.updateMoney(player);
        logger.info("updated: {}");
        try {
            logger.info("ọc kê ọc kê");
            return new ResponseEntity<>(updated,HttpStatus.OK);
        }catch (NullPointerException e){
            logger.info("error: {}", e);
            return new ResponseEntity<>(HttpStatus.SEE_OTHER.getReasonPhrase(),HttpStatus.SEE_OTHER);
        }
    }

    @DeleteMapping(produces = "application/json", value = "{/id}")
    public ResponseEntity<?> deletePlayer(@PathVariable("id") String id){
        Player deletePlayer = userService.deletePlayer(id);
        if(id.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else
            return new ResponseEntity<>(HttpStatus.OK);
    }
}
