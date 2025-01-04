package cz.uhk.kppro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "nice";
    }

    @GetMapping("/403")
    @ResponseBody
    public String forbidden() {
        return "<h1 style='color: red'>Forbidden</h1>";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "<h1 style='color: green'>You are admin!</h1>";
    }
}
