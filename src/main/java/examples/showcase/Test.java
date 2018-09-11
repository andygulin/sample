package examples.showcase;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        for (int i = 1; i <= 6; i++)
            new Test().readExcel(String.valueOf(i));
    }

    private void readExcel(String sheetName) throws Exception {
        Workbook wb = Workbook.getWorkbook(new File("D:\\内科.xls"));
        Sheet sheet = wb.getSheet(sheetName);
        List<Data> datas = new ArrayList<>();
        for (int i = 1; i < sheet.getRows(); i++) {
            Data data = new Data();
            for (int j = 0; j < sheet.getColumns(); j++) {
                Cell cell = sheet.getCell(j, i);
                int column = cell.getColumn();
                if (column == 0) data.name = cell.getContents();
                if (column == 1) data.hospital = cell.getContents();
                if (column == 2) data.sex = cell.getContents();
                if (column == 3) data.specialty = cell.getContents();
                if (column == 4) data.birthday = cell.getContents();
                if (column == 5) data.age = cell.getContents();
                if (column == 6) data.phone = cell.getContents();
                if (column == 7) data.email = cell.getContents();
                if (column == 8) data.bigArea = cell.getContents();
                if (column == 9) data.province = cell.getContents();
                if (column == 10) data.city = cell.getContents();
                if (column == 11) data.area = cell.getContents();
            }
            datas.add(data);
        }
        wb.close();

        for (Data data : datas) {
            String sql = "INSERT INTO hys_data VALUES(NULL,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');";
            String str = String.format(sql, data.name, data.hospital, data.sex, data.specialty, data.birthday, data.age, data.phone, data.email, data.bigArea, data.province, data.city, data.area);
            str += IOUtils.LINE_SEPARATOR;
            String fileName = String.format("D:/hys-data-%s.sql", sheetName);
            FileUtils.writeStringToFile(new File(fileName), str, "UTF-8", true);
        }
    }
}

class Data {
    String name;
    String hospital;
    String sex;
    String specialty;
    String birthday;
    String age;
    String phone;
    String email;
    String bigArea;
    String province;
    String city;
    String area;
}