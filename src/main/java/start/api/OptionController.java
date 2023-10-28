package start.api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import start.dto.request.OptionDTO;
import start.entity.Option;
import start.service.OptionService;
import start.utils.ResponseHandler;

@RequiredArgsConstructor
@RestController
@SecurityRequirement(name = "api")
@RequestMapping("api/v1/option")
public class OptionController {
    @Autowired
    private OptionService optionService;
    @Autowired
    ResponseHandler responseHandler;


    @GetMapping("{ServiceId}")
    public ResponseEntity getAllOptionInService(@PathVariable("ServiceId") long serviceId){
        return responseHandler.response(200,"Load all Option successfully",optionService.getAllOption(serviceId));
    }

//    @PostMapping("{ServiceId}")
//    @PreAuthorize("hasAuthority('STORE')")
//    public ResponseEntity addNewOption( Option option, @PathVariable("ServiceId") long serviceId){
//        return responseHandler.response(200,"Add Option Successfully",optionService.addOption(option,serviceId));
//    }

    @DeleteMapping("{OptionId}")
    @PreAuthorize("hasAuthority('STORE')")
    public ResponseEntity deleteOption(@PathVariable("OptionId") long optionId){
        optionService.deleteOption(optionId);
        return responseHandler.response(200,"Delete Successfully",null);
    }

    @PutMapping("{OptionId}")
    @PreAuthorize("hasAuthority('STORE')")
    public ResponseEntity updateOption(@PathVariable("OptionId") long optionId, OptionDTO option){
        return responseHandler.response(200,"Update Successfully",optionService.updateOption(optionId,option));
    }



}
