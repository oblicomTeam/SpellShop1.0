package me.sn0wmatt.plugins.spellshop;

import java.util.HashSet;
import java.util.Set;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Spellshop extends JavaPlugin {
    
private final Spelllistener playerListener = new Spelllistener(this);
private final SpellShopBlockListener blockListener = new SpellShopBlockListener(this);

public Set<Player> hasSpellLightning = new HashSet<Player>();
public Set<Player> hasSpellFire = new HashSet<Player>();
public Set<Player> hasSpellExplosion = new HashSet<Player>();
public Set<Player> hasSpellTeleport = new HashSet<Player>();
public Set<Player> hasSpell = new HashSet<Player>();

public float config_explosion_rad = getConfig().getInt("spell.Explosion.power");
    
    public void onDisable() {
        // TODO: Place any custom disable code here.
        System.out.println(this + " is now disabled!");
    }

    public void onEnable() {
        // TODO: Place any custom enable code here, such as registering events
        PluginManager pm = getServer().getPluginManager();
        
        pm.registerEvent(Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
        pm.registerEvent(Type.BLOCK_PLACE, blockListener, Priority.Normal, this);
        
        System.out.println(this + ", by sn0wmatt, is now enabled on server!");
        
        setupConfig();
    }
    
    private void setupConfig(){
        getConfig().addDefault("spell.Lightning.price", "50.00");
        getConfig().addDefault("spell.Fire.price", "50.00");
        getConfig().addDefault("spell.Explosion.price", "50.00");
        getConfig().addDefault("spell.Teleport.price", "50.00");
        getConfig().addDefault("spell.Explosion.power", "4F");
    }
      
    public void setSpellLightning(Player player, boolean value) {
        if (hasSpellLightning.contains(player) && value == false) {
            hasSpellLightning.remove(player);
        }
        if (!hasSpellLightning.contains(player) && value == true) {
            hasSpellLightning.add(player);
        }
    }
    
    public void setSpellFire(Player player, boolean value) {
        if (hasSpellFire.contains(player) && value == false) {
            hasSpellFire.remove(player);
        }
        if (!hasSpellFire.contains(player) && value == true) {
            hasSpellFire.add(player);
        }
    }
    
    public void setSpellExplosion(Player player, boolean value) {
        if (hasSpellExplosion.contains(player) && value == false) {
            hasSpellExplosion.remove(player);
        }
        if (!hasSpellExplosion.contains(player) && value == true) {
            hasSpellExplosion.add(player);
        }
    }
    
    public void setSpellTeleport(Player player, boolean value) {
        if (hasSpellTeleport.contains(player) && value == false) {
            hasSpellTeleport.remove(player);
        }
        if (!hasSpellTeleport.contains(player) && value == true) {
            hasSpellTeleport.add(player);
        }
    }
    
    public void setSpell(Player player, boolean value) {
        if (hasSpell.contains(player) && value == false) {
            hasSpell.remove(player);
        }
        if (!hasSpell.contains(player) && value == true) {
            hasSpell.add(player);
        }
    }
    
    public boolean hasSpellLightning(Player player) {
        return hasSpellLightning.contains(player);
    }

    public boolean hasSpellFire(Player player) {
        return hasSpellFire.contains(player);
    }
    
    public boolean hasSpellExplosion(Player player) {
        return hasSpellExplosion.contains(player);
    }
    
    public boolean hasSpellTeleport(Player player) {
        return hasSpellTeleport.contains(player);
    }
    
    public boolean hasSpell(Player player) {
        return hasSpell.contains(player);
    }
    
}
