import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class discordbotstatus extends ListenerAdapter implements Listener, CommandExecutor {
    public Main plugin;
    public JDA jda;

    public discordbotstatus(Main main) {
        this.plugin = main;
        startBot();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        jda.addEventListener(this);
    }

    private void startBot() {
        try {
            jda = new JDABuilder(AccountType.BOT).setToken("token").build();
            jda.getPresence().setStatus(OnlineStatus.IDLE);
            jda.getPresence().setActivity(Activity.watching("BurnEmpire szerverét"));
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public void dctimer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                boolean statusgetting = plugin.getConfig().getBoolean("Status.BurnEmpire");
                boolean maintenancegetting = plugin.getConfig().getBoolean("Maintenance." + "Maintenance");
                String status;
                String maintenance;
                if(statusgetting == true) {
                    status = "Elérhető";
                } else {
                    status = "Nem elérhető";
                }
                if(maintenancegetting == true) {
                    maintenance = "Bekapcsolva";
                } else {
                    maintenance = "Kikapcsolva";
                }
                ArrayList list = new ArrayList();
                for(Player onlinePlayers : plugin.getServer().getOnlinePlayers()) {
                    list.add(onlinePlayers.getName());
                }
                plugin.getServer().broadcastMessage("teszt");
                String stringlist = Arrays.toString(list.toArray()).replace('[', ' ').replace(']', ' ').replace(',', '\n').trim();
                int playersonline = plugin.getServer().getOnlinePlayers().size();
                MessageChannel channel = jda.getTextChannelById("790739136881360917");
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.ORANGE);
                embed.setTitle("BurnEmpire");
                embed.setDescription("**" + "Szerver információk" + "**"
                        + "\nHa bármilyen problémád akadna olvasd át az 'INFO' kategóriát. Ha még mindig nem kaptál a kérdésedre választ, akkor írj a 'segítség' szobát. "
                        + "**" +  "Jó játékot!" + "**");
                embed.addField("Szerver státusz:", status, true);
                embed.addField("Szerver IP:", "Majd itt lesz", true);
                embed.addField("Játékosok:", playersonline + "/" + plugin.getServer().getMaxPlayers(), true);
                embed.addField("", "", true);
                embed.addField("Weboldalunk:", "burnempire.minemarket.hu", true);
                embed.addField("Karbantartás:", maintenance, true);
                embed.addField("Játékosok:", stringlist, true);
                channel.editMessageById("801861028727095297", embed.build()).queue();
            }
        }.runTaskTimer(plugin, 0, 100L);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("sendembed")) {
            if(sender.isOp()) {
                boolean statusgetting = plugin.getConfig().getBoolean("Status.BurnEmpire");
                String status;
                if(statusgetting == true) {
                    status = "Elérhető";
                } else {
                    status = "Nem elérhető";
                }
                ArrayList list = new ArrayList();
                for(Player onlinePlayers : plugin.getServer().getOnlinePlayers()) {
                    list.add(onlinePlayers.getName());
                }
                MessageChannel channel = jda.getTextChannelById("790739136881360917");
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.ORANGE);
                embed.setTitle("BurnEmpire");
                embed.setDescription("**" + "Szerver információk" + "**"
                        + "\nHa bármilyen problémád akadna olvasd át az 'INFO' kategóriát. Ha még mindig nem kaptál a kérdésedre választ, akkor írj a 'segítség' szobát. "
                        + "**" +  "Jó játékot!" + "**");
                embed.addField("Szerver státusz:", status, true);
                channel.sendMessage(embed.build()).queue();
            }
        }
        return false;
    }
}
