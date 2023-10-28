package start.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import start.entity.Option;
import start.enums.ServiceStatusEnum;
import start.enums.TitleEnum;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceAndOptionDTO {
    private String name;
    private String description;
    private TitleEnum title;
    private String figure;
    List<Option> options;
}
