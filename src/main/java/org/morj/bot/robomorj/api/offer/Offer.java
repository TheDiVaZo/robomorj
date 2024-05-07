package org.morj.bot.robomorj.api.offer;

/**
 * @author TheDiVaZo
 * created on 07.05.2024
 */
public interface Offer {
    long getId();
    OfferAuthor getAuthor();
    String getText();
    OfferStatus getOfferStatus();
}
