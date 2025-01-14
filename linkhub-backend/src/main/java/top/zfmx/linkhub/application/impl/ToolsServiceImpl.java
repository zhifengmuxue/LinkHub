package top.zfmx.linkhub.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zfmx.linkhub.application.ToolsService;
import top.zfmx.linkhub.domain.shortlink.service.ShortenService;

@Service
public class ToolsServiceImpl implements ToolsService {

    @Autowired
    private ShortenService shortenService;

    @Override
    public String generateShortUrl(String originalUrl) {
        return shortenService.generateShortUrl(originalUrl);
    }

    @Override
    public String getOriginalUrl(String shortCode) {
        return shortenService.getOriginalUrl(shortCode);
    }


}
