package start.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderCusDTO {
    private String address;
    private String numberOfCustomer;
    private float numberOfHeightCus;
    List<Long> optionIds;
}
