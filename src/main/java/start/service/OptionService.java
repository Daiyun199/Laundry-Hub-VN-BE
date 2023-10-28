package start.service;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import start.dto.request.OptionDTO;
import start.entity.Option;
import start.exception.exceptions.BadRequest;
import start.repository.OptionRepository;
import start.repository.ServiceRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class OptionService {
    private final OptionRepository optionRepository;
    private final ServiceRepository serviceRepository;

    public final List<Option> getAllOption(long ServiceId ){
        start.entity.Service service = serviceRepository.findById(ServiceId).orElseThrow(() -> new BadRequest("Can't find this service"));
        return optionRepository.findOptionByServiceId(ServiceId);
    }

//    public Option addOption(Option option,long ServiceId){
//        start.entity.Service service = serviceRepository.findById(ServiceId).orElseThrow(() -> new BadRequest("Can't find this service"));
//        option.setService(service);
//        return optionRepository.save(option);
//    }

    public void deleteOption(long OptionId){
        start.entity.Service service = serviceRepository.findServiceByOptionsId(OptionId);
        Option option = optionRepository.findById(OptionId).orElseThrow(() -> new BadRequest("Can't find this option"));
        option.setService(service);
        optionRepository.save(option);
    }
    public Option updateOption(long OptionId, OptionDTO request){
        start.entity.Service service = serviceRepository.findServiceByOptionsId(OptionId);
        Option option = optionRepository.findById(OptionId).orElseThrow(() -> new BadRequest("Can't find this option"));
        option.setName(request.getName());
        option.setPrice(request.getPrice());
        option.setService(service);
        optionRepository.save(option);
        return option;
    }
}
