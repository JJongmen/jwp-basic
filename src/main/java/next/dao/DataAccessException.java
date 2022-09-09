package next.dao;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class DataAccessException extends RuntimeException {
    private final Logger log = getLogger(DataAccessException.class);

    public DataAccessException() {
        log.error("SQLException occured.");
    }

    public DataAccessException(String message) {
        super(message);
        log.error(message);
    }
}
