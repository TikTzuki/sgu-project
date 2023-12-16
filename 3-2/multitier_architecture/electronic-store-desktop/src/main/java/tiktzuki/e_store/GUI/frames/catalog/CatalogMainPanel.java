package tiktzuki.e_store.GUI.frames.catalog;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import tiktzuki.e_store.BUS.CatalogController;
import tiktzuki.e_store.BUS.OrderCreateController;
import tiktzuki.e_store.DAL.DeviceRepository;
import tiktzuki.e_store.DTO.Combo;
import tiktzuki.e_store.DTO.Device;
import tiktzuki.e_store.GUI.MainFrame;
import tiktzuki.e_store.GUI.Resources;
import tiktzuki.e_store.GUI.customcomponents.TPager;

public class CatalogMainPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    CatalogController controller = new CatalogController();
    static OrderCreateController orderCreateController = new OrderCreateController();

    static List<CatalogItem> filteredCatalogItems;
    static List<CatalogItem> pagedCatalogItem;
    static List<Device> devices;
    static List<Combo> combos;
    static CatalogSearchPanel searchJPanel;
    static CatalogContentPanel catalogPanel;
    static TPager pager;

    public enum Action {
        CHANGE_PAGE,
        SEARCH
    }

    public static void dispatch(Action action) {
        switch (action) {
            case CHANGE_PAGE: {
                pagedCatalogItem = pager.getPagedItem();
                catalogPanel.loadPagedFilteredItem(pagedCatalogItem);
                break;
            }
            case SEARCH: {
                devices = searchJPanel.getFilteredDevice();
                combos = searchJPanel.getFilteredCombo();
                reloadFilteredItems(devices, combos);
                pager.initComp(filteredCatalogItems);
                pagedCatalogItem = pager.getPagedItem();
                catalogPanel.loadPagedFilteredItem(filteredCatalogItems);
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + action);
        }
    }

    public CatalogMainPanel() {
        loadData();
        loadComp();
    }

    private void loadData() {
        filteredCatalogItems = new ArrayList<CatalogItem>();
        devices = controller.getAll();
        combos = controller.getAllCombo();
        searchJPanel = new CatalogSearchPanel();
        reloadFilteredItems(devices, combos);
        pager = new TPager(filteredCatalogItems, 10);
        pagedCatalogItem = pager.getPagedItem();
        catalogPanel = new CatalogContentPanel(pagedCatalogItem);
        combos = controller.getAllCombo();
        reloadFilteredItems(devices, combos);
    }

    private void loadComp() {
        this.setPreferredSize(Resources.MAIN_CONTENT);
        this.add(searchJPanel);
        this.add(catalogPanel);
        this.add(pager);
    }

    private static void reloadFilteredItems(List<Device> filteredDevices, List<Combo> filteredCombos) {
        filteredCatalogItems = new ArrayList<CatalogItem>();
        filteredDevices.forEach(device -> {
            filteredCatalogItems.add(new CatalogItem(device));
        });
        if (filteredCombos != null) {
            filteredCombos.forEach(combo -> {
                filteredCatalogItems.add(new CatalogItem(combo));
            });
        }
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}

