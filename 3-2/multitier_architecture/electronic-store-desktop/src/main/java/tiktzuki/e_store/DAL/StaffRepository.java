package tiktzuki.e_store.DAL;

import tiktzuki.e_store.DTO.Staff;
import tiktzuki.e_store.infrastructure.repository.RepositoryImpl;

public class StaffRepository extends RepositoryImpl<Staff, Integer> {
    public StaffRepository() {
        super(Staff.class, Integer.class);
    }
}
