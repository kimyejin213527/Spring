package study.mvc;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

class SuccessResult{
    public String result = "success";
}
class Result{
    public String result;
    public Integer code;
}


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
    @GetMapping(value = "/result_test", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResult resultTest(){
        return new SuccessResult();
    }
    @GetMapping(value = "/result_test2",produces = MediaType.APPLICATION_JSON_VALUE)
    public Result resultTest2(){
        Result result = new Result();
        result.result = "success";
        result.code = 1000;

        return result;
    }
    @GetMapping(value = "/result_test3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> resultTest3(){
        Map<String, Object> result = new HashMap<>();
        result.put("result","success");
        result.put("code",1000);

        return result;
    }

//    현재 날짜 시간 출력하기
    @GetMapping(value = "/now", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> now(){
        Map<String,Object> result = new HashMap<>();
        LocalDateTime dt = LocalDateTime.now();
        result.put("date", dt.getYear() + "-"+dt.getMonthValue() + "-" + dt.getDayOfMonth());
        result.put("time", dt.getHour()+":"+dt.getMinute()+":"+dt.getSecond());

        return result;
    }

}

