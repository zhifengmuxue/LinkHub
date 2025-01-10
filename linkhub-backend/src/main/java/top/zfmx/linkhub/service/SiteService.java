package top.zfmx.linkhub.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.zfmx.linkhub.entity.pojo.Site;
import top.zfmx.linkhub.entity.vo.SiteVO;

public interface SiteService extends IService<Site> {
    Page<SiteVO> getSitesWithCategory(Page<SiteVO> page);
}
