package top.zfmx.linkhub.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zfmx.linkhub.service.SiteService;
import top.zfmx.linkhub.entity.vo.SiteVO;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SiteController {
    private final SiteService siteService;

    @Autowired
    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }

    @GetMapping("/api/sites")
    public Map<String, Object> getSitesWithCategoryByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> response = new HashMap<>();
        Page<SiteVO> sitePage = siteService.getPage(new Page<>(page, size));
        if (sitePage.getRecords().isEmpty()) {
            response.put("code", "400");
            response.put("message", "No data found");
        } else {
            response.put("code", "200");
            response.put("message", "Success");
            response.put("total", sitePage.getTotal());
            response.put("data", sitePage.getRecords());
        }
        return response;
    }

    @GetMapping("/sites")
    public Map<String, Object> getSitesByCategoryByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String categoryName) {
        Map<String, Object> response = new HashMap<>();
        Page<SiteVO> sitePage;
        if (categoryName == null || categoryName.isEmpty()) {
            sitePage = siteService.getPage(new Page<>(page, size));
        } else {
            sitePage = siteService.getByCategoryName(new Page<>(page, size), categoryName);
        }
        if (sitePage.getRecords().isEmpty()) {
            response.put("code", "400");
            response.put("message", "No data found");
        } else {
            response.put("code", "200");
            response.put("message", "Success");
            response.put("total", sitePage.getTotal());
            response.put("data", sitePage.getRecords());
        }
        return response;
    }
}
