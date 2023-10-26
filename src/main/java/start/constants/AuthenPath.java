package start.constants;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AuthenPath {
    public ArrayList<String> listPathAuthen;

    public AuthenPath(){
        listPathAuthen = new ArrayList<>();
        listPathAuthen.add("/admin-only");
    }

    public boolean isAuthen(String path){
        return listPathAuthen.contains(path);
    }

}
