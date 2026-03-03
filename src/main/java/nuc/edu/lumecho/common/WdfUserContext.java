package nuc.edu.lumecho.common;

public class WdfUserContext {
    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();
    public static void setCurrentUserId(Long userId) {
        USER_ID.set(userId);
    }

    public static Long getCurrentUserId() {
        return USER_ID.get();
    }

    public static void clear() {
        USER_ID.remove();
    }
}
