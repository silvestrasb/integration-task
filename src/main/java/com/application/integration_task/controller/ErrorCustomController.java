package com.application.integration_task.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorCustomController implements ErrorController{

    @RequestMapping("/error")
    public String handleError(){
        return "error/general-error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
