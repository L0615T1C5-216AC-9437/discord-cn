package D;

import arc.Events;
import arc.util.CommandHandler;
import arc.util.Log;
import arc.util.Strings;
import mindustry.entities.type.Player;
import mindustry.game.EventType;
import mindustry.net.Administration;
import mindustry.plugin.Plugin;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.json.JSONObject;

public class Main extends Plugin {
    //Var
    public static JSONObject adata = new JSONObject();
    public static Thread cycle;
    public static Thread bot;
    public static DiscordApi api = null;
    Boolean enabled = false;

    ///Var
    //on start
    public Main() {
        Events.on(EventType.ServerLoadEvent.class, event -> {
            byteCode.assertCore();
            adata = byteCode.get("database");
            if (adata == null) {
                Log.err("Invalid file - " + System.getProperty("user.home") + "/mind_db/async.cn");
                Log.info("Reset file using command `database-clear`");
                return;
            }
            if (adata.has("discordBotToken"+Administration.Config.port.num())) {
                try {
                    api = new DiscordApiBuilder().setToken(adata.getString("discordBotToken"+Administration.Config.port.num())).login().join();
                } catch (Exception e) {
                    if (e.getMessage().contains("READY packet")){
                        Log.err("\n\n[ERR!] discordplugin: invalid token.\n");
                    } else {
                        e.printStackTrace();
                    }
                }
            } else {
                Log.err("Please configure database.cn -> discordBotToken"+Administration.Config.port.num());
                return;
            }
            if (api != null) {
                cycle a = new cycle(Thread.currentThread());
                a.setDaemon(false);
                a.start();

                bot b = new bot(Thread.currentThread(), api);
                b.setDaemon(false);
                b.start();
            }
        });
        Events.on(EventType.WaveEvent.class, event -> {
            if (api != null) {
                if (!cycle.isAlive()) {
                    cycle a = new cycle(Thread.currentThread());
                    a.setDaemon(false);
                    a.start();
                }
                if (!bot.isAlive()) {
                    bot b = new bot(Thread.currentThread(), api);
                    b.setDaemon(false);
                    b.start();
                }
            }
        });

    }

    public void registerServerCommands(CommandHandler handler) {
        handler.register("database-clear", "generates the default async.cn file", arg -> {
            if (byteCode.makeDefault())  Log.info("Generated new database.cn");
        });
    }
    public void registerClientCommands(CommandHandler handler) {

    }

}