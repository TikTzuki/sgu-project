package tiktzuki.e_store.DTO;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Combo extends ArrayList<DeviceType> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public String comboId;
    public String comboName;
    public Integer discountValue = 0;
    public String image;
}
