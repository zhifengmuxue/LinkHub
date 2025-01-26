package top.zfmx.linkhub.interfaces.api;

import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.zfmx.linkhub.application.LinkService;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/link")
public class ShortLinkController {

    @Resource
    private LinkService linkService;

    // 生成短链接
    @PostMapping("/generate")
    public ResponseEntity<?> shortenUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("url");
        if (originalUrl == null || originalUrl.isEmpty()) {
            return ResponseEntity.badRequest().body("URL is required");
        }

        String shortUrl = linkService.generateShortUrl(originalUrl);
        return ResponseEntity.ok(Map.of("shortCode", shortUrl));
    }

    // 重定向到原始链接
    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirect(@PathVariable String shortCode) {
        String originalUrl = linkService.getOriginalUrl(shortCode);
        if (originalUrl == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
