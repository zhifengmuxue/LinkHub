package top.zfmx.linkhub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.zfmx.linkhub.service.ShortenService;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/short")
public class ShortController {
    @Autowired
    private ShortenService shortenService;

    // 生成短链接
    @PostMapping("/")
    public ResponseEntity<?> shortenUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("url");
//        System.out.println("originalUrl: " + originalUrl);
        if (originalUrl == null || originalUrl.isEmpty()) {
            return ResponseEntity.badRequest().body("URL is required");
        }

        String shortUrl = shortenService.generateShortUrl(originalUrl);
        return ResponseEntity.ok(Map.of("shortCode", shortUrl));
    }

    // 重定向到原始链接
    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirect(@PathVariable String shortCode) {
        String originalUrl = shortenService.getOriginalUrl(shortCode);
        if (originalUrl == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
