package study.mvc;

public class MyCommandObject {
    private String value1;
    private String value2;

    // 반드시 세터 메서드가 있어야 함

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }
}
