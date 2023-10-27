package start.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import start.entity.Option;
import start.entity.OrderDetail;
import start.entity.Service;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
    private String address;
    List<Long> optionIds;
}
