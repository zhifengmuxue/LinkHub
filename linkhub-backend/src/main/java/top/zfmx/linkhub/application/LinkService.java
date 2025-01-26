package top.zfmx.linkhub.application;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.zfmx.linkhub.domain.shortlink.service.ShortenService;

@Service
public class LinkService {

    @Resource
    private ShortenService shortenService;

    public String generateShortUrl(String originalUrl) {
        return shortenService.generateShortUrl(originalUrl);
    }

    public String getOriginalUrl(String shortCode) {
        return shortenService.getOriginalUrl(shortCode);
    }


}
