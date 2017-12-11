package newsportal.controller;

import newsportal.model.Writer;
import newsportal.repository.WriterRepository;
import newsportal.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class WriterController {

    @Autowired
    WriterRepository writerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SecurityService securityService;

    @ModelAttribute
    private Writer getWriter() {
        return new Writer();
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String create(@Valid @ModelAttribute Writer writer, BindingResult bindingResult,
                         @RequestParam String name, @RequestParam String password) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        writer.setPassword(passwordEncoder.encode(writer.getPassword()));
        writerRepository.save(writer);
        securityService.autologin(name, password);
        return "redirect:/news";
    }
}
