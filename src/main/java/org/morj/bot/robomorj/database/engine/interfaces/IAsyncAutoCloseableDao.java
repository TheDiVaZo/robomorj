package org.morj.bot.robomorj.database.engine.interfaces;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import org.jooq.lambda.fi.util.function.CheckedConsumer;
import org.jooq.lambda.fi.util.function.CheckedFunction;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

/**
 * @author TheDiVaZo
 * created on 08.05.2024
 */
public interface IAsyncAutoCloseableDao<T, ID> {
    default CompletableFuture<Void> executeRawQuery(String sql) {
        return this.createDao().thenAcceptAsync((dao) -> {
            try {
                dao.executeRawNoArgs(sql);
            } catch (SQLException var3) {
                throw new RuntimeException(var3);
            }
        });
    }

    CompletableFuture<Integer> createTable();

    CompletableFuture<Dao<T, ID>> createDao();

    CompletableFuture<Void> runDaoQuery(CheckedConsumer<Dao<T, ID>> var1);

    <R> CompletableFuture<R> applyDaoQuery(CheckedFunction<Dao<T, ID>, R> var1);

    void runAutocloseableConnection(CheckedConsumer<ConnectionSource> var1);

    <R> R applyAutocloseableConnection(CheckedFunction<ConnectionSource, R> var1);

    void setDataSourceSupplier(DataSourceSupplier var1);

    public interface DataSourceSupplier {
        DataSource createDataSource() throws Throwable;
    }
}
