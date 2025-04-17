package com.example.ijgapis.Controllers;

import com.example.ijgapis.Models.Document;
import com.example.ijgapis.Services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "http://localhost:3000")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<Document> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("categoryId") String categoryId) throws IOException {
        return ResponseEntity.ok(documentService.uploadDocument(file, title, description, categoryId));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Document>> getDocumentsByCategory(@PathVariable String categoryId) {
        return ResponseEntity.ok(documentService.getDocumentsByCategory(categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        return documentService.getDocumentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id) throws IOException {
        Document document = documentService.getDocumentById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        Path path = Paths.get(document.getFileUrl());
        Resource resource = documentService.loadFileAsResource(path.toString());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path.getFileName() + "\"")
                .body(resource);
    }

    // New endpoint to serve files by name

    // View document in the browser
    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewDocument(@PathVariable String fileName) throws MalformedURLException {
        Resource resource = documentService.loadFileAsResource(fileName);

        // Determine the Content-Type based on the file extension
        String contentType = null;
        try {
            contentType = Files.probeContentType(resource.getFile().toPath());
        } catch (IOException e) {
            contentType = "application/octet-stream"; // Fallback content type
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    //update and delete endpoint
    // Update endpoint
    @PutMapping("/update/{id}")
    public ResponseEntity<Document> updateDocument(
            @PathVariable Long id,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam(value = "categoryId", required = false) String categoryId) throws IOException {
        Document updatedDocument = documentService.updateDocument(id, file, title, description, categoryId);
        return ResponseEntity.ok(updatedDocument);
    }

    // Delete endpoint
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}