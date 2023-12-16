package tiktzuki.e_store.GUI.frames.catalog;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lombok.Data;
import tiktzuki.e_store.BUS.CatalogController;
import tiktzuki.e_store.DTO.Brand;
import tiktzuki.e_store.DTO.Combo;
import tiktzuki.e_store.DTO.Device;
import tiktzuki.e_store.DTO.DeviceStatus;
import tiktzuki.e_store.DTO.DeviceType;
import tiktzuki.e_store.GUI.Resources;
import tiktzuki.e_store.GUI.customcomponents.TButton;
import tiktzuki.e_store.GUI.customcomponents.TComboBox;
import tiktzuki.e_store.GUI.customcomponents.TComboBoxItem;
import tiktzuki.e_store.GUI.customcomponents.TFormControl;
import tiktzuki.e_store.GUI.frames.catalog.CatalogMainPanel.Action;

@Data
public class CatalogSearchPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    JLabel lblTitle;

    TFormControl<JTextField> txtName;

    TFormControl<JTextField> txtMinPrice;

    TFormControl<JTextField> txtMaxPrice;

    TFormControl<TComboBox<DeviceType>> cbxDeviceType;

    TFormControl<TComboBox<Brand>> cbxBrand;

    TFormControl<TComboBox<DeviceStatus>> cbxStatus;

    List<Device> filteredDevice;
    List<Combo> filteredCombo;
    TButton btnSearch;
    JCheckBox ckbIncludeCombo;
    JPanel pnlBtn;

    CatalogController catalogController = new CatalogController();

    public CatalogSearchPanel() {
        initData();
        initComp();
    }

    public void initData() {

        lblTitle = new JLabel("TÌM KIẾM");
        txtName = new TFormControl<JTextField>(new JTextField(), "Tên thiết bị");
        txtMinPrice = new TFormControl<JTextField>(new JTextField(), "Giá (min)", TFormControl.WIDTH_S);
        txtMaxPrice = new TFormControl<JTextField>(new JTextField(), "Giá (max)", TFormControl.WIDTH_S);
        cbxDeviceType = new TFormControl<TComboBox<DeviceType>>(new TComboBox<TComboBoxItem<DeviceType>>(), "Loại thiết bị");
        cbxBrand = new TFormControl<TComboBox<Brand>>(new TComboBox<TComboBoxItem<Brand>>(), "Hãng");
        cbxStatus = new TFormControl<TComboBox<DeviceStatus>>(new TComboBox<TComboBoxItem<DeviceStatus>>(), "Trạng thái");

        ckbIncludeCombo = new JCheckBox("Bao gồm combo", true);
        btnSearch = new TButton("Tìm");
        pnlBtn = new JPanel();
    }


    public void initComp() {
        cbxBrand.getComponent().addItem(new TComboBoxItem<Brand>(new Brand(-1, -1, "---"), "---"));
        catalogController.getAllBrand().forEach(brand -> {
            cbxBrand.getComponent().addItem(new TComboBoxItem<Brand>(brand, brand.getName()));
        });


        cbxDeviceType.getComponent().addItem(new TComboBoxItem<DeviceType>(new DeviceType(-1, "---", -1), "---"));
        catalogController.getAllDeviceType().forEach(type -> {
            cbxDeviceType.getComponent().addItem(new TComboBoxItem<DeviceType>(type, type.getName()));
        });

        cbxStatus.getComponent().addItem(new TComboBoxItem<DeviceStatus>(new DeviceStatus(-1, "---"), "---"));
        catalogController.getAllDeviceStatus().forEach(status -> {
            cbxStatus.getComponent().addItem(new TComboBoxItem<DeviceStatus>(status, status.getName()));
        });

        lblTitle.setFont(Resources.F_H3Regular);
        btnSearch.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                filteredDevice = catalogController.search(
                        txtName.getComponent().getText(),
                        cbxDeviceType.getComponent().getSelectedObject().getId(),
                        cbxBrand.getComponent().getSelectedObject().getId(),
                        cbxStatus.getComponent().getSelectedObject().getId(),
                        !txtMinPrice.getComponent().getText().equals("") ? Integer.valueOf(txtMinPrice.getComponent().getText()) : -1,
                        !txtMaxPrice.getComponent().getText().equals("") ? Integer.valueOf(txtMaxPrice.getComponent().getText()) : -1
                );
                if (ckbIncludeCombo.isSelected()) {
                    filteredCombo = catalogController.searchCombo(
                            txtName.getComponent().getText(),
                            cbxDeviceType.getComponent().getSelectedObject().getId(),
                            cbxBrand.getComponent().getSelectedObject().getId(),
                            cbxStatus.getComponent().getSelectedObject().getId(),
                            !txtMinPrice.getComponent().getText().equals("") ? Integer.valueOf(txtMinPrice.getComponent().getText()) : -1,
                            !txtMaxPrice.getComponent().getText().equals("") ? Integer.valueOf(txtMaxPrice.getComponent().getText()) : -1
                    );
                } else {
                    filteredCombo = catalogController.removeAllFilteredCombo();
                }
                CatalogMainPanel.dispatch(Action.SEARCH);
            }
        });

        pnlBtn.setPreferredSize(new Dimension(120, 60));
        pnlBtn.add(ckbIncludeCombo);
        pnlBtn.add(btnSearch);
        pnlBtn.setBackground(Resources.C_LIGHT);
        this.add(txtName.getPnl());
        this.add(txtMinPrice.getPnl());
        this.add(txtMaxPrice.getPnl());
        this.add(cbxDeviceType.getPnl());
        this.add(cbxBrand.getPnl());
        this.add(cbxStatus.getPnl());
        this.add(pnlBtn);
        this.setBackground(Resources.C_LIGHT);
    }

}
