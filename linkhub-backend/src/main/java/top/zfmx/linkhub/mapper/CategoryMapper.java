package top.zfmx.linkhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.zfmx.linkhub.entity.pojo.Category;

@Mapper
public interface CategoryMapper
        extends BaseMapper<Category> {
}
