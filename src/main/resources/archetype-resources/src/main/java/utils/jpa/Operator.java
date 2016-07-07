package ${groupId}.utils.jpa;

/**
 * JpaHelper操作名枚举
 *
 * @author demon
 */
public enum Operator {
    /** 同数据库中== */
    EQ,
    /** 同数据库中！= */
    NE,
    /** 同数据库中like */
    LIKE,
    /** 同数据库中> */
    GT,
    /** 同数据库中< */
    LT,
    /** 同数据库中>= */
    GTE,
    /** 同数据库中<= */
    LTE,
    /** 同数据库中between */
    BT,
    /** 同数据库中的in */
    IN
}
