package ${groupId}.utils.jpa;

import ${groupId}.utils.BeanUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/6/22 9:52
 */
public class JpaUtils {

    /**
     * 替换sql中的?
     *
     * @param sql
     * @param params
     * @return
     */
    public static String getRealSql(String sql, Object[] params) {
        StringBuffer result = new StringBuffer();
        Pattern pattern = Pattern.compile("\\?");
        Matcher matcher = pattern.matcher(sql);
        int i = 0;
        while (matcher.find()) {
            String matchString = matcher.group();
            matchString.replace("?", "");
            Object value = params[i];
            if (value instanceof String) {
                matcher.appendReplacement(result, "'" + value.toString() + "'");
            } else {
                matcher.appendReplacement(result, value.toString());
            }
            i++;
        }
        return matcher.appendTail(result).toString();
    }

    /**
     * 执行sql，获取执行结果（List<Object[]>）
     *
     * @param sql
     * @param params
     * @param em
     * @return
     */
    public static Object excuteSql(String sql, Object[] params, EntityManager em) {
        Query query = em.createNativeQuery(getRealSql(sql, params));
        return query.getResultList();
    }

    /**
     * 执行sql, 获取一条执行结果（Object[]）
     *
     * @param sql
     * @param params
     * @param em
     * @return
     */
    public static Object queryOne(String sql, Object[] params, EntityManager em) {
        Query query = em.createNativeQuery(getRealSql(sql, params));
        return query.getSingleResult();
    }

    /**
     * 执行sql，获取条执行结果（Class<T>）
     *
     * @param sql
     * @param params
     * @param em
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T queryOne(String sql, Object[] params, EntityManager em, Class<T> clazz) {
        return BeanUtil.objArrToBean((Object[]) queryOne(sql, params, em), clazz);
    }

    /**
     * 执行sql，获取执行结果（List<Class<T>）
     *
     * @param sql
     * @param params
     * @param em
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> excuteSql(String sql, Object[] params, EntityManager em, Class<T> clazz) {
        List<Object[]> objList = (List<Object[]>) excuteSql(sql, params, em);
        return BeanUtil.listObjToListBean(objList, clazz);
    }
}
