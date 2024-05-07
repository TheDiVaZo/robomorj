package org.morj.bot.robomorj.database.ticket;

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
    @DatabaseField(canBeNull = false, foreign = true)
    TicketTable ticketTable;
    @DatabaseField(id = true)
    long id;
    @DatabaseField
    long authorId;
    @DatabaseField
    String message;
}
