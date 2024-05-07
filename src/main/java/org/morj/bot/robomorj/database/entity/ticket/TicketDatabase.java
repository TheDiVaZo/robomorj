package org.morj.bot.robomorj.database.entity.ticket;

import com.j256.ormlite.db.DatabaseType;
import org.morj.bot.robomorj.database.engine.AsyncAutoCloseableDao;
import org.morj.bot.robomorj.database.engine.interfaces.IAsyncAutoCloseableDao;
import org.morj.bot.robomorj.database.engine.interfaces.IDatabase;

import java.util.concurrent.CompletableFuture;

/**
 * @author TheDiVaZo
 * created on 08.05.2024
 */
public class TicketDatabase extends AsyncAutoCloseableDao<TicketTable, Long> implements IDatabase<TicketTable, Long> {

    public TicketDatabase(DatabaseType databaseType, IAsyncAutoCloseableDao.DataSourceSupplier source) {
        super(databaseType, source, TicketTable.class);
        initialize();
    }

    @Override
    public Long idOf(TicketTable table) {
        return table.getId();
    }

    public CompletableFuture<Boolean> isExist(long id) {
        return getElements(dao -> {
            dao.where().eq(TicketTable.ID_COLUMN_NAME, id);
            return dao;
        }).thenApply(tables -> tables.stream().findAny().isPresent());
    }

    public CompletableFuture<Boolean> deleteElement(long id) {
         return deleteElement(deleteBuilder -> {
            deleteBuilder.setWhere(deleteBuilder.where().eq(TicketTable.ID_COLUMN_NAME, id));
            return deleteBuilder;
        });
    }
}
