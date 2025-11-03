package com.example.rpneeds;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class RoleplayNeeds extends JavaPlugin implements Listener {

    private final HashMap<UUID, Integer> urine = new HashMap<>();
    private final HashMap<UUID, Integer> poop = new HashMap<>();
    private final HashMap<UUID, Integer> thirst = new HashMap<>();

    private final HashMap<UUID, Long> lastUrine = new HashMap<>();
    private final HashMap<UUID, Long> lastPoop = new HashMap<>();
    private final HashMap<UUID, Long> lastDrink = new HashMap<>();

    private int maxLevel, increaseInterval, increaseAmount;
    private int urineCooldown, poopCooldown;
    private int slownessLevel, slownessDuration;
    private int dehydrationTime, dizzyDuration;
    private String prefix, deathMsg, thirstDeathMsg;
    private String drinkSound, drinkParticle, drinkMsg, badDrinkMsg;
    private String drinkCooldownMsg;
    private String badWaterEffectMsg;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfig();
        Bukkit.getPluginManager().registerEvents(this, this);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new NeedsPlaceholder(this).register();
        }

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                UUID id = p.getUniqueId();

                urine.put(id, urine.getOrDefault(id, 0) + increaseAmount);
                poop.put(id, poop.getOrDefault(id, 0) + increaseAmount);
                thirst.put(id, thirst.getOrDefault(id, 0) + increaseAmount);

                int u = urine.get(id);
                int po = poop.get(id);
                int th = thirst.get(id);

                if (u >= maxLevel || po >= maxLevel || th >= maxLevel) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, slownessDuration * 20, slownessLevel));
                }

                long lastDrinkTime = lastDrink.getOrDefault(id, 0L);
                long elapsed = System.currentTimeMillis() - lastDrinkTime;
                long limit = dehydrationTime * 1000L;

                if (elapsed > limit) {
                    if (elapsed < limit + (dizzyDuration * 1000L)) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 1));
                    } else {
                        p.setHealth(0);
                        p.sendMessage(color(prefix + thirstDeathMsg));
                    }
                }

                if (u >= maxLevel && po >= maxLevel) {
                    p.setHealth(0);
                    p.sendMessage(color(prefix + deathMsg));
                }
            }
        }, 0L, 20L * increaseInterval);

        getLogger().info("RoleplayNeeds Enabled!");
    }

    private void loadConfig() {
        reloadConfig();
        prefix = getConfig().getString("prefix", "&7[Needs] ");
        maxLevel = getConfig().getInt("max-level", 100);
        increaseInterval = getConfig().getInt("increase-interval", 60);
        increaseAmount = getConfig().getInt("increase-amount", 1);
        urineCooldown = getConfig().getInt("cooldowns.urine", 30);
        poopCooldown = getConfig().getInt("cooldowns.pop", 30);
        slownessLevel = getConfig().getInt("effects.slowness-level", 1);
        slownessDuration = getConfig().getInt("effects.slowness-duration", 60);

        dehydrationTime = getConfig().getInt("thirst.dehydration-seconds", 2400); // 40 minutes real = 2 days MC
        dizzyDuration = getConfig().getInt("thirst.dizzy-seconds", 300); // 5 minutes

        drinkSound = getConfig().getString("thirst.drink-sound", "ENTITY_GENERIC_DRINK");
        drinkParticle = getConfig().getString("thirst.drink-particle", "WATER_SPLASH");
        drinkMsg = getConfig().getString("thirst.message-success", "&bYou drank water!");
        badDrinkMsg = getConfig().getString("thirst.message-bad-water", "&cYou drank dirty water!");
        badWaterEffectMsg = getConfig().getString("thirst.message-sick", "&cYou feel sick from dirty water!");
        thirstDeathMsg = getConfig().getString("thirst.message-death", "&cYou died from dehydration!");
    }

    @EventHandler
    public void onDrink(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        if (e.getItem().getType() == Material.POTION) {
            UUID id = p.getUniqueId();
            thirst.put(id, 0);
            lastDrink.put(id, System.currentTimeMillis());
            p.sendMessage(color(prefix + drinkMsg));
            p.getWorld().playSound(p.getLocation(), Sound.valueOf(drinkSound), 1f, 1f);
            p.getWorld().spawnParticle(Particle.valueOf(drinkParticle), p.getLocation(), 10);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getHand() != EquipmentSlot.HAND) return;
        Player p = e.getPlayer();
        if (p.getTargetBlockExact(5) == null) return;

        Material m = p.getTargetBlockExact(5).getType();
        if (m == Material.WATER || m == Material.CAULDRON || m == Material.WATER_CAULDRON) {
            UUID id = p.getUniqueId();
            thirst.put(id, 0);
            lastDrink.put(id, System.currentTimeMillis());

            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 60, 1));
            p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20 * 60, 1)); 
            p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 10, 0)); 

            p.sendMessage(color(prefix + badDrinkMsg));
            p.sendMessage(color(prefix + badWaterEffectMsg));
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_DRINK, 1f, 0.7f);
        }
    }

    private String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public int getUrine(UUID id) { return urine.getOrDefault(id, 0); }
    public int getPoop(UUID id) { return poop.getOrDefault(id, 0); }
    public int getThirst(UUID id) { return thirst.getOrDefault(id, 0); }

    @Override
    public void onDisable() {
        getLogger().info("RoleplayNeeds Disabled!");
    }
}
