package com.keith.test.boottest.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 *  Excel工具类
 * @author kenliu
 * @date 2019/4/8 10:55
 */
public class ExcelUtil {

    /**
     * Excel 扩展名 xls 2003
     */
    private static final String EXCEL_2003_EXT = ".xls";

    /**
     * Excel 扩展名 xlsx 2007~
     */
    private static final String EXCEL_2007_EXT = ".xlsx";

    /**
     * excel最大列数
     */
    private static final int MAX_CELL = 100;

    /**
     * Excel数字
     */
    private static Pattern PATTERN_NUMBER = Pattern.compile(".0$");

    /**
     * excel 列 map
     */
    private static Map<Integer, String> EXCEL_CELL_MAP = new HashMap<>(MAX_CELL);

    static {
        // 初始化excel列 map
        String[] tip = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        int size = tip.length;
        for(int i = 0; i < MAX_CELL; i++) {
            StringBuilder sb = new StringBuilder();
            if(i == 0) {
                sb.append(tip[0]);
            } else {
                int first = i / size;
                int last = i % size;
                if(first > 0) {
                    sb.append(tip[first - 1]);
                    if(last == 0) {
                        sb.append(tip[0]);
                    }
                }
                if(last > 0) {
                    sb.append(tip[last]);
                }
            }
            EXCEL_CELL_MAP.put(i, sb.toString());
        }
    }

    /**
     * 读取Excel
     * @param filePath
     * @return
     */
    public static Workbook readExcel(String filePath){
        // 参数判断
        if(null == filePath || "".equals(filePath.trim())){
            return null;
        }
        // 获取文件扩展名
        String extString = filePath.substring(filePath.lastIndexOf("."));
        try (InputStream excelIs = new BufferedInputStream(new URL(filePath).openStream())) {
            // 文件流处理
            //InputStream excelIs = new FileInputStream(filePath);
            if(EXCEL_2003_EXT.equals(extString)) {
                return new HSSFWorkbook(excelIs);
            } else if(EXCEL_2007_EXT.equals(extString)) {
                return new XSSFWorkbook(excelIs);
            } else {
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取cell值
     * @param cell
     * @return
     */
    public static String getCellFormatValue(Cell cell){
        if (cell != null) {
            if (cell.getCellType() == CellType.NUMERIC) {
                return getRealStringValueOfDouble(cell.getNumericCellValue());
            }

            cell.setCellType(CellType.STRING);
            return cell.getStringCellValue().trim();
        }
        return "";
    }

    /**
     * 解决浮点数精度问题和科学计数读法问题
     * @param d
     * @return
     */
    private static String getRealStringValueOfDouble(Double d) {
        String doubleStr = d.toString();
        boolean b = doubleStr.contains("E");
        int indexOfPoint = doubleStr.indexOf('.');
        if (b) {
            int indexOfE = doubleStr.indexOf('E');
            BigInteger xs = new BigInteger(doubleStr.substring(indexOfPoint
                    + BigInteger.ONE.intValue(), indexOfE));
            int pow = Integer.valueOf(doubleStr.substring(indexOfE
                    + BigInteger.ONE.intValue()));
            int xsLen = xs.toByteArray().length;
            int scale = xsLen - pow > 0 ? xsLen - pow : 0;
            doubleStr = String.format("%." + scale + "f", d);
        } else {
            Matcher m = PATTERN_NUMBER.matcher(doubleStr);
            if (m.find()) {
                doubleStr = doubleStr.replace(".0", "");
            } else {
                DecimalFormat df = new DecimalFormat("#.###");
                doubleStr = df.format(d);
            }
        }
        return doubleStr;
    }

}
