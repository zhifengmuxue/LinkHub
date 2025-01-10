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
    public Page<SiteVO> getSitesWithCategory(Page<SiteVO> page) {
        return page.setRecords(this.baseMapper.selectSitesWithCategory(page));
    }
}
