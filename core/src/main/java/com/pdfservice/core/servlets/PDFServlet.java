package com.pdfservice.core.servlets;

import java.io.IOException;
import java.net.URL;
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
import org.osgi.service.component.annotations.Component;

import com.adobe.pdfservices.operation.ExecutionContext;
import com.adobe.pdfservices.operation.auth.Credentials;
import com.adobe.pdfservices.operation.io.FileRef;
import com.adobe.pdfservices.operation.pdfops.CreatePDFOperation;
import com.adobe.pdfservices.operation.pdfops.options.createpdf.CreatePDFOptions;
import com.adobe.pdfservices.operation.pdfops.options.createpdf.PageLayout;

@Component(service = { Servlet.class })
@SlingServletPaths("/bin/pdftest")
public class PDFServlet extends SlingSafeMethodsServlet {

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

            ExecutionContext executionContext = ExecutionContext.create(credentials);
            CreatePDFOperation htmlToPDFOperation = CreatePDFOperation.createNew();
            htmlToPDFOperation.setInput(FileRef.createFromURL(new URL("https://experienceleague.adobe.com/?lang=en#home")));
            CreatePDFOptions htmlToPdfOptions = CreatePDFOptions.htmlOptionsBuilder().includeHeaderFooter(true).build();
            htmlToPDFOperation.setOptions(htmlToPdfOptions);
            setCustomOptions(htmlToPDFOperation);
            FileRef result = htmlToPDFOperation.execute(executionContext);
            result.saveAs(resp.getOutputStream());

        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private static void setCustomOptions(CreatePDFOperation htmlToPDFOperation) {
        // Define the page layout, in this case an 8 x 11.5 inch page (effectively
        // portrait orientation).
        PageLayout pageLayout = new PageLayout();
        pageLayout.setPageSize(20, 25);

        // Set the desired HTML-to-PDF conversion options.
        CreatePDFOptions htmlToPdfOptions = CreatePDFOptions.htmlOptionsBuilder()
                .includeHeaderFooter(true)
                .withPageLayout(pageLayout)
                .build();
        htmlToPDFOperation.setOptions(htmlToPdfOptions);
    }
}
