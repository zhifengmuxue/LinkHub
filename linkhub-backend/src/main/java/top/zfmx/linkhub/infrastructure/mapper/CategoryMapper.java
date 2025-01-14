package top.zfmx.linkhub.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.zfmx.linkhub.domain.sites.entity.Category;

@Mapper
public interface CategoryMapper
        extends BaseMapper<Category> {
}
