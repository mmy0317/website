package website.project.website.interceptor;

import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;


public class AuthNHolder {

    /**
     * 线程池隔离
     */
    private static final ThreadLocal<HashMap<String, String>> loginThreadLocal = new ThreadLocal();

    public static void set(HashMap<String, String> map) {
        loginThreadLocal.set(map);
    }

    public static String get(String key) {
        Map<String, String> map = getMap();
        return map.get(key);
    }

    public static Map<String, String> getMap() {
        HashMap<String, String> map = loginThreadLocal.get();
        if (CollectionUtils.isEmpty(map)){
            return new HashMap<>();
        }
        return map;
    }

    public static void clear() {
        loginThreadLocal.remove();
    }

}
