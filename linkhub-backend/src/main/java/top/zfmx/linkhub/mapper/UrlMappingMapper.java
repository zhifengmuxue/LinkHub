package top.zfmx.linkhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.zfmx.linkhub.entity.pojo.UrlMapping;

import java.util.List;

@Mapper
public interface UrlMappingMapper extends BaseMapper<UrlMapping> {

    @Select("SELECT * FROM url_mapping ORDER BY created_at LIMIT #{limit}")
    List<UrlMapping> findOldestRecords(@Param("limit") int limit);

    @Delete("DELETE url_mapping FROM url_mapping JOIN (SELECT id FROM url_mapping ORDER BY created_at LIMIT #{limit}) AS temp ON url_mapping.id = temp.id")
    void deleteOldestRecords(@Param("limit") int limit);
}
