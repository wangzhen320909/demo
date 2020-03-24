package ${packageStr};

import com.yl.base.exception.BusinessException;
import com.yl.base.util.ToolUtils;
import com.yl.model.base.enums.ExceptionEnum;
import org.springframework.beans.BeanUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yl.base.service.impl.BaseServiceImpl;
import com.yl.base.model.WebReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ${entityPackage}.${entityName};
import ${daoType};
import ${serviceType};
import ${voType};
import java.util.Date;

/**
 * ${entityDesc}service服务类
 *
 * @author ${author}
 * @created ${time}
 * @version 1.0
 * @since 1.8
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ${entityName}ServiceImpl extends BaseServiceImpl<${entityName}Mapper,${entityName}> implements ${entityName}Service {

    /**
     * 分页查询
     *
     * @param pojo 条件参数封装信息
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @return 分页信息
     */
    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public WebReturn<${entityName}> findByPage(${entityName} pojo, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<${entityName}> page = (Page<${entityName}>)selectList(pojo);
        return new WebReturn<>(new PageInfo<>(page));
    }

    /**
     * 通过id查询指定信息
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public ${entityName} findById(Long id) {
        return selectById(id);
    }

    /**
     * 通过对象查询指定信息
     * @param pojo
     * @return
     */
    @Override
    public ${entityName} findOneByEntity(${entityName} pojo) {
        return selectOne(pojo);
    }

    /**
     * 新增单体数据
     * @param pojo 新增数据实体
     * @return
     */
    @Override
    public ${entityName} create(${entityName} pojo) {
        pojo.setCreateTime(new Date());
        insert(pojo);
        return pojo;
    }

    /**
     * 更新数据
     *
     * @param pojo 待更新数据
     * @return
     */
    @Override
    public ${entityName} update(${entityName} pojo) throws BusinessException{
        if (null == pojo || null == pojo.getId() || pojo.getId().intValue() == 0) {
            throw new BusinessException(ExceptionEnum.UPDATE_ID_NULL_ERROR);
        }
        ${entityName} pojoOld = selectById(pojo.getId());
        if (null == pojoOld) {
            throw new BusinessException(ExceptionEnum.UPDATE_ERROR, String.valueOf(pojo.getId()));
        }
        BeanUtils.copyProperties(pojo, pojoOld, ToolUtils.getNullPropertyNames(pojo));
        updateById(pojoOld);
        return pojoOld;
    }

    /**
     * 通过id删除数据
     *
     * @param id
     * @return
     */
    @Override
    public WebReturn<${entityName}> delete(Long id) {
        deleteById(id);
        return new WebReturn<>(id);
    }
    /**
     * 校验属性
     *
     * @param pojo
     * @return
     */
    private WebReturn<${entityName}> checkProp(${entityName} pojo){
        return null;
    }
}