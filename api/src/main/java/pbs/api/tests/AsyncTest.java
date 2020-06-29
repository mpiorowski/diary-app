package pbs.api.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class AsyncTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(AsyncTest.class);

  @Async
  public Future<String> asyncMethodWithReturnType() {
    LOGGER.debug("Execute method asynchronously - {}", Thread.currentThread().getName());
    try {
      Thread.sleep(5000);
      return new AsyncResult<>("Async test completed");
    } catch (InterruptedException e) {
      LOGGER.error("Async method error - {}", e.getMessage());
      Thread.currentThread().interrupt();
    }

    return null;
  }
}
