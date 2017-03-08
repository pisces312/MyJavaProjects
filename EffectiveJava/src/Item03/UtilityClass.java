package 第3条通过私有构造函数强化不可实例化能力;

// Noninstantiable utility class - page 12
public class UtilityClass {

    // Suppress default constructor for noninstantiability
    private UtilityClass() {
        // This constructor will never be invoked
    }

    // ...  // Remainder omitted
}
