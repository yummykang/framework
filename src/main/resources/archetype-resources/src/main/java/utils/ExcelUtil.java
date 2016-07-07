package ${groupId}.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.util.*;

/**
 * 导出Excel文档工具类
 * Allen
 */
public class ExcelUtil {

    /**
     * 创建excel文档，
     *
     * @param list        数据
     * @param keys        list中map的key数组集合
     * @param columnNames excel的列名
     */
    public static Workbook createWorkBook(List<Map<String, Object>> list, String[] keys, String columnNames[]) {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for (int i = 0; i < keys.length; i++) {
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        //设置列名
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(getCs1(wb));
        }
        //设置每行每列的值
        for (short i = 1; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow((short) i);
            // 在row行上创建一个方格
            for (short j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
                cell.setCellStyle(getCs2(wb));
            }
        }
        return wb;
    }

    public static Workbook createSheet(Workbook wb, String[] keys, String columnNames[], List<Object[]> list, String sheetName) {
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(sheetName);
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for (int i = 0; i < keys.length; i++) {
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        //设置列名
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(getCs1(wb));
        }
        //设置每行每列的值
        for (short i = 0; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow((short) i + 1);
            // 在row行上创建一个方格
            for (short j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                cell.setCellValue(list.get(i)[j] == null ? " " : list.get(i)[j].toString());
                cell.setCellStyle(getCs2(wb));
            }
        }
        return wb;
    }

    /**
     * 获取第一种cellstyle
     *
     * @param wb
     * @return
     */
    public static CellStyle getCs1(Workbook wb) {
        CellStyle cs = wb.createCellStyle();
        Font f = wb.createFont();
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);
        cs.setFont(f);
        cs.setAlignment(CellStyle.ALIGN_CENTER);
        return cs;
    }

    /**
     * 获取第二种cellstyle
     *
     * @param wb
     * @return
     */
    public static CellStyle getCs2(Workbook wb) {
        CellStyle cs2 = wb.createCellStyle();
        Font f2 = wb.createFont();
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());
        cs2.setFont(f2);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        return cs2;
    }

    /**
     * 创建sheet
     *
     * @param wb Workbook实体
     * @param sheetName sheet名称
     * @param columnNames 列名
     * @param dataList 数据列表
     */
    public static void createSheet(Workbook wb, String sheetName, String[] columnNames, List<Object[]> dataList) {
        Sheet sheet = wb.createSheet(sheetName);
        sheet.autoSizeColumn(1, true);
        Row row = sheet.createRow(0);
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
        }
        for (int i = 0; i < dataList.size(); i++) {
            Object[] data = dataList.get(i);
            Row rowI = sheet.createRow(i + 1);
            for (int j = 0; j < data.length; j++) {
                Cell cellI_J = rowI.createCell(j);
                if (data[j] == null) {
                    data[j] = "-";
                }
                cellI_J.setCellValue(data[j].toString());

            }
        }
    }

    /**
     * 创建sheet，数据类型是List<T>
     *
     * @param wb
     * @param sheetName
     * @param colMap
     * @param dataList
     * @param <T>
     */
    public static <T>void newSheet(Workbook wb, String sheetName, Map<String, String> colMap, List<T> dataList) {
        Sheet sheet = wb.createSheet(sheetName);
        sheet.autoSizeColumn(1, true);
        Row row = sheet.createRow(0);
        Set<String> colNames = colMap.keySet();
        Collection<String> cols = colMap.values();
        int i = 0;
        Iterator<String> iterator = colNames.iterator();
        while (iterator.hasNext()) {
            Cell cell = row.createCell(i);
            cell.setCellValue(iterator.next());
            i++;
        }
        for (int j = 0; j < dataList.size(); j++) {
            Row rowI = sheet.createRow(j + 1);
            T data = dataList.get(j);
            Iterator<String> ite = cols.iterator();
            int k = 0;
            while (ite.hasNext()) {
                Cell cellI_J = rowI.createCell(k);
                Object value = BeanUtil.invokeGet(data.getClass(), data, ite.next());
                if (value == null) {
                    cellI_J.setCellValue("-");
                } else {
                    cellI_J.setCellValue(value.toString());
                }
                k++;
            }
        }
    }

}