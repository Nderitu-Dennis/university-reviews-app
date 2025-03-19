package com.mazindere.university_reviews_app.controller;

import com.mazindere.university_reviews_app.service.FunFactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/funfact")
public class FunFactController {

   private FunFactService funFactService;

    @Autowired
    public FunFactController(FunFactService funFactService) {
        this.funFactService = funFactService;
    }

    @GetMapping("/{universityName}")
    @ResponseBody
    public String getFunFact(@PathVariable String universityName) {
        return funFactService.generateFunFact(universityName);
    }
    }


