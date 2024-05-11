package com.erp.gameserver.test;

public class Test {
    public static void main(String[] args) {
        System.out.println(test());
    }

    private static boolean test() {
        return first() && second() ? getone() : getTwo();
    }

    private static boolean second() {
        System.out.println("second");
        return true;
    }

    private static boolean first() {
        return false;
    }

    private static boolean getTwo() {
        System.out.println("two");
        return false;
    }

    private static boolean getone() {
        System.out.println("one");
        return false;
    }


}
