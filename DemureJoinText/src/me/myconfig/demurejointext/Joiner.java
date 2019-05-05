package me.myconfig.demurejointext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;

public class Joiner
  implements Listener
{
  int a;
  int b;
  int c;
  private Main plugin;
  String joinmessage;
  String leavemessage;
  String title;
  String subtitle;
  Sound joinsound;
  boolean joins;
  
  public Joiner(Main plugin)
  {
    this.plugin = plugin;
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
    this.joinmessage = plugin.getConfig().getString("NormalMessages.Join.Message")
      .replace("&", "§");
    this.leavemessage = plugin.getConfig().getString("NormalMessages.Leave.Message")
      .replace("&", "§");
    this.a = plugin.getConfig().getInt("JoinTitle.Seconds.FadeIn");
    this.b = plugin.getConfig().getInt("JoinTitle.Seconds.Stay");
    this.c = plugin.getConfig().getInt("JoinTitle.Seconds.FadeOut");
    this.title = plugin.getConfig().getString("JoinTitle.Title")
      .replace("&", "§");
    this.subtitle = plugin.getConfig().getString("JoinTitle.Subtitle")
      .replace("&", "§");
    this.joinsound = Sound.valueOf(plugin.getConfig().getString("NormalMessages.Join.Sound"));
    this.joins = plugin.getConfig().getBoolean("NormalMessages.Join.enabledSound");
  }
  
  @Deprecated
  public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message)
  {
    sendTitle(player, fadeIn, stay, fadeOut, message, null);
  }
  
  @Deprecated
  public static void sendSubtitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message)
  {
    sendTitle(player, fadeIn, stay, fadeOut, null, message);
  }
  
  @Deprecated
  public static void sendFullTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle)
  {
    sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
  }
  
  public static void sendPacket(Player player, Object packet)
  {
    try
    {
      Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
      Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
      playerConnection.getClass().getMethod("sendPacket", new Class[] {
        getNMSClass("Packet") })
        .invoke(playerConnection, new Object[] {
        packet });
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public static Class<?> getNMSClass(String name)
  {
    String version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    try
    {
      return Class.forName("net.minecraft.server." + version + "." + name);
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle)
  {
    try
    {
      if (title != null)
      {
        title = ChatColor.translateAlternateColorCodes('&', title);
        title = title.replaceAll("%player%", player.getDisplayName());
        
        Object e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
        Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] {
          String.class })
          .invoke(null, new Object[] {
          "{\"text\":\"" + title + "\"}" });
        
        Constructor subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] {
          getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], 
          getNMSClass("IChatBaseComponent"), 
          Integer.TYPE, 
          Integer.TYPE, 
          Integer.TYPE });
        
        Object titlePacket = subtitleConstructor.newInstance(new Object[] {
          e, 
          chatTitle, 
          fadeIn, 
          stay, 
          fadeOut });
        
        sendPacket(player, titlePacket);
        
        e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
        chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] {
          String.class })
          .invoke(null, new Object[] {
          "{\"text\":\"" + title + "\"}" });
        
        subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] {
          getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], 
          getNMSClass("IChatBaseComponent") });
        
        titlePacket = subtitleConstructor.newInstance(new Object[] {
          e, 
          chatTitle });
        
        sendPacket(player, titlePacket);
      }
      if (subtitle != null)
      {
        subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
        subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
        
        Object e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
        Object chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] {
          String.class })
          .invoke(null, new Object[] {
          "{\"text\":\"" + title + "\"}" });
        
        Constructor subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] {
          getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], 
          getNMSClass("IChatBaseComponent"), 
          Integer.TYPE, 
          Integer.TYPE, 
          Integer.TYPE });
        
        Object subtitlePacket = subtitleConstructor.newInstance(new Object[] {
          e, 
          chatSubtitle, 
          fadeIn, 
          stay, 
          fadeOut });
        
        sendPacket(player, subtitlePacket);
        
        e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
        chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] {
          String.class })
          .invoke(null, new Object[] {
          "{\"text\":\"" + subtitle + "\"}" });
        
        subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] {
          getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], 
          getNMSClass("IChatBaseComponent"), 
          Integer.TYPE, 
          Integer.TYPE, 
          Integer.TYPE });
        
        subtitlePacket = subtitleConstructor.newInstance(new Object[] {
          e, 
          chatSubtitle, 
          fadeIn, 
          stay, 
          fadeOut });
        
        sendPacket(player, subtitlePacket);
      }
    }
    catch (Exception var11)
    {
      var11.printStackTrace();
    }
  }
  
  public static void clearTitle(Player player)
  {
    sendTitle(player, Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), "", "");
  }
  
  public static void sendTabTitle(Player player, String header, String footer)
  {
    if (header == null) {
      header = "";
    }
    header = ChatColor.translateAlternateColorCodes('&', header);
    if (footer == null) {
      footer = "";
    }
    footer = ChatColor.translateAlternateColorCodes('&', footer);
    
    header = header.replaceAll("%player%", player.getDisplayName());
    footer = footer.replaceAll("%player%", player.getDisplayName());
    try
    {
      Object tabHeader = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] {
        String.class })
        .invoke(null, new Object[] {
        "{\"text\":\"" + header + "\"}" });
      
      Object tabFooter = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] {
        String.class })
        .invoke(null, new Object[] {
        "{\"text\":\"" + footer + "\"}" });
      
      Constructor<?> titleConstructor = getNMSClass("PacketPlayOutPlayerListHeaderFooter").getConstructor(new Class[] {
        getNMSClass("IChatBaseComponent") });
      
      Object packet = titleConstructor.newInstance(new Object[] {
        tabHeader });
      
      Field field = packet.getClass().getDeclaredField("b");
      field.setAccessible(true);
      field.set(packet, tabFooter);
      sendPacket(player, packet);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
}
