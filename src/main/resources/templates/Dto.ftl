package ${packageStr};

${entityImport}
import io.swagger.annotations.ApiModel;
import com.yl.model.base.dto.QueryBaseDto;
import lombok.Data;
import java.io.Serializable;

/**
 * ${entityDesc}dto
 *
 * @author ${author}
 * @version 1.0
 * @created ${time}
 * @since 1.8
 */
@ApiModel(value = "${className}", description = "${entityDesc}入参类")
@Data
public class ${className} implements Serializable {
    private static final long serialVersionUID = ${serialVersionNum};
    private QueryBaseDto queryBaseDto = new QueryBaseDto();
    private ${pojoClassName} ${lowerFirstPoClassName} = new ${pojoClassName}();
}