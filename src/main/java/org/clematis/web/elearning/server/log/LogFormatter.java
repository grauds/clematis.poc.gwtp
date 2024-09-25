package org.clematis.web.elearning.server.log;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class LogFormatter extends SimpleFormatter {

    private static String version;

    static
    {
        Properties properties = new Properties();
        try
        {
            properties.load(LogFormatter.class.getResourceAsStream("/version.properties"));
            version = properties.getProperty("version");
        }
        catch (IOException e)
        {
            LoggerFactory.getLogger(LogFormatter.class.getName()).log(Level.SEVERE, "Cannot load version number from properties");
        }
    }

    /**
     * Format message if it is configured properly in application logging.properties file as handler for application logging.
     *
     * @param record to be formatted
     * @return formatted message
     */
    @Override
    public synchronized String format(LogRecord record)
    {
        return getVersion() + ": " + super.format(record);
    }

    /**
     * Format message for default log handler
     *
     * @param message to be formatted
     * @return formatted message
     */
    public String format(String message)
    {
        return getVersion() + ": " + message;
    }


    public String getVersion()
    {
        return version;
    }
}
