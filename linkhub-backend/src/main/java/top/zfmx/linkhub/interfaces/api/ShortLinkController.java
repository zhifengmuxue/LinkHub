package top.zfmx.linkhub.interfaces.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.zfmx.linkhub.application.ToolsService;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/link")
public class ShortLinkController {

    private final ToolsService toolsService;
    @Autowired
    public ShortLinkController(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    // 生成短链接
    @PostMapping("/generate")
    public ResponseEntity<?> shortenUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("url");
        if (originalUrl == null || originalUrl.isEmpty()) {
            return ResponseEntity.badRequest().body("URL is required");
        }

        String shortUrl = toolsService.generateShortUrl(originalUrl);
        return ResponseEntity.ok(Map.of("shortCode", shortUrl));
    }

    // 重定向到原始链接
    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirect(@PathVariable String shortCode) {
        String originalUrl = toolsService.getOriginalUrl(shortCode);
        if (originalUrl == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
