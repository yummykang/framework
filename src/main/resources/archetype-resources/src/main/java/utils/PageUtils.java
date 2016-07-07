package ${groupId}.utils;

import ${groupId}.bean.base.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/5/26 15:49
 */
public class PageUtils {

    /**
     * 获取Pageable实例
     *
     * @param t
     * @param sort
     * @param <T>
     * @return
     */
    public static <T extends Page>Pageable getPage(T t, Sort sort) {
        int page = t.getPageNo();
        int size = t.getPageSize();
        Pageable pageable = new PageRequest(page - 1, size, sort);
        return pageable;
    }
}
