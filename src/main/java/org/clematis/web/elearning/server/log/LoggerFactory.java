package org.clematis.web.elearning.server.log;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LoggerFactory {
    private static final LogFormatter formatter = new LogFormatter();
    private static final LoggerFactory loggerFactory;
    private static final Logger logger;

    static
    {
        loggerFactory = new LoggerFactory();
        logger = Logger.getLogger("org.clematis");
    }

    /**
     * todo One logger for all?
     *
     * @param name of a logger
     * @return single instance of logger
     */
    public static LoggerFactory getLogger(String name)
    {
        return loggerFactory;
    }

    public Logger getLogger()
    {
        return logger;
    }

    public void log(Level level, String msg, Object params[])
    {
        LogRecord lr = new LogRecord(level, formatter.format(msg));
        lr.setParameters(params);
        lr.setLoggerName(getLogger().getName());
        getLogger().log(lr);
    }

    public void log(Level level, String msg, Exception thrown)
    {
        LogRecord lr = new LogRecord(level, formatter.format(msg));
        lr.setThrown(thrown);
        lr.setLoggerName(getLogger().getName());
        getLogger().log(lr);
    }

    public void log(Level level, String msg)
    {
        LogRecord lr = new LogRecord(level, formatter.format(msg));
        lr.setLoggerName(getLogger().getName());
        getLogger().log(lr);
    }

    public void log(Level level, String msg, Object param1)
    {
        LogRecord lr = new LogRecord(level, formatter.format(msg));
        lr.setLoggerName(getLogger().getName());
        Object params[] = {param1};
        lr.setParameters(params);
        getLogger().log(lr);
    }
}
