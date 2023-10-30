package start.api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;
import start.service.StoreService;
import start.utils.ResponseHandler;

@RequiredArgsConstructor
@RestController
@SecurityRequirement(name = "api") //
@RequestMapping("api/v1/store")
public class StoreController {
    @Autowired
    private StoreService storeService;
    @Autowired
    ResponseHandler responseHandler;

    @GetMapping
    public ResponseEntity getAllStore(){
        return  responseHandler.response(200,"These are all store we having", storeService.getAllStore());
    }

    @PutMapping
    public ResponseEntity updateStatus(){
        return responseHandler.response(200,"Your store active now ", storeService.updateStatus());

    }
}
