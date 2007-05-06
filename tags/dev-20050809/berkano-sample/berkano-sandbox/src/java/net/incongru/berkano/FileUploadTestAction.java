package net.incongru.berkano;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.multipart.MultiPartRequestWrapper;
import com.opensymphony.xwork.ActionSupport;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision:  $
 */
public class FileUploadTestAction extends ActionSupport {
    private String foo;

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    public String execute() {
        /*
        MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) ServletActionContext.getRequest();

        if (multiWrapper.hasErrors()) {
            Collection errors = multiWrapper.getErrors();
            Iterator i = errors.iterator();
            while (i.hasNext()) {
                addActionError((String) i.next());
            }
            return ERROR;
        }

        String[] fileNames = multiWrapper.getFileNames("file");
        for (int i = 0; i < fileNames.length; i++) {
            String fileName = fileNames[i];
            String contentType = multiWrapper.getContentTypes("file")[i];
            File file = multiWrapper.getFiles("file")[i];

            // If it's null the upload failed
            if (file == null) {
                addActionError("Error uploading: " + multiWrapper.getFileSystemNames("file")[i]);
                return ERROR;
            }

            // Do additional processing/logging...
            System.out.println("---------------");
            System.out.println("fileName = " + fileName);
            System.out.println("contentType = " + contentType);
            System.out.println("file = " + file);
            System.out.println("---------------");
        }*/
        setFoo("CHOUBIDOUWAH");
        return SUCCESS;
    }
}
