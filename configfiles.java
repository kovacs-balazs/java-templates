// Importok:
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.List;

////////////////// CODE ////////////////

private static Main m = Main.getPlugin(Main.class);
private static File cfg;
private static FileConfiguration file;

private static ConfigFile config;
private final File ymlFile;

public GuiItemsFile(String ymlFile) {
    config = this;
    this.ymlFile = new File(m.getDataFolder(), ymlFile);
    setup();
}

public void setup() {
    cfg = ymlFile;
    if (!cfg.exists()) {
        try {
            ymlFile.getParentFile().mkdirs();
            ymlFile.createNewFile();
            InputStream in = m.getResource(ymlFile.getName());
            FileOutputStream out = new FileOutputStream(cfg);

            if(in == null) return;
            try {
                int n;
                while ((n = in.read()) != -1) {
                    out.write(n);
                }
            }
            finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }

        } catch (IOException e) {
        }
    }
    file = YamlConfiguration.loadConfiguration(cfg);
}

public FileConfiguration getFile() {
    return file;
}

public void save() {
    try {
        file.save(cfg);
    } catch (IOException e) {
        System.out.println("Can't save language file");
    }
}

public void reload() {
    file = YamlConfiguration.loadConfiguration(cfg);
}

public static FileConfiguration get() {
    return getConfig().getFile();
}

public static String getString(String path) {
    return Formatter.applyColor(get().getString(path));
}

public static List<String> getStringList(String path) {
    return get().getStringList(path).stream().map(Formatter::applyColor).toList();
}

public static ConfigFile getConfig() {
    return config;
}
