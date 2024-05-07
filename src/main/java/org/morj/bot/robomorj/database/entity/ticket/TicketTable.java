package org.morj.bot.robomorj.database.entity.ticket;

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

    public static final String ID_COLUMN_NAME = "id";
    public static final String IS_PIN_COLUMN_NAME = "is_pin";
    public static final String IS_CLOSED_COLUMN_NAME = "is_closed";
    public static final String HISTORY_COLUMN_NAME = "history";

    @DatabaseField(columnName = ID_COLUMN_NAME,id = true)
    long id;
    @DatabaseField(columnName = IS_PIN_COLUMN_NAME)
    boolean isPin;
    @DatabaseField(columnName = IS_CLOSED_COLUMN_NAME)
    boolean isClosed;
    @ForeignCollectionField(columnName = HISTORY_COLUMN_NAME)
    ForeignCollection<TicketMessage> history;
}
