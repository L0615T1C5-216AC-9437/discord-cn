package D;

import arc.util.Log;
import mindustry.Vars;
import mindustry.net.Administration;
import org.javacord.api.DiscordApi;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

public class byteCode {
    //code
    public static String dec(String string) { //discord escape codes
        return string.replace("@", "\\@").replace("*","\\*").replace("~","\\~").replace("`","\\`").replace("|","\\|").replace("_","\\_");
    }
    public static String make(String fileName, JSONObject object) {
        try {
            String userHomePath = System.getProperty("user.home");
            File file = new File(userHomePath+"/mind_db/"+fileName+".cn");
            File path = new File(userHomePath+"/mind_db/");
            if (!path.isDirectory()) {
                Log.err("404 - could not find directory "+userHomePath+"/mind_db/");
                return null;
            }
            if (!file.exists()) file.createNewFile();
            FileWriter out = new FileWriter(file, false);
            PrintWriter pw = new PrintWriter(out);
            pw.println(object.toString(4));
            out.close();
            return "Done";
        } catch (IOException i) {
            i.printStackTrace();
            return "error: \n```"+i.getMessage().toString()+"\n```";
        }
    }
    public static boolean makeDefault() {
        make("database", new JSONObject());
        putStr("database", "discordBotToken"+ Administration.Config.port.num(), "<Insert_discordBotToken_Here>");
        putStr("database", "prefix"+Administration.Config.port.num(), "//");
        putStr("database", "botChannel_id"+Administration.Config.port.num(), "<Insert_botChannel_id_here>");
        putStr("database", "botChannel_admin_id"+Administration.Config.port.num(), "<Insert_botChannel_admin_id_here>");
        putStr("database", "adminRole_id", "<Insert_adminRole_id_here>");

        return true;
    }
    public static boolean mkdir(String dirName) {
        String userHomePath = System.getProperty("user.home");
        File path = new File(userHomePath+"/"+dirName);
        if (!path.isDirectory()) {
            if (path.mkdir()) return true;
            return false;
        }
        return true;
    }
    public static JSONObject get(String fileName) {
        try {
            String userHomePath = System.getProperty("user.home");
            File file = new File(userHomePath+"/mind_db/"+fileName+".cn");
            File path = new File(userHomePath+"/mind_db/");
            if (!path.isDirectory()) {
                Log.err("404 - could not find directory "+userHomePath+"/mind_db/");
                return null;
            }
            if (!file.exists()) {
                Log.err("404 - "+userHomePath+"/mind_db/"+fileName+".cn"+" not found");
                return null;
            }
            FileReader fr = new FileReader(file);
            StringBuilder builder = new StringBuilder();
            int i;
            while((i=fr.read())!=-1) {
                builder.append((char)i);
            }
            //return null;
            return new JSONObject(new JSONTokener(builder.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String putJObject(String fileName, String key, JSONObject object) {
        try {
            JSONObject data = get(fileName);
            if (data == null) return null;
            data.put(key, object);

            return save(fileName, data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String putInt(String fileName, String key, float valueNumber) {
        try {
            JSONObject data = get(fileName);
            if (data == null) return null;
            data.put(key, valueNumber);

            return save(fileName, data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String putStr(String fileName, String key, String value) {
        try {
            JSONObject data = get(fileName);
            if (data == null) return null;
            if (!value.equals("")) {
                data.put(key, value);
            } else {
                return "Error - value == \"\" and valueNumber == 0";
            }

            return save(fileName, data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String remove(String fileName, String key) {
        try {
            JSONObject data = get(fileName);
            if (data == null) return null;
            data.remove(key);

            return save(fileName, data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String save(String fileName, JSONObject object) {
        String userHomePath = System.getProperty("user.home");
        File file = new File(userHomePath+"/mind_db/"+fileName+".cn");
        File path = new File(userHomePath+"/mind_db/");
        if (!path.isDirectory()) {
            Log.err("404 - could not find directory "+userHomePath+"/mind_db/");
            return null;
        }
        if (!file.exists()) {
            Log.err("404 - "+userHomePath+"/mind_db/"+fileName+".cn"+" not found");
            return null;
        }
        try {
            FileWriter out = new FileWriter(file, false);
            PrintWriter pw = new PrintWriter(out);
            pw.println(object.toString(4));
            out.close();
            return "Done";
        } catch (IOException it) {
            it.printStackTrace();
            return "error: \n```"+it.getMessage().toString()+"\n```";
        }
    }
    public static Boolean has(String fileName) {
        String userHomePath = System.getProperty("user.home");
        File file = new File(userHomePath+"/mind_db/"+fileName+".cn");
        File path = new File(userHomePath+"/mind_db/");
        if (!path.isDirectory()) {
            Log.err("404 - could not find directory "+userHomePath+"/mind_db/");
            return false;
        }
        if (file.exists()) {
            return true;
        }
        return false;
    }
    public static Boolean hasDir(String dirName) {
        String userHomePath = System.getProperty("user.home");
        File path = new File(userHomePath+"/"+dirName+"/");
        if (path.isDirectory()) return true;
        return false;
    }
    public static void assertCore() {
        if (!hasDir("mind_db")) mkdir("mind_db");
        if (!has("database")) if (make("database", new JSONObject()) != null)  Log.info("Generated new database.cn");
        JSONObject adata = get("database");
        if (!adata.has("discordBotToken"+ Administration.Config.port.num())) {
            putStr("database", "discordBotToken"+ Administration.Config.port.num(), "<Insert_discordBotToken_Here>");
            Log.warn("Setup discord token by doing `putstr database discordBotToken"+Administration.Config.port.num()+" <insert_discordBotToken_here>`\n");
        }
        if (!adata.has("prefix"+Administration.Config.port.num())) {
            putStr("database", "prefix"+Administration.Config.port.num(), "//");
            Log.warn("Setup discord prefix by doing `putstr database prefix"+Administration.Config.port.num()+" <insert_prefix"+Administration.Config.port.num()+"_here>`\n");
        }
        if (!adata.has("botChannel_id")) {
            putStr("database", "botChannel_id", "<Insert_botChannel_id_here>");
            Log.warn("Setup discord botChannel_id by doing `putstr database botChannel_id <insert_botChannel_id_here>`\n");
        }
        if (!adata.has("botChannel_admin_id")) {
            putStr("database", "botChannel_admin_id", "<Insert_botChannel_admin_id_here>");
            Log.warn("Setup discord botChannel_admin_id by doing `putstr database botChannel_admin_id <insert_botChannel_admin_id_here>`\n");
        }
        if (!adata.has("adminRole_id")) {
            putStr("database", "adminRole_id", "<Insert_adminRole_id_here>");
            Log.warn("Setup discord adminRole_id by doing `putstr database adminRole_id <insert_adminRole_id_here>`\n");
        }
    }
    public static void updateStatus(DiscordApi api) {
        if (Vars.playerGroup.size() == 0) {
            api.updateActivity("with no one ;-;");
        } else if (Vars.playerGroup.size() == 1) {
            api.updateActivity("with 1 player.");
        } else {
            api.updateActivity("with "+Vars.playerGroup.size()+" players.");
        }
    }
}