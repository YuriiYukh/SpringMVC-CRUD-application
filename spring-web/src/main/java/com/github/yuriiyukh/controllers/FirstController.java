package com.github.yuriiyukh.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    private static final String RESULT = "result";

    @GetMapping("/hello")
    public String helloPage(HttpServletRequest request, Model model) {

        String name = request.getParameter("name");
        model.addAttribute("message", "Hello " + name);

        return "first/hello";
    }

    @GetMapping("/goodbye")
    public String goodbyePage() {
        return "first/goodbye";
    }

    @GetMapping("/calculator")
    public String calculator(HttpServletRequest request, Model model) {
        int a = Integer.parseInt(request.getParameter("a"));
        int b = Integer.parseInt(request.getParameter("b"));
        String action = request.getParameter("action");
        if (action == null) {
            model.addAttribute(RESULT, "");
        } else
        if (action.equals("+")) {
            model.addAttribute(RESULT, "result = " + Integer.toString(a + b));
        } else if (action.equals("-")) {
            model.addAttribute(RESULT, Integer.toString(a - b));
        } else if (action.equals("*")) {
            model.addAttribute(RESULT, Integer.toString(a * b));
        } else if (action.equals("/")) {
            model.addAttribute(RESULT, Integer.toString(a / b));
        } else {
            model.addAttribute(RESULT, "FAIL");
        }
        return "first/hello";
    }

}
