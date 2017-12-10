package newsportal.controller;

import newsportal.model.Writer;
import newsportal.repository.WriterRepository;
import newsportal.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WriterController {

    @Autowired
    WriterRepository writerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SecurityService securityService;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String create(@RequestParam String name, @RequestParam String password) {
        Writer writer = new Writer();
        writer.setName(name);
        writer.setPassword(passwordEncoder.encode(password));
        writerRepository.save(writer);
        securityService.autologin(name, password);
        return "redirect:/news";
    }
}
