package pbs.api.service.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pbs.api.config.StorageConfig;
import pbs.api.domain.diaries.DiaryEntity;
import pbs.api.domain.diaries.DiaryService;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExcelService {

  private final DiaryService diaryService;

  private XSSFWorkbook workbook;
  private XSSFSheet sheet;
  private XSSFCellStyle headerStyle;
  private List<LocalDate> dateArray = new ArrayList<>();

  public ExcelService(DiaryService diaryService) {
    this.diaryService = diaryService;
  }

  public void setWorkbook(XSSFWorkbook workbook) {
    this.workbook = workbook;
  }

  public void generate(LocalDate startDate, LocalDate endDate) {

    dateArray.clear();
    sheet = workbook.createSheet("Dzienniczek PBS");

    Row header = sheet.createRow(0);

    headerStyle = workbook.createCellStyle();
    Color color = new Color(220, 220, 220);
    headerStyle.setFillForegroundColor(new XSSFColor(color, null));
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    XSSFFont font = (workbook).createFont();
    font.setFontName("Arial");
    font.setFontHeightInPoints((short) 10);
    font.setBold(true);
    headerStyle.setFont(font);

    Cell headerCell;

    var i = 1;
    for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
      headerCell = header.createCell(i);
      headerCell.setCellValue(date.toString());
      headerCell.setCellStyle(headerStyle);
      sheet.autoSizeColumn(i);
      dateArray.add(date);
      i++;
    }

    CellStyle style = workbook.createCellStyle();
    style.setWrapText(true);
  }

  public void insertData(LocalDate startDate, LocalDate endDate) {

    var x = 0;
    var y = 0;
    var i = 0;
    var max = 0;
    var min = 0;
    String userName = "";

    List<DiaryEntity> diaries = diaryService.findByDates(startDate, endDate);

    for (DiaryEntity diary : diaries) {

      if (!diary.getDiaryAuthor().getUserName().equals(userName) && i != 0) {
        x = 0;
        y = min = max;
      }
      userName = diary.getDiaryAuthor().getUserName();

      while (!diary.getDiaryDate().isEqual(dateArray.get(x))) {
        x++;
        y = min;
      }
      if (sheet.getRow(y + 1) == null) {
        sheet.createRow(y + 1);
      }

      sheet.getRow(y + 1).createCell(x + 1).setCellValue(diary.getDiaryType());
      if (sheet.getRow(y + 1).getCell(0) == null) {
        Cell userCell = sheet.getRow(y + 1).createCell(0);
        userCell.setCellValue(userName);
        userCell.setCellStyle(headerStyle);
      }
      y++;
      if (y > max) {
        max = y;
      }
      i++;
    }
  }

  public void saveExcel(XSSFWorkbook savedWorkbook) throws IOException {

    String filename =
        "diary-" + new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss").format(new Date()) + ".xlsx";
    File currDir = new File(StorageConfig.getLocation());
    if (!currDir.exists() && !currDir.mkdirs()) {
      throw new IOException("There was a problem creating a folder!");
    }
    String path = currDir.getAbsolutePath();
    String fileLocation = path + "/" + filename;

    FileOutputStream outputStream = new FileOutputStream(fileLocation);

    savedWorkbook.write(outputStream);
    //    savedWorkbook.close();
  }
}
