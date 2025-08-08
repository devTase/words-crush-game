package org.academiadecodigo.wordsgame.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.academiadecodigo.wordsgame.database.Database;
import org.academiadecodigo.wordsgame.service.UserAuthenticator;
import org.academiadecodigo.wordsgame.service.UserService;

public class GameModule extends AbstractModule {

    @Override
    protected void configure() {
        // Bind interfaces to implementations
        bind(GameConfiguration.class).in(Singleton.class);
        bind(UserService.class).in(Singleton.class);
        bind(UserAuthenticator.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    Database provideDatabase() throws SQLException {
        Database database = new Database();
        database.startDb();
        return database;
    }

    @Provides
    @Singleton
    @GameExecutorService
    ExecutorService provideGameExecutorService() {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    }

    @Provides
    @Singleton
    @ClientExecutorService
    ExecutorService provideClientExecutorService() {
        return Executors.newCachedThreadPool();
    }
}
