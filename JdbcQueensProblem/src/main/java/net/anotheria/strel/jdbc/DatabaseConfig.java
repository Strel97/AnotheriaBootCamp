package net.anotheria.strel.jdbc;

import org.configureme.ConfigurationManager;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * Represents configuration for database connection.
 * @author Strel97
 */
@ConfigureMe( name = "dbconfig" )
public class DatabaseConfig {

    @Configure
    private String dbName;

    @Configure
    private String host;

    @Configure
    private String userName;

    @Configure
    private String password;

    private static final DatabaseConfig INSTANCE = new DatabaseConfig();


    private DatabaseConfig() {
        ConfigurationManager.INSTANCE.configure(this);
    }

    public String getDbName() {
        return dbName;
    }

    public String getHost() {
        return host;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public static DatabaseConfig getInstance() {
        return INSTANCE;
    }
}
