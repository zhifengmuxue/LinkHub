package top.zfmx.linkhub.domain.sites.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.zfmx.linkhub.domain.sites.entity.Site;
import top.zfmx.linkhub.interfaces.dto.SiteDTO;

public interface SiteService extends IService<Site> {
    Page<SiteDTO> getPage(Page<SiteDTO> page);
    Page<Site> getByCategory(Page<Site> page, Long categoryId);
    Page<SiteDTO> getByCategoryName(Page<SiteDTO> page, String categoryName);
}
