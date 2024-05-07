package org.morj.bot.robomorj.database.entity.ticket;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;

/**
 * @author TheDiVaZo
 * created on 07.05.2024
 */
@Data
@DatabaseTable(tableName = "ticket_messages")
public class TicketMessage {
    public static final String TICKET_COLUMN_NAME = "ticket";
    public static final String ID_COLUMN_NAME = "id";
    public static final String AUTHOR_COLUMN_NAME = "author";
    public static final String MESSAGE_COLUMN_NAME = "message";

    @DatabaseField(columnName = TICKET_COLUMN_NAME,canBeNull = false, foreign = true)
    TicketTable ticketTable;
    @DatabaseField(columnName = ID_COLUMN_NAME,id = true)
    long id;
    @DatabaseField(columnName = AUTHOR_COLUMN_NAME)
    long authorId;
    @DatabaseField(columnName = MESSAGE_COLUMN_NAME)
    String message;
}
