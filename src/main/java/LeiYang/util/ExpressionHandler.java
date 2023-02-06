package LeiYang.util;


import javax.servlet.http.HttpServletResponse;

public class ExpressionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    ExceptionMessage HandlerException(Exception e, HttpServletResponse response) {
        return new ExceptionMessage(response.hashCode(), e.getMessage());
    }
}
