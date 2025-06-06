package website.project.website.interceptor;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.util.CollectionUtils;
import website.project.website.domain.dto.UserDTO;

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

    public static UserDTO user(){
        Map<String, String> map = getMap();
        if (CollectionUtils.isEmpty(map)){
            return UserDTO.builder().build();
        }
        String userDtoString = JSONObject.toJSONString(map);
        return JSONObject.parseObject(userDtoString, UserDTO.class);
    }

    public static void clear() {
        loginThreadLocal.remove();
    }

}
