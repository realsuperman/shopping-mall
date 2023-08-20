package com.bit.shoppingmall.global;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

public class FileUploadServlet extends HttpServlet {

    private static final FirebaseApp firebaseApp;

    static {
        try {
            InputStream serviceAccount = FileUploadServlet.class.getResourceAsStream("/serviceAccountKey.json");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("shoppingmall-c6950.appspot.com")
                    .build();
            firebaseApp = FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
                upload.setSizeMax(1 * 1024 * 1024); // 1MB 파일 크기 제한

                List<FileItem> items = upload.parseRequest(request);
                for (FileItem item : items) {
                    if (!item.isFormField()) {
                        // Get file bytes from the FileItem
                        byte[] fileBytes = item.get();

                        // Determine MIME type based on the file content (you might need to adjust this logic)
                        String mimeType = item.getContentType();

                        // Generate a unique filename
                        String uniqueFileName = System.currentTimeMillis() + "_" + item.getName();

                        // Upload file to Firebase Storage
                        StorageClient storageClient = StorageClient.getInstance();
                        String remotePath = uniqueFileName; // Adjust this path as needed
                        storageClient.bucket().create(remotePath, fileBytes, mimeType);

                        String downloadUrl = generateDownloadUrl(remotePath);

                        response.getWriter().write(downloadUrl);
                    }
                }
            } catch (FileUploadException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String generateDownloadUrl(String remotePath) {
        return remotePath;
    }
}