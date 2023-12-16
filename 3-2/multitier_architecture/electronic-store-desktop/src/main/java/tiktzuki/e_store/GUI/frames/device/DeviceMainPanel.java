package tiktzuki.e_store.GUI.frames.device;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import tiktzuki.e_store.DAL.DeviceRepository;
import tiktzuki.e_store.DTO.Device;
import tiktzuki.e_store.GUI.MainFrame;
import tiktzuki.e_store.GUI.Resources;

public class DeviceMainPanel extends JPanel {
    public static JPanel mainContent;
    public static Device selectedDevice;
    public static List<Device> devices;
    private static DeviceRepository deviceRepository = new DeviceRepository();

    public DeviceMainPanel() {
        super(new FlowLayout(0, 0, 0));
        devices = deviceRepository.findAll();
        selectedDevice = new Device();
        mainContent = new JPanel(new FlowLayout(0, 0, 0));
        mainContent.add(new DeviceMainPanel1());
        this.add(mainContent);
        this.setBackground(Resources.C_INFO);
    }

    public static void initAddDevicePanel() {
        selectedDevice = new Device();
        mainContent.removeAll();
        mainContent.add(new AddDevice1());
        mainContent.getParent().revalidate();
        mainContent.repaint();
    }

    public static void initMainDevicePanel() {
        mainContent.removeAll();
        mainContent.add(new DeviceMainPanel1());
        mainContent.getParent().revalidate();
        mainContent.repaint();
    }

    public static void initModifyDevice() {
        devices = deviceRepository.findAll();
        mainContent.removeAll();
        mainContent.add(new AddDevice1(selectedDevice.getId()));
        mainContent.getParent().revalidate();
        mainContent.repaint();
    }

    public static void initDetailDevice() {
        devices = deviceRepository.findAll();
        mainContent.removeAll();
        mainContent.add(new DetailDevicePanel(selectedDevice.getId()));
        mainContent.getParent().revalidate();
        mainContent.repaint();
    }
}
