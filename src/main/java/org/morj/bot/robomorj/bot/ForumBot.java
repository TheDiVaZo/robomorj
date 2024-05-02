package org.morj.bot.robomorj.bot;

import me.cadox8.xenapi.XenAPI;
import me.cadox8.xenapi.exceptions.ArgsErrorException;
import me.cadox8.xenapi.reply.AbstractReply;
import me.cadox8.xenapi.reply.LoginReply;
import me.cadox8.xenapi.request.Request;
import me.cadox8.xenapi.request.RequestBuilder;
import me.cadox8.xenapi.request.RequestParam;
import me.cadox8.xenapi.request.RequestType;

import java.util.function.Consumer;

/**
 * @author TheDiVaZo
 * created on 02.05.2024
 */
public class ForumBot {
    private final XenAPI api;
    private volatile String hash = null;

    public ForumBot(String apiKey, String linkToForum, String authUser, String authPass) {
        this.api = new XenAPI(apiKey, "http://localhost/forum");
    }



    public void auth(String authUser, String authPass ) {
        Request request = RequestBuilder.newRequest(RequestType.LOGIN)
                .addParam(RequestParam.AUTH_USER, authUser)
                .addParam(RequestParam.AUTH_PASS, authPass)
                .createRequest();

        this.<LoginReply>reply(request, reply->this.hash = reply.getHash());
    }

    public void createPost(String message, long threadId) {
        Request request = RequestBuilder.newRequest(RequestType.CREATE_POST)
                .addParam(RequestParam.CREATE_POST_MESSAGE, message)
                .addParam(RequestParam.CREATE_POST_THREAD_ID, threadId)
                .createRequest();

        reply(request, (reply) -> {});
    }

    protected <T extends AbstractReply> void reply(Request request, Consumer<T> consumer) {
        api.<T>getReply(request, (failCause, result) ->{
            try {
                consumer.accept(result);
                result.checkError();
                if (failCause != null) failCause.printStackTrace();
            } catch (ArgsErrorException e) {
                e.printStackTrace();
            }
        });
    }

}
