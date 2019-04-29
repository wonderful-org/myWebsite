package com.aviator.mywebsite;

/**
 * @Description TODO
 * @ClassName TestUtils
 * @Author aviator_ls
 * @Date 2019/4/29 19:58
 */
public abstract class TestUtils {

    public static void test(Class<? extends Testable> testableType) {
        Testable testable = null;
        try {
            testable = testableType.newInstance();
            testable.before();
            testable.doTest();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (testable != null) {
                testable.after();
            }
        }
    }
}
