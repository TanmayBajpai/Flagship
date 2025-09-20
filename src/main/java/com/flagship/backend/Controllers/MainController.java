package com.flagship.backend.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping(value = { "/", "/{path:[^\\.]*}", "/**/{path:[^\\.]*}" })
    public String index() {
        return "forward:/index.html";
    }
}
