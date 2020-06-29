package pbs.api.rest.test;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pbs.api.config.StorageConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/test/excel")
public class ExcelTestController {

  private static final Logger logger = LoggerFactory.getLogger(ExcelTestController.class);

//  @GetMapping()
//  public void excel(HttpServletRequest request, HttpServletResponse response) throws IOException {
//    Workbook workbook = new XSSFWorkbook();
//
//    Sheet sheet = workbook.createSheet("Persons");
//    sheet.setColumnWidth(0, 6000);
//    sheet.setColumnWidth(1, 4000);
//
//    Row header = sheet.createRow(0);
//
//    CellStyle headerStyle = workbook.createCellStyle();
//    headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
//    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
//    XSSFFont font = ((XSSFWorkbook) workbook).createFont();
//    font.setFontName("Arial");
//    font.setFontHeightInPoints((short) 16);
//    font.setBold(true);
//    headerStyle.setFont(font);
//
//    Cell headerCell = header.createCell(0);
//    headerCell.setCellValue("Name");
//    headerCell.setCellStyle(headerStyle);
//
//    headerCell = header.createCell(1);
//    headerCell.setCellValue("Age");
//    headerCell.setCellStyle(headerStyle);
//
//    CellStyle style = workbook.createCellStyle();
//    style.setWrapText(true);
//
//    Row row = sheet.createRow(2);
//    Cell cell = row.createCell(0);
//    cell.setCellValue("John Smith");
//    cell.setCellStyle(style);
//
//    cell = row.createCell(1);
//    cell.setCellValue(20);
//    cell.setCellStyle(style);
//
//    File currDir = new File(StorageConfig.getLocation());
//    currDir.mkdirs();
//    String path = currDir.getAbsolutePath();
//    logger.info(path);
//    String fileLocation = path + "/temp.xlsx";
//
//    FileOutputStream outputStream = new FileOutputStream(fileLocation);
//    workbook.write(outputStream);
//    workbook.close();
//
//    Path file = Paths.get(fileLocation);
//
//    if (Files.exists(file)) {
//      response.setContentType("application/pdf");
//      response.addHeader("Content-Disposition", "attachment; filename=temp.xlsx");
//      try {
//        Files.copy(file, response.getOutputStream());
//        response.getOutputStream().flush();
//      } catch (IOException ex) {
//        ex.printStackTrace();
//      }
//    }
//  }
}
