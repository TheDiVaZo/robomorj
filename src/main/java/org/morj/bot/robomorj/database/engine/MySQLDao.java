package org.morj.bot.robomorj.database.engine;

import com.j256.ormlite.jdbc.db.MysqlDatabaseType;
import org.morj.bot.robomorj.database.engine.datasource.DefaultJDBCSource;
import org.morj.bot.robomorj.database.engine.interfaces.IAsyncAutoCloseableDao;

/**
 * @author TheDiVaZo
 * created on 08.05.2024
 */
public class MySQLDao<T, ID> extends AsyncAutoCloseableDao<T, ID> {
    public MySQLDao(IAsyncAutoCloseableDao.DataSourceSupplier supplier, Class<T> tableClass) {
        super(new MysqlDatabaseType(), supplier, tableClass);
    }

    public static <T, ID> MySQLDao<T, ID> credentials(String url, String username, String password, Class<T> tableClass) {
        DefaultJDBCSource defaultSource = new DefaultJDBCSource("jdbc:mysql://" + url);
        defaultSource.setPassword(password);
        defaultSource.setUsername(username);
        return new MySQLDao<>(() -> defaultSource, tableClass);
    }

    private static IAsyncAutoCloseableDao.DataSourceSupplier credentials(String url, String username, String password) {
        DefaultJDBCSource defaultSource = new DefaultJDBCSource("jdbc:mysql://" + url);
        defaultSource.setPassword(password);
        defaultSource.setUsername(username);
        return () -> defaultSource;
    }
}
