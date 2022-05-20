private static File cfg;
private static FileConfiguration file;

public static Main m = Main.getPlugin(Main.class);

public static void setup() {
    cfg = new File(m.getDataFolder(), "name.yml"); //
    if(!cfg.exists()) {
        try {
            cfg.createNewFile();
        } catch (IOException e) {
            // owww
        }
    }
    file = YamlConfiguration.loadConfiguration(cfg);
}

public static FileConfiguration getName() {
    return file;
}

public static void saveName() {
    File files = new File(m.getDataFolder(), "name.yml");
    YamlConfiguration config = YamlConfiguration.loadConfiguration(files);
    try {
        file.save(cfg);
    } catch (IOException e) {
        System.out.println("Can't save language file");
    }
}

public static void reloadName() {
    file = YamlConfiguration.loadConfiguration(cfg);
}
