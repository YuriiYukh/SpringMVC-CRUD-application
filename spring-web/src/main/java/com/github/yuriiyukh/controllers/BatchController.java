package com.github.yuriiyukh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.yuriiyukh.dao.PersonDao;

@Controller
@RequestMapping("/batch-update")
public class BatchController {
    
    private final PersonDao personDao;
    
    @Autowired
    public BatchController(PersonDao personDao) {
        this.personDao = personDao;
    }
    
    @GetMapping()
    public String index() {
        return "batch/index";
    }
    
    @GetMapping("/update")
    public String batchUpdate() {
        personDao.batchUpdate();
        return "redirect:/people";
    }
    

}
