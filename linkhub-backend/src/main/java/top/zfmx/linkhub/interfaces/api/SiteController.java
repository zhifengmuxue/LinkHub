package top.zfmx.linkhub.interfaces.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zfmx.linkhub.application.LinkhubService;
import top.zfmx.linkhub.interfaces.dto.ApiResponse;
import top.zfmx.linkhub.interfaces.dto.SiteDTO;


@RestController
public class SiteController {
    private final LinkhubService linkhubService;

    @Autowired
    public SiteController(LinkhubService linkhubService) {
        this.linkhubService = linkhubService;
    }

    @GetMapping("/api/sites")
    public ApiResponse<Page<SiteDTO>> getSitesByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<SiteDTO> sitePage = linkhubService.getRecords(page, size, null);
        if (sitePage.getRecords().isEmpty()) {
            return ApiResponse.fail(400, "No data found");
        } else {
            return ApiResponse.success(sitePage);
        }
    }

    @GetMapping("/sites")
    public ApiResponse<Page<SiteDTO>> getSitesByCategoryByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String categoryName) {
        Page<SiteDTO> sitePage = linkhubService.getRecords(page, size, categoryName);
        if (sitePage.getRecords().isEmpty()) {
            return ApiResponse.fail(400, "No data found");
        } else {
            return ApiResponse.success(sitePage);
        }
    }
}
