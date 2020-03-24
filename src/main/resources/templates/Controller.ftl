package ${packageStr};

import ${entityPackage}.${entityName};
import ${servicePackage}.${serviceName};
import ${voPackage}.${voClassName};
import ${dtoPackage}.${dtoClassName};
import com.github.pagehelper.PageInfo;
import com.yl.base.controller.BaseController;
import com.yl.base.model.WebReturn;
import com.yl.base.util.MyNoticeUtil;
import com.yl.model.annotation.ExcelField;
import com.yl.model.base.Constants;
import com.yl.model.base.enums.QueryType;
import com.yl.model.base.enums.TitleEnum;
import com.yl.utils.date.MyDateUtils;
import com.yl.utils.dict.DictUtil;
import com.yl.utils.excel.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ${entityDesc}控制层
 *
 * @author ${author}
 * @created ${time}
 * @version 1.0
 * @since 1.8
 */
@Slf4j
@Api(value = "${entityDesc}",tags = "${entityDesc}")
@RestController
@RequestMapping("/${requestMapping}")
public class ${className} extends BaseController<${entityName}Service,${entityName}>{

    @Autowired
    private ${serviceName} ${lowerServiceName};

    /**
     * 条件分页查询${entityDesc}
     *
     * @param pojo     条件信息
     * @param pageNum  页码
     * @param pageSize 页面大小
     * @return
     */
    @ApiOperation(value = "分页查询${entityDesc}", notes = "按条件分页查询${entityDesc}", httpMethod = "POST", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "path",required = true),
            @ApiImplicitParam(name = "pageSize", value = "页面大小", dataType = "int", paramType = "path",required = true)
    })
    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public WebReturn <${entityName}> findByPage(@Valid @RequestBody(required = false) ${entityName}Dto pojo, @PathVariable Integer pageNum, @PathVariable Integer pageSize) throws Exception {
        if(null==pojo){
            pojo = new ${entityName}Dto();
        }
        pojo.getQueryBaseDto().setQueryType(QueryType.PAGE);
        pojo.getQueryBaseDto().setPageNum(pageNum);
        pojo.getQueryBaseDto().setPageSize(pageSize);
        PageInfo pageInfo = this.page(pojo.getQueryBaseDto(), pojo.get${entityName}());
        if (null != pageInfo.getList() && pageInfo.getList().size() > 0) {
            List <${entityName}Vo> vos = DictUtil.getDictList(pageInfo.getList(),${entityName}Vo.class);
            pageInfo.setList(vos);
        }
        return new WebReturn <>(pageInfo);
    }

    /**
     * 通过id查询${entityDesc}
     *
     * @return
     */
    @ApiOperation(value = "通过id查询${entityDesc}", notes = "通过id查询${entityDesc}", httpMethod = "POST", produces = "application/json")
    @PostMapping("/findById/{id}")
    public WebReturn<${entityName}> findById(@PathVariable Long id) throws Exception {
        ${entityName} vo = ${lowerServiceName}.findById(id);
        return new WebReturn(vo);
    }

    /**
     * 新增${entityDesc}
     *
     * @param pojo
     * @return
     */
    @ApiOperation(value = "新增${entityDesc}数据", notes = "新增${entityDesc}数据", httpMethod = "POST", produces = "application/json")
    @PostMapping("/create")
    public WebReturn<${entityName}> create(@Valid @RequestBody ${entityName} pojo) throws Exception {
        ${lowerServiceName}.create(pojo);
        return MyNoticeUtil.getNoticeVo(TitleEnum.ADD_SUCCESS);
    }

    /**
     * 更新${entityDesc}
     *
     * @param pojo
     * @return
     */
    @ApiOperation(value = "更新${entityDesc}数据", notes = "更新${entityDesc}数据", httpMethod = "POST", produces = "application/json")
    @PostMapping("/update")
    public WebReturn<${entityName}> update(@RequestBody ${entityName} pojo) throws Exception {
        ${lowerServiceName}.update(pojo);
        return MyNoticeUtil.getNoticeVo(TitleEnum.UPDATE_SUCCESS);
    }

    /**
     * 根据id删除${entityDesc}
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除${entityDesc}", notes = "根据id删除${entityDesc}", httpMethod = "POST", produces = "application/json")
    @PostMapping("/delete/{id}")
    public WebReturn delete(@PathVariable Long id) {
        ${lowerServiceName}.delete(id);
        return MyNoticeUtil.getNoticeVo(TitleEnum.DELETE_SUCCESS);
    }



    /**
     * 导出
     * @param pojo
     * @throws Exception
     */
    @ApiOperation(value = "导出", notes = "导出", httpMethod = "POST", produces = "application/json")
    @PostMapping("/export")
    public void export(@Valid @RequestBody(required = false) ${entityName}Dto pojo) throws Exception {
        if (null == pojo) {
            pojo = new ${entityName}Dto();
        }
        pojo.getQueryBaseDto().setQueryType(QueryType.LIST);
        String fileName = "${entityName}" + MyDateUtils.DateToStr(new Date(), "yyyyMMddHHmm") + ".xls";
        List <${entityName}> list = this.list(pojo.getQueryBaseDto(), pojo.get${entityName}());
        ExcelUtil <${entityName}> util = new ExcelUtil <${entityName}>(${entityName}.class);
        util.exportExcel(list, "${entityName}", fileName, request, response);
    }


}