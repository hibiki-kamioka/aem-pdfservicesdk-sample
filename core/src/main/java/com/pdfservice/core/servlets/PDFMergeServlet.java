package com.pdfservice.core.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;

import com.adobe.pdfservices.operation.ExecutionContext;
import com.adobe.pdfservices.operation.auth.Credentials;
import com.adobe.pdfservices.operation.io.FileRef;
import com.adobe.pdfservices.operation.pdfops.DocumentMergeOperation;
import com.adobe.pdfservices.operation.pdfops.DocumentMergeOperation.SupportedSourceFormat;
import com.adobe.pdfservices.operation.pdfops.options.documentmerge.DocumentMergeOptions;
import com.adobe.pdfservices.operation.pdfops.options.documentmerge.OutputFormat;

@Component(service = { Servlet.class })
@SlingServletPaths("/bin/pdfmerge")
public class PDFMergeServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException, IOException {
        final Resource resource = req.getResource();
        resp.setContentType("application/pdf");
        try {

            File credentailsJson = new File("pdfservices-api-credentials.json");
            String credentailsJsonPath = credentailsJson.getAbsolutePath();
            logger.info("### credentailsJsonPath ### : " + credentailsJsonPath);
            
            Credentials credentials = Credentials.serviceAccountCredentialsBuilder()
                .fromFile(credentailsJsonPath)
                .build();

            JSONObject jsonDataForMerge = new JSONObject("{\"Author\": \"Adobe, Inc.\",\"Report_Date\": \"16/08/2022\"}");
            DocumentMergeOptions documentMergeOptions = new DocumentMergeOptions(jsonDataForMerge, OutputFormat.PDF);
            DocumentMergeOperation docMergeOperation = DocumentMergeOperation.createNew(documentMergeOptions);
            InputStream template = getClass().getResourceAsStream("/Template.docx");
            docMergeOperation.setInput(FileRef.createFromStream(template, SupportedSourceFormat.DOCX.getMediaType()));

            FileRef result = docMergeOperation.execute(ExecutionContext.create(credentials));
            result.saveAs(resp.getOutputStream());

        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}
