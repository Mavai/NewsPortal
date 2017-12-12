package newsportal.controller;

import newsportal.model.Writer;
import newsportal.repository.WriterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;

@Controller
public class DefaultController {

    @Autowired
    private WriterRepository writerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Profile("default")
    @PostConstruct
    public void init() {
        if (writerRepository.findByName("user") != null) {
            return;
        }
        Writer writer = new Writer();
        writer.setName("user");
        writer.setPassword(passwordEncoder.encode("password"));
        writerRepository.save(writer);
    }

    @RequestMapping("/")
    public String index() {
        return "redirect:/news/page/1";
    }
}
