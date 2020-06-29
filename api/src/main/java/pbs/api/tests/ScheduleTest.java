package pbs.api.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Service
public class ScheduleTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleTest.class);
  private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

//  @Scheduled(fixedDelay = 2000)
  public void scheduleTaskWithFixedDelay() {
    String now = dateTimeFormatter.format(LocalDateTime.now());
    LOGGER.info("Fixed Delay Task :: Execution Time - {}", now);
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException ex) {
      LOGGER.error("Ran into an error {}", ex.getMessage());
      Thread.currentThread().interrupt();
      throw new IllegalStateException(ex);
    }
  }
}
