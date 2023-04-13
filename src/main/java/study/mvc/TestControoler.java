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

    @GetMapping(value = "/reverse", produces = MediaType.TEXT_PLAIN_VALUE)
    public String reverse (@RequestParam(value = "words", required = true) String words){
        String result = "";

        String[] strs = words.split(",");
        for (int i = strs.length - 1; i >= 0; i--) {
            result += strs[i] + ",";
        }
        return result;
    }
    @GetMapping (value="/mul", produces = MediaType.TEXT_PLAIN_VALUE)
    public String mul(
            @RequestParam("num1") Integer num1,
            @RequestParam("num2") Integer num2
    ){
        return  "" + (num1 * num2);
    }
    @GetMapping(value = "/calc/{op}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String calc(
            @PathVariable("op") String op,
            @RequestParam("num1") Integer num1,
            @RequestParam("num2") Integer num2
    ) throws Exception {
            switch (op){
                case "add" : return "" + (num1+num2);
                case "sub" : return "" + (num1-num2);
                case "mul" : return "" + (num1*num2);
                default : throw new Exception("잘못된 연산자");
        }
    }
    int count = 0;
    @PostMapping("/count")
    public String count(){
        return "" + (++count);
    }
}

