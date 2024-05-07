package org.morj.bot.robomorj.database.ticket;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;

/**
 * @author TheDiVaZo
 * created on 07.05.2024
 */
@Data
@DatabaseTable(tableName = "tickets")
public class TicketTable {
    @DatabaseField(id = true)
    long id;
    @DatabaseField
    boolean isPin;
    @DatabaseField
    boolean isClosed;
    @ForeignCollectionField
    ForeignCollection<TicketMessage> history;
}
