package tiktzuki.e_store.infrastructure.repository;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse.File;

import tiktzuki.e_store.DAL.BrandRepository;
import tiktzuki.e_store.DAL.OrderDetailRepository;
import tiktzuki.e_store.DTO.Brand;
import tiktzuki.e_store.DTO.OrderDetail;
import tiktzuki.e_store.infrastructure.Connector;
import tiktzuki.e_store.infrastructure.MssqlConnector;
import tiktzuki.e_store.services.DateFormat;

public class RepositoryImpl<T, ID> implements Repository<T, ID> {
    private final Class<T> type;
    private final Class<ID> idType;
    private Connector connector;
    private Table table;
    private Field idCol;
    private List<Field> coupleId = new ArrayList<Field>();
    private List<Field> fields = new ArrayList<Field>();
    Logger LOGGER = Logger.getLogger(this.getClass().getSimpleName());

    public RepositoryImpl(Class<T> type, Class<ID> id) {
        this.type = type;
        this.idType = id;
        this.table = this.type.getDeclaredAnnotation(Table.class);
        connector = new MssqlConnector();

        this.fields = Stream.of(type.getDeclaredFields()).filter(t -> t.getDeclaredAnnotation(Col.class) != null).collect(Collectors.toList());
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Id.class)) {
                this.idCol = field;
            }
            if (field.isAnnotationPresent(CoupleId.class)) {
                coupleId.add(field);
            }
        }
    }


    @Override
    public Optional<T> findById(ID id) {
        StringBuilder query = new StringBuilder("SELECT * FROM " + table.value() + " WHERE " + this.idCol.getAnnotation(Col.class).value() + "='" + id + "';");
        LOGGER.info(query.toString());
        ResultSet rs = connector.executeQuery(query.toString());
        return Optional.ofNullable(extract(rs).get(0));
    }

    @Override
    public List<T> findAll() {
        String query = "SELECT * FROM " + table.value();
        LOGGER.info(query.toString());
        ResultSet rs = connector.executeQuery(query);
        return extract(rs);
    }

    @Override
    public List<T> findAllById(Iterable<? extends ID> ids) {
        StringBuilder query = new StringBuilder("SELECT * FROM " + table.value() + " WHERE ");
        for (ID id : ids) {
            query.append(" " + this.idCol.getAnnotation(Col.class).value() + " = '" + id + "' OR");
        }
        query = new StringBuilder(query.substring(0, query.lastIndexOf("OR")));
        LOGGER.info(query.toString());
        ResultSet rs = connector.executeQuery(query.toString());
        return extract(rs);
    }

    @Override
    public List<T> extract(ResultSet rs) {
        List<T> items = new ArrayList<T>();
        try {
            while (rs.next()) {
                T dto = type.getConstructor().newInstance();
                for (Field field : fields) {
                    Col col = field.getDeclaredAnnotation(Col.class);
                    if (col == null)
                        continue;
                    String name = col.value();
                    String value = rs.getString(name);
                    if (value == null)
                        continue;
                    if (field.getType() == Date.class) {
                        SimpleDateFormat spf = new DateFormat();
                        Date d = spf.parse(value);
                        field.set(dto, d);
                    } else {
                        field.set(dto, value != null ? field.getType().getConstructor(String.class).newInstance(value) : null);
                    }
                }
                items.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public int insert(T entity) {
        StringBuilder query = new StringBuilder("INSERT INTO " + this.table.value() + "(");
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Id.class)) {
                Col col = field.getDeclaredAnnotation(Col.class);
                if (col != null) {
                    query.append(col.value() + ", ");
                }
            }
        }
        query = new StringBuilder(query.substring(0, query.lastIndexOf(",")));
        query.append(") VALUES (");
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Id.class)) {
                try {
                    query.append(" N'" + field.get(entity) + "',");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        query = new StringBuilder(query.substring(0, query.lastIndexOf(",")) + ")");
        LOGGER.info(query.toString());
        return connector.executeUpdate(query.toString());
    }

    @Override
    public int insertAll(Iterable<T> entities) {
        StringBuilder query = new StringBuilder("INSERT INTO " + this.table.value() + "(");
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Id.class)) {
                Col col = field.getDeclaredAnnotation(Col.class);
                if (col != null) {
                    query.append(col.value() + ", ");
                }
            }
        }
        query = new StringBuilder(query.substring(0, query.lastIndexOf(",")));
        query.append(") VALUES ");
        for (T entity : entities) {
            query.append("(");
            for (Field field : fields) {
                if (!field.isAnnotationPresent(Id.class)) {
                    try {
                        query.append(" N'" + field.get(entity) + "',");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            query = new StringBuilder(query.substring(0, query.lastIndexOf(",")) + "),");
        }
        query = new StringBuilder(query.substring(0, query.lastIndexOf(",")));
        this.LOGGER.info(query.toString());
        return connector.executeUpdate(query.toString());
    }

    @Override
    public int update(T entity) {
        StringBuilder query = new StringBuilder("");
        try {
            String prepend = "UPDATE " + this.table.value() + " SET ";
            StringBuilder payload = new StringBuilder();
            StringBuilder append = new StringBuilder(" WHERE ");
            for (Field field : fields) {
                Col col = field.getDeclaredAnnotation(Col.class);
                if (col == null)
                    continue;
                if (!field.isAnnotationPresent(CoupleId.class) && !field.isAnnotationPresent(Id.class)) {
                    payload.append(col.value() + " = N'" + field.get(entity) + "', ");
                } else {
                    append.append(col.value() + " = N'" + field.get(entity) + "' AND ");
                }
            }
            query.append(
                    prepend
                            + payload.substring(0, payload.lastIndexOf(","))
                            + append.substring(0, append.lastIndexOf("AND"))
                            + ";");
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        LOGGER.info(query.toString());
        return connector.executeUpdate(query.toString());
    }

    @Override
    public int updateAll(Iterable<T> entities) {
        StringBuilder query = new StringBuilder("");
        try {
            for (T entity : entities) {
                String prepend = "UPDATE " + this.table.value() + " SET ";
                StringBuilder payload = new StringBuilder();
                StringBuilder append = new StringBuilder(" WHERE ");
                for (Field field : fields) {
                    Col col = field.getDeclaredAnnotation(Col.class);
                    if (col == null)
                        continue;
                    if (!field.isAnnotationPresent(CoupleId.class) && !field.isAnnotationPresent(Id.class)) {
                        payload.append(col.value() + " = N'" + field.get(entity) + "', ");
                    } else {
                        append.append(col.value() + " = N'" + field.get(entity) + "' AND ");
                    }
                }
                query.append(
                        prepend
                                + payload.substring(0, payload.lastIndexOf(","))
                                + append.substring(0, append.lastIndexOf("AND"))
                                + "; \n");
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        LOGGER.info(query.toString());
        return connector.executeUpdate(query.toString());
    }

    @Override
    public void delete(T entity) {
        StringBuilder query = new StringBuilder("DELETE FROM " + this.table.value() + " WHERE ");
        try {
            if (idCol != null) {
                query.append(idCol.getAnnotation(Col.class).value() + " = '" + idCol.get(entity) + "' OR ");
            } else {
                for (Field idField : coupleId) {
                    query.append(idField.getAnnotation(Col.class).value() + " = '" + idField.get(entity) + "' AND ");
                }
                query = new StringBuilder(query.substring(0, query.lastIndexOf("AND")) + " OR ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        query = new StringBuilder(query.substring(0, query.lastIndexOf("OR")));
        LOGGER.info(query.toString());
        connector.executeUpdate(query.toString());
    }

    @Override
    public void deleteById(ID id) {
        StringBuilder query = new StringBuilder("DELETE FROM " + table.value() + " WHERE " + idCol.getAnnotation(Col.class).value() + " = '" + id + "' ");
        connector.executeUpdate(query.toString());
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        StringBuilder query = new StringBuilder("DELETE FROM " + this.table.value() + " WHERE ");
        for (ID id : ids) {
            query.append(idCol.getAnnotation(Col.class).value() + " = '" + id + "' OR ");
        }
        query = new StringBuilder(query.substring(0, query.lastIndexOf("OR")));
        LOGGER.info(query.toString());
        connector.executeUpdate(query.toString());
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        StringBuilder query = new StringBuilder("DELETE FROM " + this.table.value() + " WHERE ");
        try {
            for (T entity : entities) {
                if (idCol != null) {
                    query.append(idCol.getAnnotation(Col.class).value() + " = '" + idCol.get(entity) + "' OR ");
                } else {
                    for (Field idField : coupleId) {
                        query.append(idField.getAnnotation(Col.class).value() + " = '" + idField.get(entity) + "' AND ");
                    }
                    query = new StringBuilder(query.substring(0, query.lastIndexOf("AND")) + " OR ");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        query = new StringBuilder(query.substring(0, query.lastIndexOf("OR")));
        LOGGER.info(query.toString());
        connector.executeUpdate(query.toString());
    }

    @Override
    public long count() {
        long count = 0;
        String query = "SELECT COUNT(*) as count FROM " + this.table.value();
        ResultSet rs = connector.executeQuery(query);
        try {
            while (rs.next())
                count = rs.getLong("count");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LOGGER.info(query.toString());
        return count;
    }


    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        // TODO test single ID
        Repository<Brand, Integer> brandRepos = new BrandRepository();
        List<Brand> brands = Arrays.asList(
                new Brand(15, 1, "Truc"),
                new Brand(16, 1, "Nitendo")
        );
        brandRepos
                .findAllById(Arrays.asList(new Integer[]{1, 2}))
                .forEach(System.out::println);
//    	brandRepos.updateAll(brands);
//    	brandRepos.update(brands.get(0));
//    	brandRepos.deleteAllById(Arrays.asList(new Integer[] {15,16}));
//    	brandRepos.deleteById(15);
//    	brandRepos.deleteAll(brands);
//    	brandRepos.delete(brands.get(0));
//    	brandRepos
//    	.findAll()
//    	.forEach(System.out::println);

        // TODO test couple ID
        Repository<OrderDetail, Integer> repos = new OrderDetailRepository();
        List<OrderDetail> orderDetails = Stream.of(
                new OrderDetail(1, 1, 1, 1, 1),
                new OrderDetail(2, 3, 4, 6, 9)
        ).collect(Collectors.toList());
        repos
                .findAll()
                .forEach(System.out::println);
//    	repos.deleteAll(orderDetails);
//    	repos.delete(orderDetails.get(0));

    }
}
