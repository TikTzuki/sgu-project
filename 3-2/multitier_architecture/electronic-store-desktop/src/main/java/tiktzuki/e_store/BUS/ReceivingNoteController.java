package tiktzuki.e_store.BUS;

import java.util.List;

import tiktzuki.e_store.DAL.ReceivingNoteRepository;
import tiktzuki.e_store.DTO.ReceivingNote;

public class ReceivingNoteController {
    ReceivingNoteRepository receiRepos;

    public List getAll() {
        return receiRepos.findAll();
    }

    public void modify(ReceivingNote note) {
        receiRepos.update(note);
    }


}
