package ${groupId}.utils;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/6/8 15:23
 */
public class StringUtils {
    /**
     * 判断是否包含数组中的字符串
     *
     * @param source
     * @param urls
     * @return
     */
    public static boolean contains(String source, String[] urls) {
        for (String url : urls) {
            if (source.contains(url)) {
                return true;
            }
        }

        return false;
    }
}
