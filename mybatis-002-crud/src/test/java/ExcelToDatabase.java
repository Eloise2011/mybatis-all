import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * @author Joshua.H.Brooks
 * @description
 * @date 2024-05-15 09:15
 */
public class ExcelToDatabase {

    public static void main(String[] args) {
        String excelFilePath = "/Users/JoshuaBrooks/IdeaProjects/Joshs/Learning/mybatis-all/mybatis-002-crud/src/main/resources/iicc_inf_api_resource_info-bk190829.xlsx";
        String dbUrl = "jdbc:mysql://localhost:3306/iicc_inf_ss?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull";
        String dbUsername = "root";
        String dbPassword = "MyNewPassword";
        String tableName = "iicc_inf_api_resource_info";
        String pattern = "yyyy-MM-dd HH:mm";
        // Excel 中日期的起始时间（1900年1月1日）
        //Date baseDate = new Date(-2209075200000L);

        Date baseDate = new Date(-2209190400000L);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            File file = new File(excelFilePath);
            if(file.exists()) {
                System.out.println("File fetched successfully, size in KB: "+file.length()/1024);
            }else {
                System.out.println("No file found in Josh's mac");
            }
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0); // Assuming the first sheet
            System.out.println(sheet.getSheetName());
            String insertSql = generateInsertSql(tableName, sheet.getRow(0)); // Generate SQL insert statement
            System.out.println(insertSql);
            PreparedStatement pstmt = conn.prepareStatement(insertSql);
            int lastRowNum = sheet.getLastRowNum();
            System.out.println("lastRowNum = " + lastRowNum);

            for (int rowIndex = 1; rowIndex <= lastRowNum; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                System.out.println("rowIndex ~~~~~~~~~~~~~~= " + rowIndex);
                for (int colIndex = 0; colIndex < 18; colIndex++) {
                    Cell cell = row.getCell(colIndex);
                    if(colIndex == 11 || colIndex ==13 || colIndex ==16){ //create_time, update_time, trash_time
                        //System.out.println("==============================================================="+cell.getNumericCellValue());
                        if(cell==null){
                            pstmt.setObject(colIndex + 1, null);
                            continue;
                        }
                        // 计算偏移毫秒数
                        System.out.println("cell.getColumnIndex() = " + cell.getColumnIndex());
                        Object excelSerialDate = getValueFromCell(cell);
                        System.out.println("excelSerialDate = " + excelSerialDate);
                        long offsetMillis = (long) (Double.valueOf(String.valueOf(excelSerialDate)) * 24 * 60 * 60 * 1000);
                        // 创建对应的日期对象
                        Date date = new Date(baseDate.getTime() + offsetMillis);
                        String formattedDate = df.format(date);
                        System.out.println("formattedDate = " + formattedDate);
                        pstmt.setObject(colIndex + 1, formattedDate);
                    }else {
                        pstmt.setObject(colIndex + 1, getValueFromCell(cell));
                    }

                }
                pstmt.addBatch(); // Add to batch for bulk insertion
            }

            pstmt.executeBatch(); // Execute batch insertion
            pstmt.close();
            workbook.close();
            fis.close();

            System.out.println("Data inserted successfully!");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static String generateInsertSql(String tableName, Row headerRow) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(tableName).append(" (");
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(headerRow.getCell(i).getStringCellValue());
        }
        sb.append(") VALUES (");
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append("?");
        }
        sb.append(")");
        return sb.toString();
    }

    private static Object getValueFromCell(Cell cell) {
        if(cell==null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return cell.getNumericCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }
}
