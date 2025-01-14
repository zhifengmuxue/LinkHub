package top.zfmx.linkhub.domain.sites.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class Category implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @TableId
    private Long id;
    private String name;
    private String description;
}
