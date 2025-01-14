package top.zfmx.linkhub.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.zfmx.linkhub.domain.sites.entity.Site;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.zfmx.linkhub.interfaces.dto.SiteDTO;

import java.util.List;

@Mapper
public interface SiteMapper
        extends BaseMapper<Site> {

    @Select("SELECT s.name, s.url, s.icon, s.description, c.name AS categoryName " +
            "FROM site s LEFT JOIN category c ON s.category_id = c.id")
    List<SiteDTO> selectByPage(Page<?> page);

    @Select("SELECT s.name, s.url, s.icon, s.description, c.id AS categoryId " +
            "FROM site s LEFT JOIN category c ON s.category_id = c.id " +
            "WHERE s.category_id = #{categoryId}")
    List<Site> selectByCategoryId(Page<?> page, Long categoryId);

    @Select("SELECT s.name, s.url, s.icon, s.description, c.name AS categoryName " +
            "FROM site s LEFT JOIN category c ON s.category_id = c.id " +
            "WHERE c.name = #{categoryName}")
    List<SiteDTO> selectByCategoryName(Page<?> page, String categoryName);
}
