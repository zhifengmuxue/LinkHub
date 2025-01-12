package top.zfmx.linkhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zfmx.linkhub.entity.pojo.UrlMapping;
import top.zfmx.linkhub.mapper.UrlMappingMapper;
import top.zfmx.linkhub.service.ShortenService;

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
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortCode(shortCode);
        urlMappingMapper.insert(urlMapping);
        return shortCode;
    }

    // 查询原始链接
    @Override
    public String getOriginalUrl(String shortCode) {
        QueryWrapper<UrlMapping> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("short_code", shortCode);
        UrlMapping urlMapping = urlMappingMapper.selectOne(queryWrapper);
        return urlMapping != null ? urlMapping.getOriginalUrl() : null;
    }

    // 生成唯一的短码
    private String generateUniqueShortCode() {
        Random random = new Random();
        StringBuilder shortCode = new StringBuilder();
        while (true) {
            for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
                shortCode.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
            }
            QueryWrapper<UrlMapping> queryWrapper = new QueryWrapper<>();
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
