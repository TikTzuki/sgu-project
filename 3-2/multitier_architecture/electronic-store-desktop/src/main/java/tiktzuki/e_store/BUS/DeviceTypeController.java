package tiktzuki.e_store.BUS;

import java.util.List;

import tiktzuki.e_store.DAL.DeviceTypeRepository;
import tiktzuki.e_store.DTO.DeviceType;

public class DeviceTypeController {
    public static DeviceTypeRepository repos;

    public List<DeviceType> getAll() {
        return repos.findAll();
    }
}
