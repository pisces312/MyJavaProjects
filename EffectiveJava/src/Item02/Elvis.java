package 第2条私有构造函数强化单子模式;

// Singleton with final field - page 10
public class Elvis {
    public static final Elvis INSTANCE = new Elvis();

    private Elvis() {
        // ...
    }

    // ...  // Remainder omitted

    public static void main(String[] args) {
        System.out.println(Elvis.INSTANCE);
    }
}
