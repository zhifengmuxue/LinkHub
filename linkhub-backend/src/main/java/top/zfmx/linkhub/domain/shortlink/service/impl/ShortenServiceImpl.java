package top.zfmx.linkhub.domain.shortlink.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zfmx.linkhub.domain.shortlink.entity.ShortUrl;
import top.zfmx.linkhub.domain.shortlink.service.ShortenService;
import top.zfmx.linkhub.infrastructure.mapper.UrlMappingMapper;

import java.util.Random;

@Service
public class ShortenServiceImpl implements ShortenService {

    private final UrlMappingMapper urlMappingMapper;
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_CODE_LENGTH = 6;
    private static final int MAX_RECORDS = 20; // 最大记录数

    @Autowired
    public ShortenServiceImpl(UrlMappingMapper urlMappingMapper) {
        this.urlMappingMapper = urlMappingMapper;
    }

    // 生成短链接
    @Override
    public String generateShortUrl(String originalUrl) {
        checkAndCleanRecords(); // 检查并清理记录
        String shortCode = generateUniqueShortCode();
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(originalUrl);
        shortUrl.setShortCode(shortCode);
        urlMappingMapper.insert(shortUrl);
        return shortCode;
    }

    // 查询原始链接
    @Override
    public String getOriginalUrl(String shortCode) {
        QueryWrapper<ShortUrl> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("short_code", shortCode);
        ShortUrl shortUrl = urlMappingMapper.selectOne(queryWrapper);
        return shortUrl != null ? shortUrl.getOriginalUrl() : null;
    }

    // 生成唯一的短码
    private String generateUniqueShortCode() {
        Random random = new Random();
        StringBuilder shortCode = new StringBuilder();
        while (true) {
            for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
                shortCode.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
            }
            QueryWrapper<ShortUrl> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("short_code", shortCode.toString());
            if (urlMappingMapper.selectCount(queryWrapper) == 0) {
                break;
            }
            shortCode.setLength(0); // 清空重新生成
        }
        return shortCode.toString();
    }

    // 检查并清理记录
    private void checkAndCleanRecords() {
        long count = urlMappingMapper.selectCount(null);
        if (count >= MAX_RECORDS) {
            // 删除最早的记录
            urlMappingMapper.deleteOldestRecords((int) (count - MAX_RECORDS + 1));
        }
    }
}
