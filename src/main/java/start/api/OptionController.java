package start.api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import start.dto.request.OptionDTO;
import start.entity.Option;
import start.service.OptionService;
import start.utils.ResponseHandler;

@RequiredArgsConstructor
@RestController
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

    @PostMapping("{ServiceId}")
    public ResponseEntity addNewOption( Option option, @PathVariable("ServiceId") long serviceId){
        return responseHandler.response(200,"Add Option Successfully",optionService.addOption(option,serviceId));
    }

    @DeleteMapping("{OptionId}/{ServiceId}")
    public ResponseEntity deleteOption(@PathVariable("OptionId") long optionId, @PathVariable("ServiceId") long ServiceId){
        optionService.deleteOption(optionId,ServiceId);
        return responseHandler.response(200,"Delete Successfully",null);
    }

    @PutMapping("{OptionId}/{ServiceId}")
    public ResponseEntity updateOption(@PathVariable("OptionId") long optionId, @PathVariable("ServiceId") long ServiceId, OptionDTO option){
        return responseHandler.response(200,"Update Successfully",optionService.updateOption(optionId,ServiceId,option));
    }



}
