package newsportal.controller;

import newsportal.repository.ImageFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;

@Controller
public class ImageFileController {

    @Autowired
    ImageFileRepository imageFileRepository;


    @GetMapping(path = "images/{id}", produces = {"image/jpg", "image/png"})
    @ResponseBody
    @Transactional
    public byte[] get(@PathVariable Long id) {
        return imageFileRepository.findById(id).get().getContent();
    }
}
