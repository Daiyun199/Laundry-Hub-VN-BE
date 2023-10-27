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
//        System.out.println(request.getMethod());
        if (request.getMethod().equals("GET") || request.getRequestURI().equals("/login") || request.getRequestURI().equals("/signup"))
            return false;
        return true;
    }

}
