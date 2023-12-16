package tiktzuki.e_store.DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tiktzuki.e_store.DTO.Brand;
import tiktzuki.e_store.DTO.SpecificationDetail;
import tiktzuki.e_store.infrastructure.Connector;
import tiktzuki.e_store.infrastructure.MssqlConnector;
import tiktzuki.e_store.infrastructure.repository.Repository;
import tiktzuki.e_store.infrastructure.repository.RepositoryImpl;

public class BrandRepository extends RepositoryImpl<Brand, Integer> {
    private Connector connector;

    public BrandRepository() {
        super(Brand.class, Integer.class);
        connector = new MssqlConnector();

    }

    public List<Brand> getAll() {
        List<Brand> brands = new ArrayList<Brand>();
        brands = super.findAll();
        return brands;
    }

    public int findIdByName(String name) {
        String query = "SELECT * FROM tbl_Hang where Ten = N'" + name + "'";
        ResultSet rs = connector.executeQuery(query);
        int id = 0;
        try {
            while (rs.next())
                id = rs.getInt("MaHang");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String findNameById(int id) {
        String query = "SELECT * FROM tbl_Hang where MaHang = N'" + id + "'";
        ResultSet rs = connector.executeQuery(query);
        String name = "";
        try {
            while (rs.next())
                name = rs.getString("Ten");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public List<Brand> findById(int id) {
        String query = "SELECT * FROM tbl_Hang" + " WHERE " + "MaHang = '" + id + "' ";
        ResultSet rs = connector.executeQuery(query);
        return super.extract(rs);
    }

    public static void main(String[] args) {
        Repository<Brand, Integer> brandRepos = new BrandRepository();
        List<Brand> brands = Arrays.asList(
                new Brand(15, 1, "Truc"),
                new Brand(16, 1, "Nitendo")
        );
        brandRepos
                .findAllById(Arrays.asList(new Integer[]{1, 2}))
                .forEach(System.out::println);
        brandRepos.updateAll(brands);
        brandRepos.update(brands.get(0));
        brandRepos.deleteAllById(Arrays.asList(new Integer[]{15, 16}));
        brandRepos.deleteById(15);
        brandRepos.deleteAll(brands);
        brandRepos.delete(brands.get(0));
        brandRepos
                .findAll()
                .forEach(System.out::println);
    }
}
