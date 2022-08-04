
private String host = "localhost";
private String port = "3306";
private String database = "sql_template";
private String username = "root";
private String password = "";

private static Connection connection;

public boolean isConnected() {
    return (connection == null ? false : true);
}

public void connect() throws ClassNotFoundException, SQLException {
    if(!isConnected()) {
        connection = DriverManager.getConnection("jdbc:mysql://" +
                        host + ":" + port + "/" + database + "?useSSL=false&autoReconnect=true",
                username, password);
    }
}

public void disconnect() {
    if(isConnected()) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

public Connection getConnection() {
    return connection;
}
