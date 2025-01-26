package top.zfmx.linkhub.infrastructure.external;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import top.zfmx.linkhub.domain.sites.entity.Site;
import top.zfmx.linkhub.domain.sites.service.SiteService;
import top.zfmx.linkhub.infrastructure.mapper.SiteMapper;
import top.zfmx.linkhub.interfaces.dto.SiteDTO;

@Component
public class SiteServiceImpl
        extends ServiceImpl<SiteMapper, Site>
        implements SiteService {

    @Override
    public Page<SiteDTO> getPage(Page<SiteDTO> page) {
        return page.setRecords(this.baseMapper.selectByPage(page));
    }

    @Override
    public Page<Site> getByCategory(Page<Site> page, Long categoryId) {
        return page.setRecords(this.baseMapper.selectByCategoryId(page,categoryId));
    }

    @Override
    public Page<SiteDTO> getByCategoryName(Page<SiteDTO> page, String categoryName) {
        return page.setRecords(this.baseMapper.selectByCategoryName(page,categoryName));
    }

}
