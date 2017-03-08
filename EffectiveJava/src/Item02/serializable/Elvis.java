package 第2条私有构造函数强化单子模式.serializable;

// Serialzable Singleton - Page 11
import java.io.*;
/**
 * 确信该类始终是单子模式，则使用该种方法
 * 通过类成员清楚表明该类采用单子模式
 * 性能上领先
 * @author DELL
 */
public class Elvis implements Serializable {
    //这里使用公有静态final域

    public static final Elvis INSTANCE = new Elvis();

    private Elvis() {
        // ...
    }

    // ...  // Remainder omitted
    /**
     * 保证反序列化返回唯一的实例
     * @return
     * @throws java.io.ObjectStreamException
     */
    // readResolve method to preserve singleton property
    private Object readResolve() throws ObjectStreamException {
        /*
         * Return the one true Elvis and let the garbage collector
         * take care of the Elvis impersonator.
         */
        return INSTANCE;
    }

    public static void main(String[] args) {
        System.out.println(Elvis.INSTANCE);
    }
}
