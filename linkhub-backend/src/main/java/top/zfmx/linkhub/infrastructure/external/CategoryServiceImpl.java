package top.zfmx.linkhub.infrastructure.external;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import top.zfmx.linkhub.domain.sites.entity.Category;
import top.zfmx.linkhub.domain.sites.service.CategoryService;
import top.zfmx.linkhub.infrastructure.mapper.CategoryMapper;

@Component
public class CategoryServiceImpl
        extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {
}
