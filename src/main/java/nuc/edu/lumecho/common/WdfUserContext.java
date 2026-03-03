package nuc.edu.lumecho.common;

public class WdfUserContext {
    private static final ThreadLocal<Integer> USER_ID = new ThreadLocal<>();
    public static void setCurrentUserId(Integer userId) {
        USER_ID.set(userId);
    }

    public static Integer getCurrentUserId() {
        return USER_ID.get();
    }

    public static void clear() {
        USER_ID.remove();
    }
}
