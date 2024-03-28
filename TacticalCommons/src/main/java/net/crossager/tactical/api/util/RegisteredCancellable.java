package net.crossager.tactical.api.util;

public interface RegisteredCancellable {
    boolean isCancelled();
    void cancel();
}
