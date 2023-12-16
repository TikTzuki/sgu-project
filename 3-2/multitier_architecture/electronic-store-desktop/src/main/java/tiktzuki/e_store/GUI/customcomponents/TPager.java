package tiktzuki.e_store.GUI.customcomponents;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import lombok.Data;
import tiktzuki.e_store.BUS.CatalogController;
import tiktzuki.e_store.GUI.Resources;
import tiktzuki.e_store.GUI.frames.catalog.CatalogItem;
import tiktzuki.e_store.GUI.frames.catalog.CatalogMainPanel;
import tiktzuki.e_store.GUI.frames.catalog.CatalogMainPanel.Action;

@Data
public class TPager extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public Integer pageIndex;
    public Integer totalPage;
    public Integer pageLimit;
    public Integer itemCount;
    public List<CatalogItem> allItems;

    private CatalogController controller;

    List<TButton> btnPages = new ArrayList<TButton>();

    public TPager(List<CatalogItem> allItems, int pageLimit) {
        this.allItems = allItems;
        this.pageLimit = pageLimit;
        initComp(allItems);
        this.setPreferredSize(new Dimension(40, 600));
    }

    public void initComp(List<CatalogItem> items) {
        this.pageIndex = 0;
        this.itemCount = items.size();
        this.allItems = items;
        this.totalPage = (int) (Math.ceil(itemCount / (pageLimit * 1.0)));
        this.removeAll();

        TButton btnPrev = new TButton(Resources.DOUBLE_ARROW_UP);
        btnPages.add(btnPrev);
        btnPrev.addMouseListener(new OnClickPage(this, btnPrev));
        this.add(btnPrev);

        TButton btnNext = new TButton(Resources.DOUBLE_ARROW_DOWN);
        btnPages.add(btnNext);
        btnNext.addMouseListener(new OnClickPage(this, btnNext));
        this.add(btnNext);
        for (int i = 0; i < totalPage; i++) {
            if ((i >= pageIndex - 2 && i <= pageIndex + 2)) {
                TButton btn = new TButton(String.format("%d", i));
                btnPages.add(btn);
                if (i == pageIndex) {
                    btn.setBackground(Resources.C_PRIMARY);
                }
                btn.addMouseListener(new OnClickPage(this, btn));
                this.add(btn);
            }
        }

        if (this.getParent() != null) {
            this.getParent().revalidate();
            this.repaint();
        }
    }

    public void reload(int index) {
        this.setPageIndex(index);
        this.removeAll();
        TButton btnPrev = new TButton(Resources.DOUBLE_ARROW_UP);
        btnPages.add(btnPrev);
        btnPrev.addMouseListener(new OnClickPage(this, btnPrev));
        this.add(btnPrev);

        TButton btnNext = new TButton(Resources.DOUBLE_ARROW_DOWN);
        btnPages.add(btnNext);
        btnNext.addMouseListener(new OnClickPage(this, btnNext));
        this.add(btnNext);
        for (int i = 0; i < totalPage; i++) {
            if ((i >= pageIndex - 2 && i <= pageIndex + 2)) {
                TButton btn = new TButton(String.format("%d", i));
                btnPages.add(btn);
                if (i == pageIndex) {
                    btn.setBackground(Resources.C_PRIMARY);
                }
                btn.addMouseListener(new OnClickPage(this, btn));
                this.add(btn);
            }
        }

        if (this.getParent() != null) {
            this.getParent().revalidate();
            this.repaint();
        }
    }

    public List<CatalogItem> getPagedItem() {
        int from = this.pageIndex * this.pageLimit;
        int to = from + this.pageLimit > this.itemCount ? this.itemCount : from + this.pageLimit;
        return this.allItems.subList(from, to);
    }
}

class OnClickPage extends MouseAdapter {
    TButton currentBtn;
    List<TButton> btns;
    TPager pager;

    public OnClickPage(TPager pager, TButton btn) {
        super();
        this.currentBtn = btn;
        this.btns = pager.getBtnPages();
        this.pager = pager;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        int index = 0;
        if (currentBtn.getIcon() == Resources.DOUBLE_ARROW_UP) {
            index = pager.pageIndex - 1;
        } else {
            if (currentBtn.getIcon() == Resources.DOUBLE_ARROW_DOWN) {
                index = pager.pageIndex + 1;
            } else {
                index = Integer.valueOf(currentBtn.getText());
            }
        }
        if (index < 0) {
            index = 0;
        }
        if (index == pager.totalPage) {
            index = pager.totalPage - 1;
        }
        pager.reload(index);
        CatalogMainPanel.dispatch(Action.CHANGE_PAGE);
    }

//	@Override
//	public void mouseClicked(MouseEvent e) {
//		super.mouseClicked(e);
//		int index = 0;
//		if(currentBtn.getIcon() == Resources.DOUBLE_ARROW_UP) {
//			index = pager.pageIndex -1;
//		} else {
//			if(currentBtn.getIcon() == Resources.DOUBLE_ARROW_DOWN) {
//				index = pager.pageIndex + 1;
//			} else {
//				index = Integer.valueOf(currentBtn.getText());
//			}
//		}
//		if(index < 0) {
//			index = 0;
//		}
//		if(index == pager.totalPage) {
//			index = pager.totalPage-1;
//		}
//		pager.reload(index);
//		CatalogMainPanel.dispatch(Action.CHANGE_PAGE);
//	}
}
