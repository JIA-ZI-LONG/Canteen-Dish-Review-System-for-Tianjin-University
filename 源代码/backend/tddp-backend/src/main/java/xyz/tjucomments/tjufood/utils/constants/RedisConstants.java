package xyz.tjucomments.tjufood.utils.constants;

/**
 * @author Gemini
 * @description Redis相关的Key前缀和TTL常量
 * @create 2025-06-16 14:15
 */
public class RedisConstants {

    // ================== 登录与认证 (Login & Auth) ==================

    /**
     * 登录/注册/密码重置 邮箱验证码的Key前缀
     */
    public static final String LOGIN_CODE_KEY = "login:code:";
    public static final Long LOGIN_CODE_TTL = 5L; // 单位: 分钟


    /**
     * 用户登录Token的Key前缀
     */
    public static final String LOGIN_USER_KEY = "login:token:";
    public static final Long LOGIN_USER_TTL = 30L; // 单位: 分钟

    /**
     * 管理员登录Token的Key前缀
     */
    public static final String LOGIN_ADMIN_KEY = "login:admin:token:";
    public static final Long LOGIN_ADMIN_TTL = 120L; // 管理员登录有效期延长至2小时


    // ================== 业务数据缓存 (Business Cache) ==================

    /**
     * 缓存空对象的TTL
     */
    public static final Long CACHE_NULL_TTL = 2L; // 单位: 分钟

    /**
     * 食堂信息缓存的Key前缀
     */
    public static final String CACHE_CANTEEN_KEY = "cache:canteen:";
    public static final Long CACHE_CANTEEN_TTL = 30L; // 单位: 分钟


    // --- 新增常量 ---
    /**
     * 用户签到记录的Key前缀
     */
    public static final String USER_SIGN_KEY = "sign:";
    /**
     * 博客点赞记录的Key前缀
     */
    public static final String BLOG_LIKED_KEY = "blog:liked:";
    /**
     * 博客收藏记录的Key前缀
     */
    public static final String BLOG_FAVORITES_KEY = "blog:favorites:";
    public static final long LOCK_TTL = 30L;
}
