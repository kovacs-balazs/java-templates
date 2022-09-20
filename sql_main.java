// IMPORTTOK
import me.koba1.economyplots.Main;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// IMPORTTOK
public static SQLMain instance;

private String host;
private String username;
private String password;
private String port;
private String database;

public Connection conn;

public Connection getConnection() {
    return conn;
}

public static SQLMain getInstance() {
    return instance;
}

public void connect() {
    try {
        instance = this;
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://" +
                        host + ":" + port + "/" + database + "?useSSL=false",
                username, password);

    } catch(SQLException | ClassCastException e) {
        e.printStackTrace();
        Bukkit.getLogger().severe("CAN'T CONNECT SQL!");
        Bukkit.getServer().getPluginManager().disablePlugin(Main.getPlugin(Main.class));
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    }
}

public boolean isConnected() {
    return conn != null;
}

private Main m = Main.getPlugin(Main.class);
public void setupSQL() {
    host = m.getConfig().getString("host");
    username = m.getConfig().getString("username");
    password = m.getConfig().getString("password");
    port = m.getConfig().getString("port");
    database = m.getConfig().getString("database");
}
