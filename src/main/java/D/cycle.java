package D;

import arc.util.Log;
import mindustry.Vars;
import mindustry.content.Bullets;
import mindustry.content.Fx;
import mindustry.entities.type.Bullet;
import mindustry.entities.type.Player;
import mindustry.game.Team;
import mindustry.gen.Call;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import static mindustry.Vars.netServer;

public class cycle extends Thread {
    private Thread MainT;
    private JSONObject data = new JSONObject();
    private JSONObject list = new JSONObject();

    public cycle(Thread main) {
        MainT = main;
    }

    public void run() {
        Log.info("async started - Waiting 60 Seconds");
        Main.cycle = Thread.currentThread();
        try {
            TimeUnit.MINUTES.sleep(1);
        } catch (Exception ignored) {
        }
        Log.info("cycle running");
        //Var
        int seconds = 0;
        int nex15Seconds = 0;
        int nexMinute = 0;
        //
        while (MainT.isAlive()) {
            try {
                TimeUnit.SECONDS.sleep(1);
                seconds++;
            } catch (Exception ignored) {
            }
            //run
            //
            if (seconds >= nex15Seconds) {
                nex15Seconds = seconds + 15;
                //run
            }
            if (seconds >= nexMinute) {
                nexMinute = seconds + 60;
                //run
                byteCode.updateStatus(Main.api);
            }
        }
    }
}
