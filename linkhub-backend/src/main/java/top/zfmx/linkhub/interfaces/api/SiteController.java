package top.zfmx.linkhub.interfaces.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zfmx.linkhub.application.MainService;
import top.zfmx.linkhub.interfaces.dto.ApiResponse;
import top.zfmx.linkhub.interfaces.dto.SiteDTO;


@RestController
public class SiteController {

    @Resource
    private MainService mainService;

    @GetMapping("/api/sites")
    public ApiResponse<Page<SiteDTO>> getSitesByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<SiteDTO> sitePage = mainService.getRecords(page, size, null);
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
        Page<SiteDTO> sitePage = mainService.getRecords(page, size, categoryName);
        if (sitePage.getRecords().isEmpty()) {
            return ApiResponse.fail(400, "No data found");
        } else {
            return ApiResponse.success(sitePage);
        }
    }
}
