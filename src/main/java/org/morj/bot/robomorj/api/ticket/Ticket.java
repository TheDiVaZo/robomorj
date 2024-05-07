package org.morj.bot.robomorj.api.ticket;

import java.util.List;

/**
 * @author TheDiVaZo
 * created on 07.05.2024
 */
public interface Ticket {
    long getId();
    boolean isPin();
    boolean isClose();
    List<TicketMessage> getHistory();
}
