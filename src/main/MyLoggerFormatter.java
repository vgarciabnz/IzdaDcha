package main;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * This class extends Formatter class and is used to customized the AppTm4l log
 * appearance
 * 
 * @author ehas
 * 
 */
public class MyLoggerFormatter extends Formatter {

	@Override
	public String format(LogRecord record) {
		String message = record.getMessage() + "\n";
		return message;
	}

}