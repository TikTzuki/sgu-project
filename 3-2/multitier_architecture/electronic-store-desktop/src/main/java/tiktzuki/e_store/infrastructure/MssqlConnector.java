package tiktzuki.e_store.infrastructure;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.mysql.cj.x.protobuf.MysqlxCrud.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tiktzuki.e_store.ProjectProperties;
import tiktzuki.e_store.DTO.OrderDetail;
import tiktzuki.e_store.infrastructure.repository.Col;
import tiktzuki.e_store.infrastructure.repository.Repository;
import tiktzuki.e_store.infrastructure.repository.RepositoryImpl;

@AllArgsConstructor
@Data
public class MssqlConnector implements Connector {
    private static Logger logger = Logger.getLogger(MssqlConnector.class.getName());
    private String host;
    private String username;
    private String password;
    private String database;
    protected Statement statement;
    protected ResultSet resultSet;
    static protected Connection connection;

    public MssqlConnector() {
        this.host = ProjectProperties.getProperties("mssql.host");
        this.username = ProjectProperties.getProperties("mssql.user");
        this.password = ProjectProperties.getProperties("mssql.password");
        this.database = ProjectProperties.getProperties("mssql.database");
    }

    @Override
    public void getConnect() {
        String url = "jdbc:sqlserver://" + this.host + ";databaseName=" + this.database;
        try {
            connection = DriverManager.getConnection(url, this.username, this.password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setStatement() {
        try {
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet executeQuery(String query) {
        ResultSet rs = this.resultSet;
        try {
            this.getConnect();
            this.setStatement();
            rs = this.statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public int executeUpdate(String query) {
        int res = Integer.MIN_VALUE;
        try {
            this.getConnect();
            this.setStatement();
            res = this.statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeConnection();
        }
        return res;
    }

    @Override
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }

            if (this.statement != null) {
                this.statement.close();
            }

            if (this.resultSet != null) {
                this.resultSet.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Connector con = new MssqlConnector();
        con.getConnect();
        if (con != null) {
            System.out.print("Connected!");
        }
//		Connector connector = new MssqlConnector();
//		connector.getConnect();
//		ResultSet rs = connector.executeQuery("SELECT * FROM tbl_ChiTietHoaDon");
//
//	    List<Field> fields = Arrays.asList(OrderDetail.class.getDeclaredFields());
//	    for(Field field: fields) {
//	    	System.out.println(field);
//	        field.setAccessible(true);
//	    }
//
//	    List<OrderDetail> list = new ArrayList<>(); 
//	    try {
//	    	while(rs.next()) {
//	    		OrderDetail dto = OrderDetail.class.getConstructor().newInstance();
//
//	    		for(Field field: fields) {
//	    			Col col = field.getAnnotation(Col.class);
//	    			if(col!=null) {
//	    				String name = col.name();
//	    				try{
//	    					String value = rs.getString(name);
//	    					field.set(dto, field.getType().getConstructor(String.class).newInstance(value));
//	    				} catch (Exception e) {
//	    					e.printStackTrace();
//	    				}
//	    			}
//	    		}
//	    		list.add(dto);
//	    	}
//	    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
//	    		| NoSuchMethodException | SecurityException | SQLException e) {
//	    	e.printStackTrace();
//	    }
//	    list.forEach(System.out::println);
    }
}
