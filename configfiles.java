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
private final File folder;
private final String fileName;

public ConfigFile(String folder, String fileName) {
    config = this;
    this.folder = new File(m.getDataFolder(), folder);
    if(!this.folder.exists())
        this.folder.mkdir();

    this.fileName = fileName;
    setup();
}

public ConfigFile(String fileName) {
    config = this;
    this.fileName = fileName;
    this.folder = null;
    setup();
}

public void setup() {
    String filePath = this.folder != null ? this.folder.getName() + "\\" + this.fileName : this.fileName;
    cfg = new File(m.getDataFolder(), filePath);
    if (!cfg.exists()) {
        try {
            if(this.folder == null) {
                m.saveResource(this.fileName, false);
                return;
            }
            cfg.createNewFile();
            InputStream in = m.getResource(this.fileName);
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
