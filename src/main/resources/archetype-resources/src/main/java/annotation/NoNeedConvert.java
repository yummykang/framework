package ${groupId}.annotation;

import java.lang.annotation.*;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/6/22 11:13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface NoNeedConvert {
}
