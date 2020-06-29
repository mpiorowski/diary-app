package pbs.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer, AsyncUncaughtExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(AsyncConfig.class);

  private final AppConfig appConfig;

  public AsyncConfig(AppConfig appConfig) {
    this.appConfig = appConfig;
  }

  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(appConfig.getAsync().getCorePoolSize());
    executor.setMaxPoolSize(appConfig.getAsync().getMaxPoolSize());
    executor.setQueueCapacity(appConfig.getAsync().getQueueCapacity());
    executor.setThreadNamePrefix(appConfig.getAsync().getThreadNamePrefix());
    executor.initialize();
    return executor;
  }

  @Override
  public void handleUncaughtException(
      Throwable throwable, Method method, Object... obj) {

    LOGGER.error("Exception message - {}", throwable.getMessage());
    LOGGER.error("Method name - {}", method.getName());
    for (Object param : obj) {
      LOGGER.error("Parameter value - {}", param);
    }
  }

}
