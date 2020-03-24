package ${packageStr};

import com.yl.base.exception.BusinessException;
import ${entityPackage}.${entityName};
import com.github.pagehelper.PageInfo;
import com.yl.base.service.BaseService;
import com.yl.base.model.WebReturn;

/**
 * ${entityDesc}服务service
 *
 * @author ${author}
 * @version 1.0
 * @created ${time}
 * @since 1.8
 */
public interface ${className} extends BaseService<${entityName}>{

    /**
     * 分页查询
     * @param pojo
     * @param pageNum
     * @param pageSize
     * @return
     */
    WebReturn<${entityName}> findByPage(${entityName} pojo, Integer pageNum, Integer pageSize);

    /**
     * 通过id查询指定信息
     * @param id
     * @return
     */
    ${entityName} findById(Long id);

    /**
     * 通过对象查询指定信息
     * @param pojo
     * @return
     */
    ${entityName} findOneByEntity(${entityName} pojo);

    /**
     * 新增单体数据
     * @param pojo
     * @return
     */
    ${entityName} create(${entityName} pojo);

    /**
     * 更新数据
     * @param pojo
     * @return
     */
    ${entityName} update(${entityName} pojo) throws BusinessException;

    /**
     * 通过id删除数据
     * @param id
     * @return
     */
    WebReturn<${entityName}> delete(Long id);
}