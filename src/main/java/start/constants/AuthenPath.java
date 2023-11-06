package start.constants;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Component
public class AuthenPath {
    public ArrayList<String> listPathAuthen;

    public AuthenPath(){
        listPathAuthen = new ArrayList<>();
        listPathAuthen.add("/admin-only");
        listPathAuthen.add("/api/v1/service");
    }

    public boolean isAuthen(HttpServletRequest request){
        System.out.println(request.getRequestURI());
        if (
//                request.getMethod().equals("GET")
                request.getRequestURI().equals("/login")
                || request.getMethod().equals("GET") && !request.getRequestURI().equals("/api/v1/service")
                && (!request.getRequestURI().contains("/api/v1/order")  && (!request.getRequestURI().equals("/check-role")) && (!request.getRequestURI().contains("/api/v1/customer")) && (!request.getRequestURI().contains("/information-customer")) && (!request.getRequestURI().contains("/api/v1/service") &&(!request.getRequestURI().contains("/revenue")) && (!request.getRequestURI().contains("/number-of-account-in-one-month"))&& (!request.getRequestURI().contains("/registered"))))
                || request.getRequestURI().equals("/signup"))
            return false;
        return true;
    }

}
