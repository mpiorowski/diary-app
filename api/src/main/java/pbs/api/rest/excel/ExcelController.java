package pbs.api.rest.excel;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pbs.api.service.excel.ExcelService;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/test/excel")
public class ExcelController {

  private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);

  private final ExcelService excelService;

  public ExcelController(ExcelService excelService) {
    this.excelService = excelService;
  }

  @GetMapping("/download")
  public ResponseEntity<InputStreamResource> downloadExcel(ExcelRequestDto requestDto) {

    try {
      XSSFWorkbook workbook = new XSSFWorkbook();
      excelService.setWorkbook(workbook);
      excelService.generate(requestDto.getStartDate(), requestDto.getEndDate());
      excelService.insertData(requestDto.getStartDate(), requestDto.getEndDate());

      ByteArrayOutputStream out = new ByteArrayOutputStream();
      workbook.write(out);
      ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
      String filename = "dzienniczek.xlsx";

      excelService.saveExcel(workbook);

      workbook.close();

      HttpHeaders headers = new HttpHeaders();
      headers.add(
          "Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
      headers.add("Content-Disposition", "attachment; filename=" + filename);
      return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
    } catch (IOException e) {
      logger.error("excel download", e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
