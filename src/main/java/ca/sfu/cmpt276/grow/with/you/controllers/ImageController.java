package ca.sfu.cmpt276.grow.with.you.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ca.sfu.cmpt276.grow.with.you.services.ImageUploadService;

class JsonResponse {
    private String url;

    public JsonResponse(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

@RestController
public class ImageController {
    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping("/file/messages/upload")
    public ResponseEntity<JsonResponse> uploadImage(@RequestParam("file") MultipartFile multipartFile) {
        String url = imageUploadService.upload(multipartFile);
        JsonResponse res = new JsonResponse(url);
        return ResponseEntity.ok(res);
    }
}
