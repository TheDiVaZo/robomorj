package org.morj.bot.robomorj.database.ticket;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author TheDiVaZo
 * created on 07.05.2024
 */
@DatabaseTable(tableName = "tickets")
public class TicketTable {

    @DatabaseField(id = true)
    private long id;
}
