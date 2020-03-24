package ${packageStr};

import com.yl.model.annotation.ExcelField;
import com.yl.model.annotation.MyBatisPluginPlus;
import com.yl.model.annotation.MyEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * ${entityDesc}实体
 *
 * @author ${author}
 * @created ${time}
 * @version 1.0
 * @since 1.8
 */
@MyEntity(hasHistory = false)
@Table(name = "${tableName}")
@Data
public class ${className} implements Serializable {
    private static final long serialVersionUID = ${serialVersionNum};

${propertiesStr}
${methodStr}
}