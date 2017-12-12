package newsportal.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Profile("production")
public class ProductionController {

    @RequestMapping("/")
    public String index() {
        return "redirect:/news/page/1";
    }
}
