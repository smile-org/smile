package com.dli.helper;


import org.apache.commons.io.FileUtils;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.*;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;


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

        } else if (wordFileSuffix.equalsIgnoreCase("docx")) {

            XWPFDocument document = new XWPFDocument(new FileInputStream(wordPath));
            XHTMLOptions options = XHTMLOptions.create();
            options.setExtractor(new FileImageExtractor(new File(imagePath)));
            options.URIResolver(new BasicURIResolver(wordFileName));
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(htmlPath + htmlName), "utf-8");
            XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
            xhtmlConverter.convert(document, outputStreamWriter, options);

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

            FileInputStream is = new FileInputStream(pptFile);
            SlideShow slideShow = new HSLFSlideShow(is);
            is.close();

            Dimension pgsize = slideShow.getPageSize();
            java.util.List<HSLFSlide> slides = slideShow.getSlides();

            String imghtml = "";

            int idx = 1;
            for (HSLFSlide slide : slides) {
                //这几个循环只要是设置字体为宋体，防止中文乱码，
                java.util.List<java.util.List<HSLFTextParagraph>> oneTextParagraphs = slide.getTextParagraphs();
                for (java.util.List<HSLFTextParagraph> list : oneTextParagraphs) {
                    for (HSLFTextParagraph hslfTextParagraph : list) {
                        java.util.List<HSLFTextRun> HSLFTextRunList = hslfTextParagraph.getTextRuns();
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

        } else if (pptFileSuffix.equalsIgnoreCase("pptx")) {
            FileInputStream is = new FileInputStream(pptFile);
            XMLSlideShow slideShow = new XMLSlideShow(is);
            is.close();

            Dimension pgsize = slideShow.getPageSize();
            java.util.List<XSLFSlide> slides = slideShow.getSlides();

            String imghtml = "";
            int idx = 1;
            for (XSLFSlide slide : slides) {
                java.util.List<XSLFShape> shapes = slide.getShapes();
                for(XSLFShape shape : shapes){
                    if(shape instanceof XSLFTextShape) {
                        XSLFTextShape tsh = (XSLFTextShape)shape;
                        for(XSLFTextParagraph p : tsh){
                            for(XSLFTextRun r : p){
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

        } else {
            throw new Exception("Invalid document type.");
        }
    }
}
