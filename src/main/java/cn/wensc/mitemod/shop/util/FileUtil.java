package cn.wensc.mitemod.shop.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class FileUtil {
    private static final Logger LOGGER = LogManager.getLogger(FileUtil.class);

    public static void loadOrCreateFile(File fileObj, BiConsumer<File, Properties> loadAction, Consumer<File> createAction) {
        if (fileObj.exists()) {
            Properties properties = new Properties();
            FileReader fr = null;
            try {
                fr = new FileReader(fileObj);
                properties.load(fr);
                fr.close();
                loadAction.accept(fileObj, properties);
            } catch (IOException e) {
                LOGGER.warn("fail loading file", e);
            }
        } else {
            if (!fileObj.getParentFile().exists()) {
                fileObj.getParentFile().mkdirs();
            }
            try {
                if (fileObj.createNewFile()) {
                    fileObj.setExecutable(true);
                    fileObj.setReadable(true);
                    fileObj.setWritable(true);
                    createAction.accept(fileObj);
                }
            } catch (IOException e) {
                LOGGER.warn("fail creating file", e);
            }
        }
    }
}
