package tiktzuki.e_store.GUI.customcomponents;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import tiktzuki.e_store.BUS.OrderManagerController;
import tiktzuki.e_store.BUS.ShippingNoteController;
import tiktzuki.e_store.GUI.frames.order.SearchOrderPanel;

public class FindOrderDialog {
    OrderManagerController orderController = new OrderManagerController();
    static ShippingNoteController shippingNoteController = new ShippingNoteController();

    public static JDialog dialog = new JDialog();
    JPanel pnlBackground = new JPanel();

    // Search
    SearchOrderPanel pnlSearch = new SearchOrderPanel();

    // Control
    JPanel pnlControl = new JPanel();
    static JLabel lblSelectedItem = new JLabel("Hóa đơn");
    TButton btnSubmit = new TButton("Chọn");

    //Table
    static DefaultTableModel model = new DefaultTableModel(
            new Object[]{"#", "Khách hàng", "Ngày lập", "Địa chỉ", "Số điện thoại", "Ngày nhận", "Người nhận", "Đã thanh toán", "Tổng tiền"}, 0);
    TTable tblOrder = new TTable(model);
    JScrollPane scroller = new JScrollPane(tblOrder);

    public FindOrderDialog() {
        initData();
        initComp();
    }

    private void initData() {
        loadNewOrderTable();
    }

    private void initComp() {

        //Control
        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = tblOrder.getSelectedRow();
                if (row == -1)
                    return;
                Integer id = Integer.valueOf(tblOrder.getValueAt(row, 0).toString());
                shippingNoteController.setSelectedOrder(id);
            }
        });

        pnlControl.add(lblSelectedItem);
        pnlControl.add(btnSubmit);
        // Table
        tblOrder.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = tblOrder.getSelectedRow();
                lblSelectedItem.setText("Hóa đơn: " + tblOrder.getValueAt(row, 0).toString());
            }
        });

        GroupLayout backgroundLayout = new GroupLayout(pnlBackground);
        backgroundLayout.setAutoCreateContainerGaps(true);
        backgroundLayout.setAutoCreateGaps(true);
        backgroundLayout.setHorizontalGroup(backgroundLayout.createParallelGroup()
                .addComponent(pnlSearch, 800, 800, 800)
                .addComponent(pnlControl)
                .addComponent(scroller));
        backgroundLayout.setVerticalGroup(backgroundLayout.createSequentialGroup()
                .addComponent(pnlSearch, 140, 140, 140)
                .addComponent(pnlControl)
                .addComponent(scroller));
        pnlBackground.setLayout(backgroundLayout);
        dialog.setTitle("Chọn hóa đơn cần lập phiếu giao hàng");
        dialog.add(pnlBackground);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(false);
    }

    private static void loadNewOrderTable() {
        model.setRowCount(0);
        OrderManagerController.orders.forEach(order -> {
            System.out.println(order);
            model.addRow(new Object[]{
                    order.getId(),
                    order.getCustomer().getName(),
                    order.getCreateDate(),
                    order.getAddress(),
                    order.getPhone(),
                    order.getReceiveDate(),
                    order.getReceiver(),
                    order.getPaymentStatus(),
                    order.getAmount()});
        });
    }

    public enum Action {
        SWITCH_STATE,
        CONFIRM,
        UPDATE
    }

    public static void dispatch(Action action) {
        switch (action) {
            case SWITCH_STATE: {
                dialog.setVisible(dialog.isVisible() ? false : true);
                break;
            }
            case CONFIRM: {
                shippingNoteController.setSelectedOrder(0);
                break;
            }
            case UPDATE: {
                loadNewOrderTable();
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + action);
        }
    }
//	void goodPairs(Integer[] nums) {
//		LinkedList<Integer[]> pairs = new LinkedList<Integer[]>();
//		for(int i =0; i <nums.length; i++) {
//			for(int j=i+1; j<nums.length; j++) {
//				if(nums[i] == nums[j]) {
//					pairs.add( new Integer[] {i, j});
//				}
//			}
//		}
//		pairs.forEach(pair -> {
//			System.out.print("(" + pair[0] + " , " + pair[1]+")");
//		});
//	}
//
//	void countOccurrences(Integer[] nums) {
//		HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>();
//		for(int i=0; i < nums.length; i++) {
//			if(hash.get(nums[i]) != null) {
//				continue;
//			}
//			int count = 0;
//			for(int j=i; j<nums.length; j++) {
//				if(nums[j] == nums[i]) {
//					count ++;
//				}
//			}
//			hash.put(nums[i], count);
//		}
//		for (Map.Entry<Integer, Integer> entry : hash.entrySet()) {
//			System.out.print(entry.getKey() +":" + entry.getValue() + ", ");
//		}
//	}

}
