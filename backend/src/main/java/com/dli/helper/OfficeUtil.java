package com.dli.helper;


import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
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

    public void word2html(String wordPath, String htmlPath)
            throws Exception {

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
            InputStream input = new FileInputStream(wordFile);
            HWPFDocument wordDocument = new HWPFDocument(input);

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
}
