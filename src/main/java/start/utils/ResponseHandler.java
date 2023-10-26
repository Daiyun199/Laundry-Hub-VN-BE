package start.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import start.dto.response.ResponseDTO;
import start.exception.exceptions.NotAllowException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ResponseHandler<T> {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    public ResponseEntity<ResponseDTO<T>> response(int statusCode, String message, T data){
        return ResponseEntity.ok(new ResponseDTO<T>(statusCode, message, data));
    }

    public void responseResolver(HttpServletRequest request, HttpServletResponse response, Exception exception){
        resolver.resolveException(request, response, null, exception);
    }

}
