package top.zfmx.linkhub.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("url_mapping")
public class UrlMapping {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String originalUrl;
    private String shortCode;
    private LocalDateTime createdAt = LocalDateTime.now();
}
