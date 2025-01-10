package top.zfmx.linkhub.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

@Data
public class Site implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @TableId
    private Long id;
    private String name;
    private String url;
    private String icon;
    private String description;
    private Long categoryId;
}
