package main;

public abstract class Config {

    private Config() {
    }

    private static final int AS_SERVER_PORT = 40000;

    private static final int TCP_LOGGER_PORT = 3244;

    public static int getAsServerPort() {
        return AS_SERVER_PORT;
    }

    public static int getTcpLoggerPort() {
        return TCP_LOGGER_PORT;
    }
}
