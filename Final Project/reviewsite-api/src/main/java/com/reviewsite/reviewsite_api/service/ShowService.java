package com.reviewsite.reviewsite_api.service;


import com.reviewsite.reviewsite_api.entity.Show;
import com.reviewsite.reviewsite_api.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ShowService {
    @Autowired
    private ShowRepository showRepo;

    public Show createShow(Show show) {
        return showRepo.save(show);
    }

    public void deleteShow(String Id) {
        showRepo.deleteById(Id);
    }

    public List<Show> getAllShows() {
        return showRepo.findAll();
    }


}
