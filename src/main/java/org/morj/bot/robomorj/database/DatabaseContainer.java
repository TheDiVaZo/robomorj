package org.morj.bot.robomorj.database;

import com.j256.ormlite.db.DatabaseType;
import lombok.Getter;
import org.morj.bot.robomorj.database.engine.interfaces.IAsyncAutoCloseableDao;
import org.morj.bot.robomorj.database.entity.ticket.TicketDatabase;
import org.morj.bot.robomorj.util.DatabaseUtil;

/**
 * @author TheDiVaZo
 * created on 08.05.2024
 */
public class DatabaseContainer {
    @Getter
    private TicketDatabase ticketDatabase;

    public DatabaseContainer(DatabaseType databaseType, IAsyncAutoCloseableDao.DataSourceSupplier source) {
        this.ticketDatabase = new TicketDatabase(databaseType, source);
    }
}
