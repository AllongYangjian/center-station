package com.allong.centerstation.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        System.err.println(""+statusCode);
        if(statusCode == 401){
            return "error/401";
        }else if(statusCode == 404){
            return "error/404";
        }else if(statusCode == 403){
            return "error/403";
        }else{
            return "error/500";
        }

    }
    @Override
    public String getErrorPath() {
        return "/error";
    }

//    @RequestMapping("/error/401")
//    public String _401(){
//        return "error/401";
//    }
//
//    @RequestMapping("/error/404")
//    public String _404(){
//        return "error/404";
//    }
//
//    @RequestMapping("/error/403")
//    public String _403(){
//        return "error/403";
//    }
//
//    @RequestMapping("/error/500")
//    public String _500(){
//        return "error/500";
//    }
}