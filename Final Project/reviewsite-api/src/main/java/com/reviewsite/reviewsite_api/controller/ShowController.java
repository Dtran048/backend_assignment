package com.reviewsite.reviewsite_api.controller;

import com.reviewsite.reviewsite_api.entity.Show;
import com.reviewsite.reviewsite_api.repository.ShowRepository;
import com.reviewsite.reviewsite_api.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shows")
public class ShowController {
    @Autowired
    ShowService showService;

    @Autowired
    ShowRepository showRepo;

    @PostMapping
    public ResponseEntity<?> createShow(@RequestBody Show show){
        if(!showRepo.findByTitle(show.getTitle()).isEmpty()){
            return ResponseEntity.status(409)
                    .body(Map.of("status", "409", "message", "Title already in use","timestamps", new Date()));
        }else{
            return ResponseEntity.status(201).body(showService.createShow(show));
        }

    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.status(200).body(showService.getAllShows());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShow(
            @PathVariable String id){
        if (showRepo.findById(id).isEmpty()){
            return ResponseEntity.status(404)
                    .body(Map.of("status", "404", "message", "Show doesn't exist","timestamps", new Date()));
        }
        showService.deleteShow(id);
        return ResponseEntity.status(200)
                .body(Map.of("status", "200", "message", "Show deleted successfully","timestamps", new Date()));
    }
}
