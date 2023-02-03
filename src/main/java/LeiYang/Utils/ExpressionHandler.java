package LeiYang.Utils;


import javax.servlet.http.HttpServletResponse;

public class ExpressionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    ExceptionMessage HandlerException(Exception e, HttpServletResponse response) {
        return new ExceptionMessage(400, e.getMessage());
    }
}
