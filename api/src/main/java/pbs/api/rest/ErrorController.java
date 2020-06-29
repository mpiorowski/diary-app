package pbs.api.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;

@RestController
@RequestMapping("/api/error")
public class ErrorController {
  private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

  @PostMapping
  public ResponseEntity<Void> saveErrorToLog(@RequestBody ClientErrorDto clientErrorDto) {

    String now = Instant.now().toString();

    String str = now + ": " + "\r\n" + clientErrorDto.getStack() + "\r\n";
    String fileName = "logs/ui.log";
    try (FileOutputStream oFile = new FileOutputStream(fileName, true)) {
      byte[] strToBytes = str.getBytes();
      oFile.write(strToBytes);
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
    }

    return null;
  }
}
