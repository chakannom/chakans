package com.chakans.core.config.shutdown;

import io.undertow.server.HandlerWrapper;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.GracefulShutdownHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

public class UndertowGracefulShutdownHandlerWrapper implements ApplicationListener<ContextClosedEvent>, HandlerWrapper {

    private final Logger log = LoggerFactory.getLogger(UndertowGracefulShutdownHandlerWrapper.class);

    private final long shutdownTimeoutSeconds;

    private GracefulShutdownHandler gracefulShutdownHandler;

    public UndertowGracefulShutdownHandlerWrapper() {
        this(10);
    }

    public UndertowGracefulShutdownHandlerWrapper(long shutdownTimeoutSeconds) {
        this.shutdownTimeoutSeconds = shutdownTimeoutSeconds;
    }

    @Override
    public HttpHandler wrap(HttpHandler handler) {
        if (gracefulShutdownHandler == null) {
            gracefulShutdownHandler = new GracefulShutdownHandler(handler);
        }
        return gracefulShutdownHandler;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.warn("Context closed. Going to await termination for {} seconds.", shutdownTimeoutSeconds);
        try {
            gracefulShutdownHandler.shutdown();
            if(!gracefulShutdownHandler.awaitShutdown(shutdownTimeoutSeconds * 1000)) {
                log.warn("It did not shut down gracefully within {} seconds. Proceeding with forceful shutdown.", shutdownTimeoutSeconds);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}