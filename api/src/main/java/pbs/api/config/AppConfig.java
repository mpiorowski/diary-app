package pbs.api.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@Getter
public class AppConfig {

  private final CorsConfiguration cors = new CorsConfiguration();
  private final Authentication authentication = new Authentication();
  private final Async async = new Async();
  private final Scheduler scheduler = new Scheduler();

  @Data
  public static class Authentication {
    long jwtExpirationInMsRememberMe = AppDefaults.Authentication.JWT_EXPIRATION_IN_SEC_REMEMBER_ME;
    long jwtExpirationInMs = AppDefaults.Authentication.JWT_EXPIRATION_IN_SEC;
  }

  @Data
  public static class Async {
    int corePoolSize = AppDefaults.Async.CORE_POOL_SIZE;
    int maxPoolSize = AppDefaults.Async.MAX_POOL_SIZE;
    int queueCapacity = AppDefaults.Async.QUEUE_CAPACITY;
    String threadNamePrefix = AppDefaults.Async.THREAD_NAME_PREFIX;
  }

  @Data
  public static class Scheduler {
    int corePoolSize = AppDefaults.Scheduler.CORE_POOL_SIZE;
    String threadNamePrefix = AppDefaults.Scheduler.THREAD_NAME_PREFIX;
  }
}
