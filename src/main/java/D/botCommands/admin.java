package D.botCommands;

import D.byteCode;

import arc.util.Log;
import mindustry.net.Administration;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.json.JSONObject;

import java.awt.*;
import java.util.Optional;

public class admin implements MessageCreateListener {
    private JSONObject adata;
    private DiscordApi api;

    public admin(DiscordApi api) {
        this.api = api;
    }
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String[] message = event.getMessageContent().split(" ", 1);
        String command = message[0];

        byteCode.assertCore();
        adata = byteCode.get("database");
        if (adata != null && adata.has("prefix" + Administration.Config.port.num()) && adata.has("botChannel_admin_id") && command.startsWith(adata.getString("prefix" + Administration.Config.port.num()))) {
            if (event.getChannel().getIdAsString().equals(adata.getString("botChannel_admin_id"))) {
                command = command.replaceFirst(adata.getString("prefix" + Administration.Config.port.num()), "");
                switch (command) {
                    default:
                        event.getChannel().sendMessage(byteCode.dec(command)+" is not a command!");
                        break;
                }
            }
        }
    }

    public Boolean hasRole(String roleID, MessageCreateEvent event){
        Optional<Role> r1 = api.getRoleById(roleID);
        if (!r1.isPresent()) {
            Log.err("Role not found!");
            return false;
        }
        Role r =  r1.get();
        try {
            if (r == null) return false;
            return event.getMessageAuthor().asUser().get().getRoles(event.getServer().get()).contains(r);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
