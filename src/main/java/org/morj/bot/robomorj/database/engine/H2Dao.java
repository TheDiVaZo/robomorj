package org.morj.bot.robomorj.database.engine;

import com.j256.ormlite.jdbc.db.H2DatabaseType;
import org.morj.bot.robomorj.database.engine.datasource.DefaultJDBCSource;
import org.morj.bot.robomorj.database.engine.interfaces.IAsyncAutoCloseableDao;

import java.io.File;

/**
 * @author TheDiVaZo
 * created on 08.05.2024
 */
public class H2Dao<T, ID> extends AsyncAutoCloseableDao<T, ID> {
    public H2Dao(IAsyncAutoCloseableDao.DataSourceSupplier supplier, Class<T> tableClass) {
        super(new H2DatabaseType(), supplier, tableClass);
    }

    public static <T, ID> H2Dao<T, ID> file(File file, Class<T> tableClass) {
        String var10000 = file.getAbsolutePath();
        String url = "jdbc:h2:" + var10000.replace(".mv.db", "");
        DefaultJDBCSource defaultJDBCSource = new DefaultJDBCSource(url);
        return new H2Dao<>(() -> defaultJDBCSource, tableClass);
    }

    private static IAsyncAutoCloseableDao.DataSourceSupplier file(File file) {
        String var10000 = file.getAbsolutePath();
        String url = "jdbc:h2:" + var10000.replace(".mv.db", "");
        DefaultJDBCSource defaultJDBCSource = new DefaultJDBCSource(url);
        return () -> defaultJDBCSource;
    }
}
