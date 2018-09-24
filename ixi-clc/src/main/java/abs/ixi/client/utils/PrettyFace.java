package abs.ixi.client.utils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PrettyFace {
    public static Logger logger(String source) {
	return new Logger(source);
    }

    public static Logger logger(Class<?> clz) {
	return new Logger(clz.getCanonicalName());
    }

    public static void flush() {
	for (String log : Logger.logStore) {
	    System.out.println(log);
	}
    }

    public static class Logger {
	private static List<String> logStore;
	private String source;

	public Logger(String source) {
	    logStore = new ArrayList<>();
	    this.source = source;
	}

	/**
	 * holds the log statement in memory
	 * 
	 * @param generator
	 *            class name which generated log
	 * @param log
	 *            log statement
	 * @return returns number of log statements log store
	 */
	public int hold(String log) {
	    logStore.add(prepareLog(log));
	    return logStore.size();
	}

	public void log(String log) {
	    System.out.println(prepareLog(log));
	}

	private String prepareLog(String log) {
	    return Instant.now() + " " + this.source + " " + log;
	}

    }
}
