package study.mvc;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MyController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    //@GetMapping("/hello")
    public void hello(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setHeader("content-Type", "text/plain; charset=utf-8");
        response.setHeader("content-Length", "5");
        response.getWriter().write("Hello");
    }

    @GetMapping("/echo")
    public void echo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String method = request.getMethod();
        System.out.println("Method : " + method);

        String uri = request.getRequestURI();
        System.out.println("URI : " + uri);

        String queryString = request.getQueryString();
        System.out.println("query String : " + queryString);

//        HashMap<String, String> map = new HashMap<>();
//        String[] parts = queryString.split("&");
//        for (String p : parts){
//            String[] pair = p.split("=");
//            map.put(pair[0],pair[1]);
//        }
//        for (Map.Entry<String, String>entry : map.entrySet()){
//            System.out.println(entry.getKey()+" "+entry.getValue());
//        }

        String protocol = request.getProtocol();
        System.out.println(protocol);

//        Enumeration<String> headerName = request.getHeaderNames();
//        while (headerName != null && headerName.hasMoreElements()){
//            String h = headerName.nextElement();
//            System.out.println(h+":"+request.getHeader(h));
//        }

//        byte[] bytes = request.getInputStream().readAllBytes();
//        String bytesToString = new String(bytes, StandardCharsets.UTF_8);
//        System.out.println(bytesToString);
//
//        response.setHeader("Content-Type", "text/plain; charset=utf=8");
//        response.getWriter().write(bytesToString);
    }

        @GetMapping("/hello-html")
        public void helloHTML(HttpServletResponse response) throws IOException {
            response.setStatus(200);
            // 만약 해당 내용을 text/plain으로 바꾸면?
            response.setHeader("Content-Type", "text/html; charset=utf-8");
            response.getWriter().write("<h1>Hello</h1>");
        }
    @GetMapping("/hello-xml")
    public void helloXML(HttpServletResponse response) throws IOException {
        // 상태 코드와 관련된 상수를 제공하므로 이용해도 무방함
        response.setStatus(HttpStatus.CREATED.value());
        // "text/xml"이 아님을 유의
        response.setHeader("Content-Type", "application/xml; charset=utf-8");
        response.getWriter().write("<text>Hello</text>");
    }

    @GetMapping("/hello-json")
    public void helloJSON(HttpServletResponse response) throws IOException {
        // 성공적으로 리소스를 찾아서 돌려주면서 404 코드를 돌려줘도, 스프링 쪽에서는 속사정을 알 방법이 없으니 허용하고 잘 동작함
        // (리소스가 존재하지 않는 이유를 json 같은걸로 설명할 수도 있으므로, HTTP 스펙 상에서도 204와는 달리, 404 코드를 돌려줄 때
        // 바디 데이터를 포함하지 않아야 된다고 명시하지 않았음. 단, 웹 브라우저에서는 404이므로 문제라고 인식함, 그렇다고 해도 4XX
        // 에러에 대한 처리는 프로그래머가 해야 함)
        response.setStatus(404);
        // "text/json"이 아님을 유의
        response.setHeader("Content-Type", "application/json");
        response.getWriter().write("{ \"data\": \"Hello\" }");
    }
    @GetMapping("/echo-repeat")
    public void echoRepeat(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setHeader("Content-Type", "text/plain");
        // X-Repeat-Count에 적힌 숫자 정보 가져오고 없으면 1로 초기화
        int loopCount = Integer.parseInt(request.getHeader("X-Repeat-Count") == null ? "1" : request.getHeader("X-Repeat-Count"));
        // 쿼리 스트링 정보 가져와서
        String query = request.getQueryString();
        // 쿼리 스트링 나누고
        String[] querySplit = query.split("&");
        String result = "";

        // 각 쿼리 스트링 정보들을 X-Repeat-Count만큼 반복해서 보여주기
        for(String s : querySplit) {
            for(int i=0;i<loopCount;i++) {
                String[] tmp = s.split("=");
                result += tmp[0] + "," + tmp[1] + "\n";
            }
        }

        response.getWriter().write(result.trim());
    }
    @GetMapping("/dog-image")
    public void dogImage(HttpServletResponse response) throws IOException {
        // resources 폴더의 static 폴더에 이미지 있어야 함
        File file = ResourceUtils.getFile("classpath:static/dog.jpg");
        // 파일의 바이트 데이터 모두 읽어오기
        byte[] bytes = Files.readAllBytes(file.toPath());

        response.setStatus(200);
        // 응답 메시지의 데이터가 JPEG 압축 이미지임을 설정
        response.setHeader("Content-Type", "image/jpeg");
        // 바이트 데이터 쓰기 (여기서는 텍스트 데이터를 전송하지 않기 떄문에 Writer 대신 OutputStream을 이용)
        response.getOutputStream().write(bytes);
    }
    @GetMapping("/dog-image-file")
    public void dogImageFile(HttpServletResponse response) throws IOException {
        File file = ResourceUtils.getFile("classpath:static/dog.jpg");
        byte[] bytes = Files.readAllBytes(file.toPath());

        response.setStatus(200);
        // 임의의 바이너리 데이터임을 알려주는 MIME 타입 설정
        response.setHeader("Content-Type", "application/octet-stream");
        // 다운로드 될 파일 이름 설정
        String filename = "dog.jpg";
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        response.getOutputStream().write(bytes);
    }
    private ArrayList<String> wordList = new ArrayList<>();
    // 위의 ArrayList에 단어를 추가하는 메서드
    @PostMapping("/words")
    public void addWord(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String bodyString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        String[] words = bodyString.split("\n");
        for(String w : words) wordList.add(w.trim());

        // 201 CREATED 응답 코드 발생시키기
        response.setStatus(201);
        // 생성된 리소스를 확인할 수 있는 URL 알려주기 (Location 헤더 굳이 안 붙여도 기능적으로 차이는 없음)
        response.setHeader("Location", "/words");
    }

    // 저장된 모든 단어 보여주기
    @GetMapping("/words")
    public void showWords(HttpServletResponse response) throws IOException {
        String allWords = String.join(",", wordList);

        response.setStatus(200);
        response.getWriter().write(allWords);
    }
    @GetMapping("/users/{username}/products/{productId}")
    public void getProducts(
            @PathVariable(value = "username", required = true) String username,
            @PathVariable("productId") Integer productId,
            @RequestParam(value = "show_comments", required = false, defaultValue = "true") Boolean showComments,
            @RequestHeader("API-Token") String apiToken,
            HttpServletResponse response) throws IOException {

        System.out.println(username);
        System.out.println(productId);
        System.out.println(showComments);
        System.out.println(apiToken);

        if(!apiToken.equals("secret")) {
            response.setStatus(401);
            response.getWriter().write("need valid api key");
        } else {
            response.setStatus(200);
            response.getWriter().write("success");
        }
    }
}
