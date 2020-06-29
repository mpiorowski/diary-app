package pbs.api.rest.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pbs.api.logging.LogExecutionTime;
import pbs.api.tests.AsyncTest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/api/test")
public class AsyncTestController {

  private static final Logger logger = LoggerFactory.getLogger(AsyncTestController.class);
  private final AsyncTest asyncTest;
  public AsyncTestController(AsyncTest asyncTest) {
    this.asyncTest = asyncTest;
  }

  @GetMapping("/async")
  @LogExecutionTime
  public ResponseEntity<String> async() {
    try {
      logger.info("Async test start");
      Future<String> future = asyncTest.asyncMethodWithReturnType();
      while (true) {
        if (future.isDone()) {
          logger.debug(future.get());
          return ResponseEntity.ok("Async test completed");
        }
        logger.info("Continue doing something else. ");
        Thread.sleep(1000);
      }
    } catch (InterruptedException | ExecutionException e) {
      logger.error(e.getMessage());
      Thread.currentThread().interrupt();
      return ResponseEntity.ok("Async test failed");
    }
  }
}
