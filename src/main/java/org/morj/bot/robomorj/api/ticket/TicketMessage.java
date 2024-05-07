package org.morj.bot.robomorj.api.ticket;

import java.time.LocalDateTime;

/**
 * @author TheDiVaZo
 * created on 07.05.2024
 */
public interface TicketMessage {
    long getId();
    String getMessage();
    LocalDateTime getLocalDateTime();


}
