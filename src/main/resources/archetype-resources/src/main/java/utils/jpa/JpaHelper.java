package ${groupId}.utils.jpa;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * specification help用于简化spring data jpa动态查询
 *
 * @author demon
 */
public class JpaHelper<T> {

    /**
     * 获取jpa api所需要的specification
     *
     * @param fieldName 变量名，如果需要join其它的表，则变量名.其它表的值
     * @param operator  操作
     * @param value     变量值，如果是between(BT)，则value为List类型，并且size为2
     * @return 返回specification
     */
    public Specification<T> term(String fieldName, Operator operator, Object value) {
        return (root, criteriaQuery, builder) -> {
            // 判断是否为空，动态查询
            if (value == null || "".equals(value)) {
                return null;
            } else {
                if(value instanceof Integer) {
                    if ((Integer)value == -1) {
                        return null;
                    }
                }
            }
            Path expression = null;
            // 判断变量名是否是a.b的形式，如果是则要组装root构成join查询
            if (fieldName.contains(".")) {
                String[] names = StringUtils.split(fieldName, ".");
                expression = root.get(names[0]);
                for (int i = 1; i < names.length; i++) {
                    expression = expression.get(names[i]);
                }
            } else {
                expression = root.get(fieldName);
            }
            // 根据不同的operator调用CriteriaBuilder相应的方法
            switch (operator) {
                case EQ:
                    return builder.equal(expression, value);
                case NE:
                    return builder.notEqual(expression, value);
                case LT:
                    return builder.lessThan(expression, (Comparable) value);
                case GT:
                    return builder.greaterThan(expression, (Comparable) value);
                case LIKE:
                    return builder.like((Expression<String>) expression.as(String.class), "%" + value + "%");
                case LTE:
                    return builder.lessThanOrEqualTo(expression, (Comparable) value);
                case GTE:
                    return builder.greaterThanOrEqualTo(expression, (Comparable) value);
                case BT:
                    List<Object> paramList = (List<Object>) value;
                    if (paramList.size() == 2) {
                        return builder.between(expression, (Comparable) paramList.get(0), (Comparable) paramList.get(1));
                    }
                case IN:
                    return builder.in(expression).value(value);
                default:
                    return null;
            }
        };
    }

    /**
     * 进一步封装equal，方便调用者使用
     *
     * @param field 参数名
     * @param value 参数值
     * @return 返回specification
     */
    public Specification<T> eq(String field, Object value) {
        return term(field, Operator.EQ, value);
    }

    /**
     * 进一步封装not equal，方便调用者使用
     *
     * @param field 参数名
     * @param value 参数值
     * @return 返回specification
     */
    public Specification<T> ne(String field, Object value) {
        return term(field, Operator.NE, value);
    }


    /**
     * 进一步封装like，方便调用者使用
     *
     * @param field 参数名
     * @param value 参数值
     * @return 返回specification
     */
    public Specification<T> like(String field, Object value) {
        return term(field, Operator.LIKE, value);
    }


    /**
     * 进一步封装great than，方便调用者使用
     *
     * @param field 参数名
     * @param value 参数值
     * @return 返回specification
     */
    public Specification<T> gt(String field, Object value) {
        return term(field, Operator.GT, value);
    }


    /**
     * 进一步封装less than，方便调用者使用
     *
     * @param field 参数名
     * @param value 参数值
     * @return 返回specification
     */
    public Specification<T> lt(String field, Object value) {
        return term(field, Operator.LT, value);
    }

    /**
     * 进一步封装great than and equal，方便调用者使用
     *
     * @param field 参数名
     * @param value 参数值
     * @return 返回specification
     */
    public Specification<T> gte(String field, Object value) {
        return term(field, Operator.GTE, value);
    }

    /**
     * 进一步封装less than and equal，方便调用者使用
     *
     * @param field 参数名
     * @param value 参数值
     * @return 返回specification
     */
    public Specification<T> lte(String field, Object value) {
        return term(field, Operator.LTE, value);
    }

    /**
     * 进一步封装between，方便调用者使用
     *
     * @param field 参数名
     * @param value 参数值
     * @return 返回specification
     */
    public Specification<T> bt(String field, Object value) {
        return term(field, Operator.BT, value);
    }

    /**
     * 进一步封闭in, 方便使用都使用
     *
     * @param field
     * @param value
     * @return
     */
    public Specification<T> in(String field, Object value) {
        return term(field, Operator.IN, value);
    }
}
