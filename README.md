# aem-pdfservicesdk-sample
This is a sample for using the API provided by Adobe Document Services with AEM.  
[Adobe Document Services](https://developer.adobe.com/document-services/homepage)


This sample project helps you get started using the PDF Services Java SDK with AEM.  
Sample servlets demonstrates how to use the SDK to perform PDF-related actions, such as converting to and from the PDF format.  
Please note that the PDF Services Java SDK only supports server-side use cases.

## Prerequisites
The sample application has the following requirements:

Java JDK : Version 8 or above.  
Build Tool: The application requires Maven to be installed. Maven installation instructions can be found [here](https://maven.apache.org/install.html).  

### System Requirements
| Java JDK | Maven | AEM |
----|----|---- 
| 8, 11 | 3.3.9+ | AEMaaCS, 6.5.0+ | 

### Confirmed operating environment
| Java JDK | Maven | AEM | PDF Services SDK |
----|----|----|---- 
| 11 | 3.8.4 | AEM SDK (2022.7.8085) local | 2.2.2 |

  
## OSGi bundling non OSGi jars
The PDF Service SDK jar provided in the Maven Repository is not in a format that can be used in AEM as it is, so it needs to be OSGi bundled so that it can be used in AEM.  

[pdfservices-sdk-2.2.2.jar](https://mvnrepository.com/artifact/com.adobe.documentservices/pdfservices-sdk/2.2.2)  

  
Create a module directory to bundle pdfservices-sdk-2.2.2.jar from Central Manen Repository using maven-bundle-plugin.  
<img width="350" alt="image" src="https://user-images.githubusercontent.com/42370761/184114662-29548a0f-0a05-4218-8de0-85617821426c.png">  

Please refer to `bundles/pdfservices-sdk/pom.xml` for the setting contents.  
  
## Install & Setup
### Building
You can build and install everything on a running AEM instance by issuing the following commands in your project's top level folder:  
    
```mvn clean install -PautoInstallPackage```  

### Credentials
Credentials for using the API are obtained from the following.  
[Get Started with Document Services APIs](https://documentcloud.adobe.com/dc-integration-creation-app-cdn/main.html)  

*Notes*
- After logging in with your Adobe ID, select ***PDF SERVICES API***.  
- Once you have entered the required information, you can download the sample project as a zip file.    
- When you unzip the zip file, you will find the following two files (***pdfservices-api-credentials.json, private.key***).
- Place these files in the directory where you installed AEM (same hierarchy as ***crx-quickstart***).Please refer to the following image.    


<img width="350" alt="image" src="https://user-images.githubusercontent.com/42370761/184122221-0d3c54fe-0019-4bf2-9306-ab50300b6c13.png">
  

## Sample Servlet
The sample servlet is based on the source code provided in [pdfservices-java-sdk-samples](https://github.com/adobe/pdfservices-java-sdk-samples).  

*Notes*  
- Due to not tried all the sample source code, so after you'll add your own servlet, you will need to adjust `bundles/pdfservices-sdk/pom.xml` if missing bundles appear.  

### Converts an HTML file specified by a URL to a PDF file
#### Execution method
`http://localhost:4502/bin/pdftest`　　

*Notes*  
- It takes a while to display the pdf.  


Refrence : [CreatePDFFromURL](https://github.com/adobe/pdfservices-java-sdk-samples/blob/master/src/main/java/com/adobe/pdfservices/operation/samples/createpdf/CreatePDFFromURL.java)  


### Merges Word based document templates with input JSON data to generate output documents in PDF format
#### Execution method
`http://localhost:4502/bin/pdfmerge`
  
Refrence : [MergeDocumentToPDF](https://github.com/adobe/pdfservices-java-sdk-samples/blob/2b41463d2c141cc906387b336a14c77f1a724dd9/src/main/java/com/adobe/platform/operation/samples/documentmerge/MergeDocumentToPDF.java)  

