package top.zfmx.linkhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.zfmx.linkhub.entity.pojo.Site;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.zfmx.linkhub.entity.vo.SiteVO;

import java.util.List;

@Mapper
public interface SiteMapper
        extends BaseMapper<Site> {

    @Select("SELECT s.name, s.url, s.icon, s.description, c.name AS categoryName " +
            "FROM site s LEFT JOIN category c ON s.category_id = c.id")
    List<SiteVO> selectSitesWithCategory(Page<?> page);
}
