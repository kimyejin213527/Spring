package study.mvc;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/test")

public class TestControoler {
    @GetMapping(value = "/hello", produces = MediaType.TEXT_PLAIN_VALUE)
    public String hello() {
        return "Hello,World";
    }
//    @RequestParam(value = "re", required = false, defaultValue = "true") Boolean showComments
}
