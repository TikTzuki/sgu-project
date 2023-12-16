package tiktzuki.e_store.infrastructure;

import java.sql.ResultSet;

public interface Connector {
    void getConnect();

    void setStatement();

    ResultSet executeQuery(String query);

    int executeUpdate(String query);

    void closeConnection();
}
