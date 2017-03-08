package 第2条私有构造函数强化单子模式.staticfactory;
/**
 * 提供灵活性，可以使子类不使用单子模式，使得子类可以被修改
 * 
 * @author DELL
 */
// Singleton with static factory
public class Elvis {

    private static final Elvis INSTANCE = new Elvis();

    private Elvis() {
        // ...
    }

    /**
     * 使用公有方法
     * @return
     */
    public static Elvis getInstance() {
        return INSTANCE;
    }

    // ...  // Remainder omitted
    public static void main(String[] args) {
        System.out.println(Elvis.INSTANCE);
    }
}
