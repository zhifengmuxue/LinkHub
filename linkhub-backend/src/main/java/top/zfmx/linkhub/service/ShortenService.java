package top.zfmx.linkhub.service;


public interface ShortenService {
    String generateShortUrl(String originalUrl);    // 生成短链接
    String getOriginalUrl(String shortCode);        // 查询原始链接
}
