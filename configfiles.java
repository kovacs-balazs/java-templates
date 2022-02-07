private static File namecfg;
private static FileConfiguration namefile;

public static Main m = Main.getPlugin(Main.class);

public static void setup() {
    namecfg = new File(m.getDataFolder(), "name.yml"); //
    if(!namecfg.exists()) {
        try {
            namecfg.createNewFile();
        } catch (IOException e) {
            // owww
        }
    }
    namefile = YamlConfiguration.loadConfiguration(namecfg);
}

public static FileConfiguration getName() {
    return namefile;
}

public static void saveName() {
    File file = new File(m.getDataFolder(), "name.yml");
    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
    try {
        namefile.save(namecfg);
    } catch (IOException e) {
        System.out.println("Can't save language file");
    }
}

public static void reloadName() {
    namefile = YamlConfiguration.loadConfiguration(namecfg);
}
