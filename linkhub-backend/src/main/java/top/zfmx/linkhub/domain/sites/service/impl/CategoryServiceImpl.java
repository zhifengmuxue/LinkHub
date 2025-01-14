package top.zfmx.linkhub.domain.sites.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.zfmx.linkhub.domain.sites.entity.Category;
import top.zfmx.linkhub.domain.sites.service.CategoryService;
import top.zfmx.linkhub.infrastructure.mapper.CategoryMapper;

@Service
public class CategoryServiceImpl
        extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {
}
