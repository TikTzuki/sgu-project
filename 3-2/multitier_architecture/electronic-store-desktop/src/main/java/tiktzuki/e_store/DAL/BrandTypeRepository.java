package tiktzuki.e_store.DAL;

import tiktzuki.e_store.DTO.BrandType;
import tiktzuki.e_store.infrastructure.repository.RepositoryImpl;

public class BrandTypeRepository extends RepositoryImpl<BrandType, Integer> {
    public BrandTypeRepository() {
        super(BrandType.class, Integer.class);
    }
}
