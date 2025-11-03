package com.example.rpneeds;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NeedsPlaceholder extends PlaceholderExpansion {

    private final RoleplayNeeds plugin;

    public NeedsPlaceholder(RoleplayNeeds plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "rpneeds";
    }

    @Override
    public @NotNull String getAuthor() {
        return "FoadSh";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player p, @NotNull String identifier) {
        if (p == null) return "";

        switch (identifier.toLowerCase()) {
            case "urine": return plugin.getUrine(p.getUniqueId()) + "%";
            case "pops": return plugin.getPoop(p.getUniqueId()) + "%";
            case "thirst": return plugin.getThirst(p.getUniqueId()) + "%";
            default: return null;
        }
    }
}
