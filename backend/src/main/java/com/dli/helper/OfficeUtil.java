package com.dli.helper;


import org.apache.commons.io.FileUtils;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xslf.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;


public class OfficeUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private OfficeUtil() {
    }

    private static OfficeUtil officeUtil;

    public static synchronized OfficeUtil getInstance() {
        if (officeUtil == null) {
            officeUtil = new OfficeUtil();
        }
        return officeUtil;
    }

    public void word2html(String wordPath, String htmlPath) throws Exception {

        File wordFile = new File(wordPath);
        String wordFileName = wordFile.getName().substring(0, wordFile.getName().lastIndexOf("."));
        logger.info(wordFileName);
        String wordFileSuffix = wordFile.getName().substring(wordFile.getName().lastIndexOf(".") + 1);
        logger.info(wordFileSuffix);

        String htmlName = wordFileName + ".html";
        if (!htmlPath.endsWith(File.separator)) {
            htmlPath += File.separator;
        }
        File htmlFile = new File(htmlPath + htmlName);
        String imagePath = htmlPath + wordFileName + File.separator;

        if (wordFileSuffix.equalsIgnoreCase("doc")) {
            doc2html(htmlPath, wordFile, wordFileName, htmlFile, imagePath);

        } else if (wordFileSuffix.equalsIgnoreCase("docx")) {
            docx2html(wordPath, htmlPath, wordFileName, htmlName, imagePath);
        } else {
            throw new Exception("Invalid document type.");
        }
    }


    public void ppt2html(String pptPath, String htmlPath) throws Exception {
        File pptFile = new File(pptPath);
        String pptFileName = pptFile.getName().substring(0, pptFile.getName().lastIndexOf("."));
        logger.info(pptFileName);
        String pptFileSuffix = pptFile.getName().substring(pptFile.getName().lastIndexOf(".") + 1);
        logger.info(pptFileSuffix);

        String htmlName = pptFileName + ".html";
        if (!htmlPath.endsWith(File.separator)) {
            htmlPath += File.separator;
        }
        File htmlFile = new File(htmlPath + htmlName);
        String imagePath = htmlPath + pptFileName + File.separator;

        File imgPath = new File(imagePath);
        if (!imgPath.exists()) {
            imgPath.mkdirs();
        }

        if (pptFileSuffix.equalsIgnoreCase("ppt")) {
            ppt2html(pptFile, pptFileName, htmlFile, imagePath);
        } else if (pptFileSuffix.equalsIgnoreCase("pptx")) {
            pptx2html(pptFile, pptFileName, htmlFile, imagePath);
        } else {
            throw new Exception("Invalid document type.");
        }
    }

    public void export2excel(String sheetName, List<String> rowNameList, List<Object[]> dataList, String excelPath)
            throws Exception {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);
        FileOutputStream outputStream = new FileOutputStream(excelPath);

        int rowIndex = 0;
        XSSFRow row = sheet.createRow(rowIndex);
        int columnIndex = 0;
        for (String rowName : rowNameList) {
            XSSFCell cell = row.createCell(columnIndex, Cell.CELL_TYPE_STRING);

            // 设置字体
            XSSFFont font = workbook.createFont();
            //设置字体大小
            font.setFontHeightInPoints((short) 11);
            //字体加粗
            font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
            //设置字体名字
            font.setFontName("宋体");
            //设置样式;
            XSSFCellStyle style = workbook.createCellStyle();
            //设置底边框;
            style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);
            //在样式用应用设置的字体;
            style.setFont(font);
            //设置自动换行;
            style.setWrapText(false);
            //设置水平对齐的样式为居中对齐;
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //设置垂直对齐的样式为居中对齐;
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

            cell.setCellStyle(style);

            cell.setCellValue(rowName);
            columnIndex++;
        }
        rowIndex += 1;

        columnIndex = 0;
        for (Object[] dataArray : dataList) {
            XSSFRow dataRow = sheet.createRow(rowIndex);
            for (Object data : dataArray) {
                XSSFCell cell = dataRow.createCell(columnIndex, Cell.CELL_TYPE_STRING);
                cell.setCellValue(data.toString());
                columnIndex += 1;
            }
            rowIndex += 1;
        }


        workbook.write(outputStream);
        outputStream.close();
    }

    private void doc2html(String htmlPath, File wordFile, String wordFileName, File htmlFile, String imagePath)
            throws IOException, ParserConfigurationException, TransformerException {
        HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(wordFile));
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        wordToHtmlConverter.setPicturesManager(
                (content, pictureType, suggestedName, widthInches, heightInches) -> {
                    File imgPath = new File(imagePath);
                    if (!imgPath.exists()) {
                        imgPath.mkdirs();
                    }
                    File file = new File(String.format("%s%s", imagePath, suggestedName));
                    try {
                        OutputStream os = new FileOutputStream(file);
                        os.write(content);
                        os.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return wordFileName + File.separator + suggestedName;
                });
        wordToHtmlConverter.processDocument(wordDocument);

        wordToHtmlConverter.processDocument(wordDocument);
        Document htmlDocument = wordToHtmlConverter.getDocument();

        File folder = new File(htmlPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        OutputStream outStream = new FileOutputStream(htmlFile);

        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer serializer = factory.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        outStream.close();
    }

    private void docx2html(String wordPath, String htmlPath, String wordFileName, String htmlName, String imagePath)
            throws IOException {
        XWPFDocument document = new XWPFDocument(new FileInputStream(wordPath));
        XHTMLOptions options = XHTMLOptions.create();
        options.setExtractor(new FileImageExtractor(new File(imagePath)));
        options.URIResolver(new BasicURIResolver(wordFileName));
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(htmlPath + htmlName), "utf-8");
        XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
        xhtmlConverter.convert(document, outputStreamWriter, options);
    }

    private void ppt2html(File pptFile, String pptFileName, File htmlFile, String imagePath)
            throws IOException {
        FileInputStream is = new FileInputStream(pptFile);
        SlideShow slideShow = new HSLFSlideShow(is);
        is.close();

        Dimension pgsize = slideShow.getPageSize();
        List<HSLFSlide> slides = slideShow.getSlides();

        String imghtml = "";

        int idx = 1;
        for (HSLFSlide slide : slides) {
            //这几个循环只要是设置字体为宋体，防止中文乱码，
            List<List<HSLFTextParagraph>> oneTextParagraphs = slide.getTextParagraphs();
            for (List<HSLFTextParagraph> list : oneTextParagraphs) {
                for (HSLFTextParagraph hslfTextParagraph : list) {
                    List<HSLFTextRun> HSLFTextRunList = hslfTextParagraph.getTextRuns();
                    for (HSLFTextRun textRun : HSLFTextRunList) {
                        /*
                          * 如果PPT在WPS中保存过，则 HSLFTextRunList.get(j).getFontSize();的值为0或者26040，
                          * 因此首先识别当前文本框内的字体尺寸是否为0或者大于26040，则设置默认的字体尺寸。
                          */
                        //设置字体大小
                        Double size = textRun.getFontSize();
                        if ((size <= 0) || (size >= 26040)) {
                            textRun.setFontSize(20.0);
                        }
                        //设置字体样式为宋体
                        textRun.setFontIndex(1);
                        textRun.setFontFamily("宋体");
                    }
                }
            }

            BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = img.createGraphics();
            // clear the drawing area
            graphics.setPaint(Color.white);
            graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

            // render
            slide.draw(graphics);

            // save the output
            FileOutputStream out = new FileOutputStream(imagePath + "slide-" + idx + ".png");
            javax.imageio.ImageIO.write(img, "png", out);
            out.close();

            imghtml += String.format("<p><img src=\"%s%s/slide-%d.png\" style=\"vertical-align:text-bottom;\" /></p>", pptFileName, File.separator, idx);

            idx++;
        }

        String ppthtml = "<html><head><META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body>" + imghtml + "</body></html>";
        FileUtils.writeStringToFile(htmlFile, ppthtml, "utf-8");
    }

    private void pptx2html(File pptFile, String pptFileName, File htmlFile, String imagePath)
            throws IOException {
        FileInputStream is = new FileInputStream(pptFile);
        XMLSlideShow slideShow = new XMLSlideShow(is);
        is.close();

        Dimension pgsize = slideShow.getPageSize();
        List<XSLFSlide> slides = slideShow.getSlides();

        String imghtml = "";
        int idx = 1;
        for (XSLFSlide slide : slides) {
            List<XSLFShape> shapes = slide.getShapes();
            for (XSLFShape shape : shapes) {
                if (shape instanceof XSLFTextShape) {
                    XSLFTextShape tsh = (XSLFTextShape) shape;
                    for (XSLFTextParagraph p : tsh) {
                        for (XSLFTextRun r : p) {
                            r.setFontFamily("宋体");
                        }
                    }
                }
            }
            BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = img.createGraphics();
            // clear the drawing area
            graphics.setPaint(Color.white);
            graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

            // render
            slide.draw(graphics);

            // save the output
            FileOutputStream out = new FileOutputStream(imagePath + "slide-" + idx + ".png");
            javax.imageio.ImageIO.write(img, "png", out);
            out.close();

            imghtml += String.format("<p><img src=\"%s%s/slide-%d.png\" style=\"vertical-align:text-bottom;\" /></p>", pptFileName, File.separator, idx);

            idx++;
        }
        String ppthtml = "<html><head><META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body>" + imghtml + "</body></html>";
        FileUtils.writeStringToFile(htmlFile, ppthtml, "utf-8");
    }
}
