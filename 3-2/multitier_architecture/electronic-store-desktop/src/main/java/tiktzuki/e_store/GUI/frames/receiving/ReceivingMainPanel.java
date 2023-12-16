package tiktzuki.e_store.GUI.frames.receiving;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JPanel;

import tiktzuki.e_store.DAL.ReceivingNoteRepository;
import tiktzuki.e_store.DTO.Device;
import tiktzuki.e_store.DTO.ReceivingNote;
import tiktzuki.e_store.GUI.Resources;
import tiktzuki.e_store.GUI.frames.device.AddDevice1;

public class ReceivingMainPanel extends JPanel {
    public static JPanel mainContent;
    public static ReceivingNote selectedReceiving;
    public static List<ReceivingNote> receivingNotes;
    public static ReceivingNoteRepository receivingNoteRepository = new ReceivingNoteRepository();

    public ReceivingMainPanel() {
        super(new FlowLayout(0, 0, 0));
        receivingNotes = receivingNoteRepository.findAll();
        selectedReceiving = new ReceivingNote();
        mainContent = new JPanel(new FlowLayout(0, 0, 0));
        mainContent.add(new ReceivingManagerPanel());
        this.add(mainContent);
    }

    public static void initAddReceivingPanel() {
        selectedReceiving = new ReceivingNote();
        mainContent.removeAll();
        mainContent.add(new AddReceivingPanel());
        mainContent.getParent().revalidate();
        mainContent.repaint();
    }

    public static void initAddReceivingManagerPanel() {
        selectedReceiving = new ReceivingNote();
        mainContent.removeAll();
        mainContent.add(new ReceivingManagerPanel());
        mainContent.getParent().revalidate();
        mainContent.repaint();
    }

    public static void initModifyReceivingNote() {
        receivingNotes = receivingNoteRepository.findAll();
        mainContent.removeAll();
        mainContent.add(new AddReceivingPanel(selectedReceiving.getId()));
        mainContent.getParent().revalidate();
        mainContent.repaint();
    }

    public static void initDetailReceivingNote() {
        receivingNotes = receivingNoteRepository.findAll();
        mainContent.removeAll();
        mainContent.add(new DetailReceivingPanel(selectedReceiving.getId()));
        mainContent.getParent().revalidate();
        mainContent.repaint();
    }
}
