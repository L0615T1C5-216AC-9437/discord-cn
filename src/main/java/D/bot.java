package D;

import D.botCommands.admin;
import D.botCommands.user;

import arc.util.Log;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.json.JSONObject;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class bot extends Thread{
    private DiscordApi api;
    private Thread mainT;

    public bot(Thread mainThread, DiscordApi api) {
        this.mainT = mainThread;
        this.api = api;

        api.addMessageCreateListener(new user(api));
        api.addMessageCreateListener(new admin(api));
    }

    public void run() {
        Main.bot = Thread.currentThread();
        Log.info("bot Thread started -> Wait 60s");
        try {
            TimeUnit.MINUTES.sleep(1);
        } catch (Exception ignored) {
        }
        while (mainT.isAlive()) {
            try {
                TimeUnit.MINUTES.sleep(1);
            } catch (Exception ignored) {
            }
            //run
        }
        //server is down
        byteCode.assertCore();
        JSONObject adata = byteCode.get("database");
        TextChannel tc = getTextChannel(adata.getString("botChannel_admin_id"));
        if (tc != null) new MessageBuilder().append("@everyone Server is down!").send(tc);
        api.disconnect();
        Log.info("disconnected");
    }
    public TextChannel getTextChannel(String id){
        Optional<Channel> dc =  ((Optional<Channel>)this.api.getChannelById(id));
        if (!dc.isPresent()) {
            Log.err("Channel id `"+id+"` not found!");
            return null;
        }
        Optional<TextChannel> dtc = dc.get().asTextChannel();
        if (!dtc.isPresent()){
            Log.err("Text Channel id `"+id+"` not found!");
            return null;
        }
        return dtc.get();
    }
}
