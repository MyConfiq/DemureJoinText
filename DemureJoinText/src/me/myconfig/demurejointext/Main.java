package me.myconfig.demurejointext;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.plugin.java.JavaPlugin;

public class Main
  extends JavaPlugin
{
  public void onEnable()
  {
    Bukkit.getConsoleSender().sendMessage("[DemureJoin] Enabled!");
    Bukkit.getConsoleSender().sendMessage("Coded By MyConfig");
    getConfig().options().copyDefaults(true);
    saveConfig();
    new Joiner(this);
  }
  
  public void onDisable()
  {
    Bukkit.getConsoleSender().sendMessage("[DemureJoin] has been disabled!");
  }
}
