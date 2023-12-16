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

public class CatalogController {
    private DeviceRepository deviceRepos = new DeviceRepository();
    private DeviceStatusRepository deviceStatusRepos = new DeviceStatusRepository();
    private static DeviceTypeRepository deviceTypeRepos = new DeviceTypeRepository();
    private BrandRepository brandRepos = new BrandRepository();

    // Set up combo
    public static List<Combo> combos = Arrays.asList(
            new Combo("combo1", "Combo CPU + RAM", 15000, "ram-cpu.png"),
            new Combo("combo2", "Combo CPU + MAIN + RAM", 500000, "main-cpu-ram.png"),
            new Combo("combo3", "Combo Chuột + Bàn phím", 50000, "mouse-keyboar.jpg")
    );

    static {
        combos.get(0).add(deviceTypeRepos.findById(5).get());
        combos.get(0).add(deviceTypeRepos.findById(2).get());
        combos.get(1).add(deviceTypeRepos.findById(5).get());
        combos.get(1).add(deviceTypeRepos.findById(4).get());
        combos.get(1).add(deviceTypeRepos.findById(2).get());
        combos.get(2).add(deviceTypeRepos.findById(8).get());
        combos.get(2).add(deviceTypeRepos.findById(7).get());
    }

    public static List<Combo> filteredCombo = new ArrayList<Combo>();

    public List<Combo> getAllCombo() {
        return this.combos;
    }

    public List<Combo> removeAllFilteredCombo() {
        filteredCombo = new ArrayList<Combo>();
        return new ArrayList<Combo>();
    }

    public List<Device> getAll() {
        List<Device> devices = new ArrayList<Device>();
        devices = deviceRepos.findAll();
        return devices;
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

    public List<Combo> searchCombo(String deviceName, int deviceTypeId, int brandId, int deviceStatusId, int minPrice, int maxPrice) {
        List<Combo> combos = CatalogController.combos;
        filteredCombo = new ArrayList<Combo>();
        combos.forEach(combo -> {
            if (
                    (!deviceName.equals("") ? combo.getComboId().toLowerCase().contains(deviceName.toLowerCase()) : true) &&
                            (!deviceName.equals("") ? combo.getComboName().toLowerCase().contains(deviceName.toLowerCase()) : true) &&
                            ((minPrice >= 0 ? (combo.getDiscountValue() >= minPrice) : true) &&
                                    (maxPrice >= 0 ? (combo.getDiscountValue() <= maxPrice) : true))
            ) {
                filteredCombo.add(combo);
                System.out.println(
                        ((minPrice >= 0 ? (combo.getDiscountValue() >= minPrice) : true) &&
                                (maxPrice >= 0 ? (combo.getDiscountValue() <= maxPrice) : true))
                );
            } else {
                System.out.println(combo.getComboId() + "wrong");
            }
        });
        return filteredCombo;
    }

}
