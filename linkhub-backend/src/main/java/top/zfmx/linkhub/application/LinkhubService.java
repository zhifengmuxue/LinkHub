package top.zfmx.linkhub.application;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.zfmx.linkhub.interfaces.dto.SiteDTO;

public interface LinkhubService {
    Page<SiteDTO> getByCategoryName(Page<SiteDTO> page, String categoryName);
    Page<SiteDTO> getPage(Page<SiteDTO> page);
    Page<SiteDTO> getRecords(int page, int size, String categoryName);
}
