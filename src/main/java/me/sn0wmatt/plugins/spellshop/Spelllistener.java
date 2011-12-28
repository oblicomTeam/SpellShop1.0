/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.sn0wmatt.plugins.spellshop;

import java.util.List;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
        
/**
 *
 * @author Matthew Carr
 */
public class Spelllistener extends PlayerListener{
public static Economy economy = null;
public Spellshop plugin;
public Spelllistener(Spellshop instance){
    plugin = instance;
}

    private boolean setupEconomy(){
        Plugin vault = plugin.getServer().getPluginManager().getPlugin("Vault");
        if (vault == null){
            return false;
        }
        RegisteredServiceProvider<Economy> economyProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null){
            economy = economyProvider.getProvider();
        }
        
        return (economy != null);
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
        
    Player player = event.getPlayer();
    Action action = event.getAction();
    
    if (!player.hasPermission("spell.shop.canuse")) {
        return;
    }
    
    if (action == Action.RIGHT_CLICK_BLOCK) {   
    Block block  = event.getClickedBlock();
    
    if ((!block.getType().equals(Material.WALL_SIGN)) && (!block.getType().equals(Material.SIGN_POST))){
        return;
    }
    
    Sign sign = ((Sign) block.getState());
    
        if (sign.getLine(0).equals(ChatColor.GOLD + "[SpellShop]")){
            
            String spellType = sign.getLine(1);
            
            if (spellType.isEmpty()) {
                player.sendMessage(ChatColor.RED + "Spell line is empty!");
                return;
            }
            
            if (!sign.getLine(2).isEmpty()){
                player.sendMessage("You do not need to set the price.");
            }
            
            ItemStack it = new ItemStack(Material.BOOK, 1); //Spell book
            Double price = plugin.getConfig().getDouble("spell." + sign.getLine(1) + ".price");
            
            //determining the item
            if (plugin.hasSpell(player)) {
                player.sendMessage("You already have a spell assigned!");
                return;
            }
            
            if (spellType.contains("Lightning")){
                plugin.setSpellLightning(player, true);
                plugin.setSpell(player, true);
                player.getInventory().addItem(it);
            }
            
            if (spellType.contains("Explosion")){
                plugin.setSpellExplosion(player, true);
                plugin.setSpell(player, true);
                player.getInventory().addItem(it);
            }
            
            if (spellType.contains("Fire")){
                plugin.setSpellFire(player, true);
                plugin.setSpell(player, true);
                player.getInventory().addItem(it);
            }
            
            if (spellType.contains("Teleport")){
                plugin.setSpellTeleport(player, true);
                plugin.setSpell(player, true);
                player.getInventory().addItem(it);
            }
            
            player.sendMessage("You have bought one " + spellType + " spell. Right click your target to use the spell!");
            economy.withdrawPlayer(player.getName() , plugin.getConfig().getDouble("spell." + sign.getLine(1) + ".price"));
        }
    }
    if (action.equals(Action.RIGHT_CLICK_AIR)) {
        ItemStack it = player.getItemInHand();
        
        if (!player.hasPermission("spell.shop.castspell")) {
            return;
        }
        
        if (plugin.hasSpellLightning(player)){
        
        if (it.getType().equals(Material.BOOK)) {
            player.getWorld().strikeLightning(player.getTargetBlock(null, 50).getLocation());
            plugin.setSpellLightning(player, false);
            plugin.setSpell(player, false);
            return;
        }
        }
        
        if (plugin.hasSpellFire(player)){
            
        if (it.getType().equals(Material.BOOK)) {
            
            List<Entity> entities = player.getNearbyEntities(10, 10, 10);
            
            if (!entities.isEmpty()) {
                for (Entity e : entities) {
                    e.setFireTicks(1000);
                }
            }
            plugin.setSpellFire(player, false);
            plugin.setSpell(player, false);
            
            return;
        }
        }
        
        if (plugin.hasSpellExplosion(player)){
        
        if (it.getType().equals(Material.BOOK)) {
            player.getWorld().createExplosion(player.getTargetBlock(null, 50).getLocation(), plugin.config_explosion_rad);
            plugin.setSpellExplosion(player, false);
            plugin.setSpell(player, false);
            
            return;
        }
        }
        
        if (plugin.hasSpellTeleport(player)){
        
        if (it.getType().equals(Material.BOOK)){
            player.teleport(player.getTargetBlock(null, 100).getLocation());
            plugin.setSpellTeleport(player, false);
            plugin.setSpell(player, false);
            
        }
        }
    }
}
}