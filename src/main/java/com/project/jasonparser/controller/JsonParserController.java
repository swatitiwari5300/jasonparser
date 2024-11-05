package com.project.jasonparser.controller;

import com.project.jasonparser.service.JsonParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/jsonparser")
public class JsonParserController {

    @Autowired
    private JsonParserService jsonParserService;

    @PostMapping
    public String parseJson(@RequestBody String jsonString){
        return jsonParserService.parseJson(jsonString);
    }
}
