package top.zfmx.linkhub.application;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zfmx.linkhub.domain.sites.service.SiteService;
import top.zfmx.linkhub.interfaces.dto.SiteDTO;

@Service
public class MainService {

    private final SiteService siteService;

    @Autowired
    public MainService(SiteService siteService) {
        this.siteService = siteService;
    }

    public Page<SiteDTO> getByCategoryName(Page<SiteDTO> page, String categoryName) {
        return siteService.getByCategoryName(page, categoryName);
    }

    public Page<SiteDTO> getPage(Page<SiteDTO> page) {
        return siteService.getPage(page);
    }

    public Page<SiteDTO> getRecords(int page, int size, String categoryName) {
        Page<SiteDTO> sitePage;
        if (categoryName == null || categoryName.isEmpty()) {
            sitePage = getPage(new Page<>(page, size));
        } else {
            sitePage = getByCategoryName(new Page<>(page, size), categoryName);
        }
        return sitePage;
    }
}
