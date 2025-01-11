package top.zfmx.linkhub.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.zfmx.linkhub.entity.pojo.Site;
import top.zfmx.linkhub.mapper.SiteMapper;
import top.zfmx.linkhub.service.SiteService;
import top.zfmx.linkhub.entity.vo.SiteVO;

@Service
public class SiteServiceImpl
        extends ServiceImpl<SiteMapper, Site>
        implements SiteService {

    @Override
    public Page<SiteVO> getPage(Page<SiteVO> page) {
        return page.setRecords(this.baseMapper.selectByPage(page));
    }

    @Override
    public Page<Site> getByCategory(Page<Site> page, Long categoryId) {
        return page.setRecords(this.baseMapper.selectByCategoryId(page,categoryId));
    }

    @Override
    public Page<SiteVO> getByCategoryName(Page<SiteVO> page, String categoryName) {
        return page.setRecords(this.baseMapper.selectByCategoryName(page,categoryName));
    }

}
