private static File languagecfg;
    private static FileConfiguration languagefile;
    public static Main m = Main.getPlugin(Main.class);

    public static void setup() {
        languagecfg = new File(m.getDataFolder(), "language_en.yml");
        if(!languagecfg.exists()) {
            try {
                languagecfg.createNewFile();
            } catch (IOException e) {
                // owww
            }
        }
        languagefile = YamlConfiguration.loadConfiguration(languagecfg);
    }

    public static FileConfiguration getLanguage() {
        return languagefile;
    }

    public static void saveLanguage() {
        File file = new File(m.getDataFolder(), "language_en.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        try {
            languagefile.save(languagecfg);
        } catch (IOException e) {
            System.out.println("Can't save language file");
        }
    }

    public static void reloadLanguage() {
        languagefile = YamlConfiguration.loadConfiguration(languagecfg);
    }
