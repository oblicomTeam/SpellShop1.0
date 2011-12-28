/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.sn0wmatt.plugins.spellshop;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 *
 * @author MatthewCarr
 */
public class SpellShopBlockListener extends BlockListener{
public Spellshop plugin;
public SpellShopBlockListener(Spellshop instance){
    plugin = instance;
}
    
    
    @Override
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();
        
        if (block.getType().equals(Material.SIGN_POST) || block.getType().equals(Material.WALL_SIGN)){
            
            Sign sign = (Sign)block.getState();
            
            if (player.hasPermission("spell.shop.cancreate")){
                if (sign.getLine(0).equalsIgnoreCase("[spellshop]")){
                sign.setLine(0, ChatColor.GOLD + "[SpellShop]");
                }
                if (sign.getLine(1).equals("Lightning") || sign.getLine(1).equals("Explosion") || sign.getLine(1).equals("Fire") || sign.getLine(1).equals("Teleport")){
                    sign.setLine(1, ChatColor.DARK_GREEN + sign.getLine(1));
                    sign.setLine(2, plugin.getConfig().getString("spell." + sign.getLine(1) + ".price"));
                    
                }
            
    } else {
                player.sendMessage("You do not have permission! I am going to destroy your sign!!");
                event.setCancelled(true);
            }
    
            
            
}
}
}