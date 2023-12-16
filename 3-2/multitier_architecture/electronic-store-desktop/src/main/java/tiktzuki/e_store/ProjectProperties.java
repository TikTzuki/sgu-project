package tiktzuki.e_store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class ProjectProperties {
    public static Properties properties = new Properties();

    public static FileHandler getFileHandlerLogger() {
        FileHandler fh = null;
        try {
            // This block configure the logger with handler and formatter
            fh = new FileHandler("src/MyLogFile.log");
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fh;
    }

    public void writeProperties(String key, String value) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(new File("config.properties"));
            ProjectProperties.properties.setProperty(key, value);
            ProjectProperties.properties.store(out, null);
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
    }

    public static String getProperties(String key) {
        InputStream input = null;
        // Properties prop = new Properties();
        try {
            input = new FileInputStream("config.properties");
            ProjectProperties.properties.load(input);
        } catch (Exception e) {
        }
        return ProjectProperties.properties.getProperty(key);
    }

    public static void main(String[] args) {
        ProjectProperties pros = new ProjectProperties();
        //pros.writeProperties("mysql.database", "tours");
        System.out.println(ProjectProperties.getProperties("mysql.database"));
    }
}
