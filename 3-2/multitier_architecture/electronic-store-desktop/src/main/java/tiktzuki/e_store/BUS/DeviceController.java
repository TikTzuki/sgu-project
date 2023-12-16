package tiktzuki.e_store.BUS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tiktzuki.e_store.DAL.BrandRepository;
import tiktzuki.e_store.DAL.DeviceRepository;
import tiktzuki.e_store.DAL.DeviceStatusRepository;
import tiktzuki.e_store.DAL.DeviceTypeRepository;
import tiktzuki.e_store.DTO.Brand;
import tiktzuki.e_store.DTO.Combo;
import tiktzuki.e_store.DTO.Device;
import tiktzuki.e_store.DTO.DeviceStatus;
import tiktzuki.e_store.DTO.DeviceType;

public class DeviceController {

    DeviceRepository deviceRepos;
    DeviceStatusRepository deviceStatusRepos;
    DeviceTypeRepository deviceTypeRepos;
    BrandRepository brandRepos;

    public List<Device> getAll() {
        List<Device> devices = new ArrayList<Device>();
        devices = deviceRepos.findAll();
        return devices;
    }

    public void modify(Device device) {
        deviceRepos.update(device);
    }

    public List<DeviceStatus> getAllDeviceStatus() {
        return deviceStatusRepos.findAll();
    }

    public List<DeviceType> getAllDeviceType() {
        return deviceTypeRepos.findAll();
    }

    public List<Brand> getAllBrand() {
        return brandRepos.findAll();
    }

    public List<Device> search(String deviceName, int deviceTypeId, int brandId, int deviceStatusId, int minPrice, int maxPrice) {
        List<Device> devices = new ArrayList<Device>();
        devices = deviceRepos.findAll();
        devices.removeIf(device -> (
                (!deviceName.equals("") ? !device.getName().toLowerCase().contains(deviceName.toLowerCase()) : false) ||
                        (deviceTypeId > 0 ? !(deviceTypeId == device.getDeviceTypeId()) : false) ||
                        (brandId > 0 ? !(brandId == device.getBrandId()) : false) ||
                        (deviceStatusId > 0 ? !(deviceStatusId == device.getDeviceStatusId()) : false) ||
                        !((minPrice >= 0 ? device.getPrice() >= minPrice : true) &&
                                (maxPrice >= 0 ? device.getPrice() <= maxPrice : true))
        ));
        return devices;
    }

}
