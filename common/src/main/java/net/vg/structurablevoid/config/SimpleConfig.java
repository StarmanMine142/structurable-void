package net.vg.structurablevoid.config;

import dev.architectury.platform.Platform;
import net.vg.structurablevoid.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class SimpleConfig {

    private static final Logger LOGGER = LogManager.getLogger("SimpleConfig");
    private final LinkedHashMap<String, ConfigEntry> config = new LinkedHashMap<>();
    private final ConfigRequest request;

    public static final String MOD_ID = Constants.MOD_ID;
    public static final String MOD_NAME = Constants.MOD_NAME;
    public static final String MOD_VERSION = Constants.MOD_VERSION;

    private boolean broken = false;

    public interface DefaultConfig {
        String get(String namespace);

        static String empty(String namespace) {
            return "";
        }
    }

    public static class ConfigRequest {

        private final File file;
        private final String filename;
        private DefaultConfig provider;

        private ConfigRequest(File file, String filename) {
            this.file = file;
            this.filename = filename;
            this.provider = DefaultConfig::empty;
        }

        public ConfigRequest provider(DefaultConfig provider) {
            this.provider = provider;
            return this;
        }

        public SimpleConfig request() {
            return new SimpleConfig(this);
        }

        private String getConfig() {
            return provider.get(filename) + "\n";
        }
    }

    public static ConfigRequest of(String filename) {
        Path path = Platform.getConfigFolder();
        String versionedFilename = filename + "-" + MOD_VERSION + ".properties";
        return new ConfigRequest(path.resolve(versionedFilename).toFile(), versionedFilename);
    }

    private void createConfig() throws IOException {
        request.file.getParentFile().mkdirs();
        Files.createFile(request.file.toPath());

        PrintWriter writer = new PrintWriter(request.file, StandardCharsets.UTF_8);
        writer.write(request.getConfig());
        writer.close();
    }

    private void loadConfig() throws IOException {
        Scanner reader = new Scanner(request.file);
        StringBuilder currentComment = new StringBuilder();
        for (int line = 1; reader.hasNextLine(); line++) {
            currentComment = parseConfigEntry(reader.nextLine(), line, currentComment);
        }
    }

    private StringBuilder parseConfigEntry(String entry, int line, StringBuilder currentComment) {
        if (entry.startsWith("#")) {
            currentComment.append(entry).append("\n");
        } else if (!entry.isEmpty()) {
            String[] parts = entry.split("=", 2);
            if (parts.length == 2) {
                config.put(parts[0], new ConfigEntry(parts[1].split(" #")[0], currentComment.toString()));
                currentComment.setLength(0);
            } else {
                throw new RuntimeException("Syntax error in config file on line " + line + "!");
            }
        } else {
            currentComment.append("\n");
        }
        return currentComment;
    }

    private SimpleConfig(ConfigRequest request) {
        this.request = request;
        String identifier = "Config '" + request.filename + "'";

        if (!request.file.exists()) {
            LOGGER.info(identifier + " is missing, generating default one...");

            try {
                createConfig();
            } catch (IOException e) {
                LOGGER.error(identifier + " failed to generate!");
                LOGGER.trace(e);
                broken = true;
            }
        }

        if (!broken) {
            try {
                loadConfig();
            } catch (Exception e) {
                LOGGER.error(identifier + " failed to load!");
                LOGGER.trace(e);
                broken = true;
            }
        }
    }

    public String get(String key) {
        ConfigEntry entry = config.get(key);
        return entry != null ? entry.value : null;
    }

    public String getOrDefault(String key, String def) {
        String val = get(key);
        return val == null ? def : val;
    }

    public int getOrDefault(String key, int def) {
        try {
            return Integer.parseInt(get(key));
        } catch (Exception e) {
            return def;
        }
    }

    public boolean getOrDefault(String key, boolean def) {
        String val = get(key);
        if (val != null) {
            return val.equalsIgnoreCase("true");
        }
        return def;
    }

    public double getOrDefault(String key, double def) {
        try {
            return Double.parseDouble(get(key));
        } catch (Exception e) {
            return def;
        }
    }

    public boolean isBroken() {
        return broken;
    }

    public boolean delete() {
        LOGGER.warn("Config '" + request.filename + "' was removed from existence! Restart the game to regenerate it.");
        return request.file.delete();
    }

    public void set(String key, String value) {
        ConfigEntry entry = config.get(key);
        if (entry != null) {
            entry.value = value;
        } else {
            config.put(key, new ConfigEntry(value, ""));
        }
    }

    public void set(String key, int value) {
        ConfigEntry entry = config.get(key);
        if (entry != null) {
            entry.value = Integer.toString(value);
        } else {
            config.put(key, new ConfigEntry(Integer.toString(value), ""));
        }
    }

    public void set(String key, boolean value) {
        ConfigEntry entry = config.get(key);
        if (entry != null) {
            entry.value = Boolean.toString(value);
        } else {
            config.put(key, new ConfigEntry(Boolean.toString(value), ""));
        }
    }

    public void set(String key, double value) {
        ConfigEntry entry = config.get(key);
        if (entry != null) {
            entry.value = Double.toString(value);
        } else {
            config.put(key, new ConfigEntry(Double.toString(value), ""));
        }
    }



    public void save() {
        try {
            boolean needsRecreation = false;

            String[] necessaryConfigs = ModConfigs.necessaryConfigs;
            for (String key : necessaryConfigs) {
                if (!config.containsKey(key)) {
                    needsRecreation = true;
                    break;
                }
            }

            if (needsRecreation) {
                createConfig();
                loadConfig();
            } else {
                PrintWriter writer = new PrintWriter(request.file, "UTF-8");
                for (Map.Entry<String, ConfigEntry> entry : config.entrySet()) {
                    if (entry.getValue() != null && !entry.getValue().comment.isEmpty()) {
                        writer.println(entry.getValue().comment.trim());
                    }
                    if (entry.getValue() != null) {
                        writer.println(entry.getKey() + "=" + entry.getValue().value);
                        writer.println();
                    }
                }
                writer.close();
            }
        } catch (IOException e) {
            LOGGER.error("Failed to save config!");
            LOGGER.trace(e);
        }
    }


    public void setComment(String key, String comment) {
        ConfigEntry entry = config.get(key);
        if (entry != null) {
            entry.comment = comment;
        } else {
            config.put(key, new ConfigEntry("", comment));
        }
    }

    private static class ConfigEntry {
        String value;
        String comment;

        ConfigEntry(String value, String comment) {
            this.value = value;
            this.comment = comment;
        }
    }
}
