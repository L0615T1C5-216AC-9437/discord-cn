### Description
AFKick plugin.  
Kicks players that are afk.  
This is a modified/updated version of https://github.com/J-VdS/DiscordPlugin
### Downloading a Jar
1) go to [releases](https://github.com/L0615T1C5-216AC-9437/afk-cn/releases) and download latest version, (not recommended to use pre-releases)
2) follow [#Installing](https://github.com/L0615T1C5-216AC-9437/afk0-cn/blob/master/README.md#installing)

### Building a Jar

1) download src.
2) run gradlew.bat
3) go to the plugin folder in cmd. (example: `cd C:\user\one\desk\pluginfolder\`)
4) type `gradlew jar` and execute.
5) done, look for plugin.jar in pluginfolder\build\libs\

Note: Highly recommended to use Java 8.

### Installing

Simply place the output jar from the step above in your server's `config/mods` directory and restart the server.  
List your currently installed plugins/mods by running the `mods` command.

### Configuration and Info

Files are stored in your system. (`C:\users\user\mind_db\async.cn / /home/user/mind_db/async.cn`)  
This is so the async list and settings are applied to all servers, *if* hosted on the same machine/instance/account.  

To edit settings, use the server-commands provided.  
[core-cn](https://github.com/L0615T1C5-216AC-9437/core-cn) is recommended in order view and edit settings.  

Read: Read file by doing `get afk`. This will show all setings and their value.  
Write: Write int/string by doing `putstr`/`putint` `afk <key> <value>`.  
Default: use server command `afk-clear` to reset settings.

### Contact
Discord: L0615T1C4L.N16HTM4R3#6238  
Discord-Server: [join](http://cn-discord.ddns.net )
