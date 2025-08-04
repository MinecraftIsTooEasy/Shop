package cn.wensc.mitemod.shop.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigTab;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.SimpleConfigs;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class ShopConfigML extends SimpleConfigs {
    public static final ConfigBoolean EditMode = new ConfigBoolean("shop.EditMode", false);

    private static final ShopConfigML Instance;
    public static final List<ConfigBase<?>> configValues;
    public static final List<ConfigBase<?>> general;

    public static final List<ConfigTab> tabs = new ArrayList<>();

    public ShopConfigML() {
        super("Shop-General", null, general);
    }

    static {
        general = List.of(EditMode);
        configValues = new ArrayList<>();
        configValues.addAll(general);
        tabs.add(new ConfigTab("shop.general", general));
        Instance = new ShopConfigML();
    }

    @Override
    public List<ConfigTab> getConfigTabs() {
        return tabs;
    }

    public static ShopConfigML getInstance() {
        return Instance;
    }

    @Override
    public void save() {
        JsonObject root = new JsonObject();
        ConfigUtils.writeConfigBase(root, "general", general);
        JsonUtils.writeJsonToFile(root, this.optionsFile);
    }

    @Override
    public void load() {
        if (!this.optionsFile.exists()) {
            this.save();
        } else {
            JsonElement jsonElement = JsonUtils.parseJsonFile(this.optionsFile);
            if (jsonElement != null && jsonElement.isJsonObject()) {
                JsonObject root = jsonElement.getAsJsonObject();
                ConfigUtils.readConfigBase(root, "general", general);

            }
        }
    }
}
