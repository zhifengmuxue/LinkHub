package top.zfmx.linkhub.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.zfmx.linkhub.entity.pojo.Category;
import top.zfmx.linkhub.mapper.CategoryMapper;
import top.zfmx.linkhub.service.CategoryService;

@Service
public class CategoryServiceImpl
        extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {
}
