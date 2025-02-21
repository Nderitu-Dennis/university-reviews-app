package com.mazindere.university_reviews_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UniversitiesController {

    @GetMapping("universities/mmu")
    public String getMMU(){
        return "universities/mmu";
    }
}
