import org.bukkit.*;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigItem {

    public static ItemStack getItem(String configName, FileConfiguration config, ConfigurationSection configSection) {
        HashMap<String, Object> map = getMap(configName, config, configSection);
        Material material = Material.getMaterial(map.get("material") + "");
        String base64 = (String) map.get("base64");
        String displayName = ChatColor.translateAlternateColorCodes('&', String.valueOf(map.get("display_name")));
        int amount = (int) map.get("amount");
        int customModelData = (int) map.get("custom_model_data");
        byte data = Byte.parseByte(String.valueOf(map.get("data")));
        boolean hide_unbreakble = (boolean) map.get("hide_unbreakable");
        boolean unbreakble = (boolean) map.get("unbreakable");
        List<String> lore                      = new ArrayList<>();
        List<ItemFlag> itemFlags               = new ArrayList<>();
        List<Pattern> patterns                 = new ArrayList<>();
        HashMap<Enchantment, Integer> enchants = new HashMap<>();

        boolean hasDisplayName = true;
        if(displayName.equalsIgnoreCase("") || displayName.equalsIgnoreCase("null")) {
            hasDisplayName = false;
        }

        for (String flag : (List<String>) map.get("item_flags")) {
            if(ItemFlag.valueOf(flag) != null)
                itemFlags.add(ItemFlag.valueOf(flag));
        }

        if(map.containsKey("enchantments")) {
            for (String s : (List<String>) map.get("enchantments")) {
                String[] tags = s.split(";");
                Enchantment enc = Enchantment.getByKey(NamespacedKey.minecraft(tags[0].toLowerCase()));
                int level = tags[1].matches("^[0-9]+$") ? Integer.parseInt(tags[1]) : 0;
                enchants.put(enc, level);
            }
        }

        if(map.containsKey("lore")) {
            for (String s : (List<String>) map.get("lore")) {
                lore.add(ChatColor.translateAlternateColorCodes('&', s));
            }
        }


        if(map.containsKey("banner_meta")) {
            for (String s : (List<String>) map.get("banner_meta")) {
                String[] tags = s.split(";");
                DyeColor color = DyeColor.legacyValueOf(tags[0].toUpperCase());

                PatternType type = PatternType.getByIdentifier(tags[1].toLowerCase());
                if(type == null)
                    type = PatternType.valueOf(tags[1].toUpperCase());

                Pattern pattern = new Pattern(color, type);
                patterns.add(pattern);
            }
        }

        Color color = null;
        if(map.containsKey("leather_armor_color")) {
            String armorColor = String.valueOf(map.get("leather_armor_color"));
            if(armorColor.startsWith("#"))
                color = hex2Rgb(armorColor);
            else {
                DyeColor dye = DyeColor.legacyValueOf(armorColor.toUpperCase());
                color = Color.fromRGB(dye.getColor().asRGB());
            }
        }

        ItemStack is = new ItemStack(material, amount, data);
        ItemStack onlySkull = SkullCreator.createSkull();

        if(is.getType() == onlySkull.getType() && is.getData().getData() == onlySkull.getData().getData()) {
            SkullMeta skull = (SkullMeta) is.getItemMeta();
            if(base64 == null) {
                String skullName = (String) map.get("skull_owner");
                skull.setOwningPlayer(Bukkit.getOfflinePlayer(skullName));
                is.setItemMeta(skull);
            } else {
                is = SkullCreator.itemFromBase64(base64);
                is.setAmount(amount);
            }
        }

        if(is.getType().name().contains("BANNER")) {
            BannerMeta im = (BannerMeta) is.getItemMeta();
            im.setPatterns(patterns);
            is.setItemMeta(im);
        }

        if(is.getType().name().startsWith("LEATHER_") && isArmor(is)) {
            LeatherArmorMeta armor = (LeatherArmorMeta) is.getItemMeta();
            if(color != null)
                armor.setColor(color);
            is.setItemMeta(armor);
        }

        ItemMeta im = is.getItemMeta();
        if(hasDisplayName)
            im.setDisplayName(displayName);

        im.setLore(lore);
        im.setCustomModelData(customModelData);

        if(!itemFlags.isEmpty())
            im.addItemFlags(itemFlags.toArray(new ItemFlag[0]));

        im.setUnbreakable(unbreakble);

        if(!enchants.isEmpty())
            enchants.forEach((enc, level) -> im.addEnchant(enc, level, true));

        if(hide_unbreakble)
            im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        is.setItemMeta(im);
        return is;
    }

    private static boolean isArmor(final ItemStack itemStack) {
        if (itemStack == null)
            return false;
        final String typeNameString = itemStack.getType().name();
        if (typeNameString.endsWith("_HELMET")
                || typeNameString.endsWith("_CHESTPLATE")
                || typeNameString.endsWith("_LEGGINGS")
                || typeNameString.endsWith("_BOOTS")) {
            return true;
        }

        return false;
    }

    private static Color hex2Rgb(String colorStr) {
        return Color.fromRGB(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }

    public static HashMap<String, Object> getMap(String configName, FileConfiguration config, ConfigurationSection configSection) {
        HashMap<String, Object> hashMap = baseValues();
        for (String key : configSection.getKeys(false)) {
            hashMap.put(key, config.get(configName + "." + key));
        }
        return hashMap;
    }

    private static HashMap<String, Object> baseValues() {
        HashMap<String, Object> map = new HashMap();
        map.put("material", Material.AIR);
        map.put("display_name", null);
        map.put("lore", new ArrayList<String>());
        map.put("amount", 1);
        map.put("data", 0);
        map.put("base64", null);
        map.put("enchantments", new ArrayList<String>());
        map.put("item_flags", new ArrayList<String>());
        map.put("custom_model_data", 0);
        map.put("unbreakable", false);
        map.put("hide_unbreakable", false);
        map.put("skull_owner", null);
        map.put("banner_meta", new ArrayList<>());
        map.put("leather_armor_color", null);
        return map;
    }
