package tiktzuki.e_store.DAL;

import tiktzuki.e_store.DTO.ShippingNote;
import tiktzuki.e_store.infrastructure.repository.RepositoryImpl;

public class ShippingNoteRepository extends RepositoryImpl<ShippingNote, Integer> {
    public ShippingNoteRepository() {
        super(ShippingNote.class, Integer.class);
    }

}
