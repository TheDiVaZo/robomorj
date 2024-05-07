package org.morj.bot.robomorj.database.engine;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.TableUtils;
import lombok.Setter;
import org.jooq.lambda.fi.util.function.CheckedConsumer;
import org.jooq.lambda.fi.util.function.CheckedFunction;
import org.morj.bot.robomorj.database.engine.interfaces.IAsyncAutoCloseableDao;
import org.morj.bot.robomorj.exception.SneakyThrower;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

/**
 * @author TheDiVaZo
 * created on 08.05.2024
 * //decompile code
 */
public class AsyncAutoCloseableDao<T, ID> implements IAsyncAutoCloseableDao<T, ID> {
    protected final Class<T> tableClass;
    @Setter
    protected IAsyncAutoCloseableDao.DataSourceSupplier dataSourceSupplier;
    protected final DatabaseType databaseType;

    public AsyncAutoCloseableDao(DatabaseType databaseType, IAsyncAutoCloseableDao.DataSourceSupplier dataSourceSupplier, Class<T> tableClass) {
        this.dataSourceSupplier = dataSourceSupplier;
        this.tableClass = tableClass;
        this.databaseType = databaseType;
    }

    public CompletableFuture<Integer> createTable() {
        return CompletableFuture.supplyAsync(() -> this.applyAutocloseableConnection(connectionSource -> {
            DatabaseTableConfig<T> config = DatabaseTableConfig.fromClass(this.databaseType, this.tableClass);
            return tableExists(connectionSource, config.getTableName()) ? 0 : TableUtils.createTableIfNotExists(connectionSource, config);
        }));
    }

    public CompletableFuture<Integer> createTable(String charset) {
        return CompletableFuture.supplyAsync(() -> {
            this.createTable().join();
            return this.applyDaoQuery(dao -> {
                String var10001 = dao.getTableName();
                return dao.executeRawNoArgs("ALTER TABLE " + var10001 + " CONVERT TO CHARACTER SET " + charset + ";");
            }).join();
        });
    }

    public CompletableFuture<Dao<T, ID>> createDao() {
        return CompletableFuture.supplyAsync(() -> SneakyThrower.sneaky(() -> {
            DatabaseTableConfig<T> config = DatabaseTableConfig.fromClass(this.databaseType, this.tableClass);
            return DaoManager.createDao(this.getConnection(), config);
        }));
    }

    public CompletableFuture<Void> runDaoQuery(CheckedConsumer<Dao<T, ID>> consumer) {
        return this.createDao().thenAccept(dao -> {
            try {
                CheckedConsumer.sneaky(consumer).accept(dao);
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                try {
                    dao.getConnectionSource().close();
                } catch (Exception exceptionClosed) {
                    exceptionClosed.printStackTrace();
                }

            }

        });
    }

    public <R> CompletableFuture<R> applyDaoQuery(CheckedFunction<Dao<T, ID>, R> function) {
        return this.createDao().thenApply((dao) -> {
            R value = null;

            try {
                value = CheckedFunction.sneaky(function).apply(dao);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            } finally {
                try {
                    dao.getConnectionSource().close();
                } catch (Exception exceptionConnectionSource) {
                    exceptionConnectionSource.printStackTrace();
                }

            }
            return value;

        });
    }

    public void runAutocloseableConnection(CheckedConsumer<ConnectionSource> consumer) {
        this.applyAutocloseableConnection((connection) -> {
            consumer.accept(connection);
            return null;
        });
    }

    public <R> R applyAutocloseableConnection(CheckedFunction<ConnectionSource, R> function) {
        Throwable exception = null;
        ConnectionSource connectionSource = null;
        R result = null;

        try {
            connectionSource = this.getConnection();
            result = function.apply(connectionSource);
        } catch (Throwable var13) {
            exception = var13;
        } finally {
            try {
                if (connectionSource != null) {
                    connectionSource.close();
                }
            } catch (Exception var14) {
                throw new RuntimeException(var14);
            }

        }

        if (result != null) {
            return result;
        } else {
            throw new RuntimeException(exception);
        }
    }

    protected static boolean tableExists(ConnectionSource connectionSource, String tableName) throws SQLException {
        DatabaseType databaseType = connectionSource.getDatabaseType();
        DatabaseConnection connection = connectionSource.getReadOnlyConnection(tableName);

        boolean var5;
        try {
            String sqlTable = databaseType.isEntityNamesMustBeUpCase() ? tableName.toUpperCase() : tableName;
            String catalog = connection.getUnderlyingConnection().getCatalog();
            ResultSet rs = connection.getUnderlyingConnection().getMetaData().getTables(catalog, (String)null, sqlTable, new String[]{"TABLE"});

            boolean var7;
            try {
                var7 = rs.next();
            } catch (Throwable var15) {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (Throwable var14) {
                        var15.addSuppressed(var14);
                    }
                }

                throw var15;
            }

            if (rs != null) {
                rs.close();
            }

            return var7;
        } catch (SQLException var16) {
            var16.printStackTrace();
            var5 = false;
        } finally {
            connectionSource.releaseConnection(connection);
        }

        return var5;
    }

    protected ConnectionSource getConnection() {
        return SneakyThrower.sneaky(() -> new DataSourceConnectionSource(this.dataSourceSupplier.createDataSource(), this.databaseType));
    }
}
