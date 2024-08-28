package com.erp.gameserver.test;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) throws Exception {

        compareTxt();
//        base64();
//        System.out.println(test());
    }

    private static void compareTxt() throws Exception {
        BufferedReader gameReader = new BufferedReader(new FileReader("/Users/erp/work/topjoy/pandora/text/tmp/role_create"));
        BufferedReader globalReader = new BufferedReader(new FileReader("/Users/erp/work/topjoy/pandora/text/tmp/global_ad"));
        Map<String, String> gameMap = readtoMap(gameReader);
        Map<String, String> globalMap = readtoMap(globalReader);
        // 定义时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss,SSS");

        Map<String, Long> analyMap = new HashMap<>();
        long big = TimeUnit.SECONDS.toMillis(5);
        long fast = TimeUnit.SECONDS.toMillis(1);


        gameMap.forEach((k, gameValue) -> {
            String globalValue = globalMap.get(k);
            if (StringUtils.isNotEmpty(globalValue)) {
                // 解析时间字符串为LocalTime对象
                LocalTime gametime = LocalTime.parse(gameValue, formatter);
                LocalTime globaltime = LocalTime.parse(globalValue, formatter);

                // 计算两个时间之间的秒数差
                long secondsDifference = ChronoUnit.MILLIS.between(globaltime, gametime);
                analyMap.put(k, secondsDifference);
            }
        });


        long bigCount = analyMap.entrySet().stream()
                .filter(entry -> entry.getValue() > big)
                .count();
        long failCount = analyMap.entrySet().stream()
                .filter(entry -> entry.getValue() < 0)
                .count();
        long fastCount = analyMap.entrySet().stream()
                .filter(entry -> entry.getValue() < fast)
                .count();
        int totalCount = analyMap.size();
        OptionalDouble average = analyMap.entrySet().stream()
                .filter(entry -> entry.getValue() < big)
                .filter(entry -> entry.getValue() > 0)
                .mapToLong(Map.Entry::getValue)
                .average();
        OptionalLong max = analyMap.entrySet().stream()
                .filter(entry -> entry.getValue() < big)
                .filter(entry -> entry.getValue() > 0)
                .mapToLong(Map.Entry::getValue)
                .max();


        OptionalLong min = analyMap.entrySet().stream()
                .filter(entry -> entry.getValue() < big)
                .filter(entry -> entry.getValue() > 0)
                .mapToLong(Map.Entry::getValue)
                .min();
        System.out.println("收到归因回调且创建角色的玩家总数:" + totalCount);
        System.out.println("创建角色比归因回调延迟超过" + big + "毫秒的数量:" + bigCount);
        System.out.println("创建角色比归因回调延迟低于" + fast + "毫秒的数量:" + fastCount);
        System.out.println("创建角色早于收到归因回调的数量:" + failCount);
        System.out.println("可分析正常值数量:" + (totalCount - bigCount - failCount));
        System.out.println("去掉异常值,剩余的最大值:" + max.getAsLong());
        System.out.println("去掉异常值,剩余的最小值:" + min.getAsLong());
        System.out.println("去掉异常值,剩余的平均值:" + (long) average.getAsDouble());
    }

    private static Map<String, String> readtoMap(BufferedReader gameReader) throws IOException {
        Map<String, String> ans = new HashMap<>();
        while (true) {
            String s = gameReader.readLine();
            if (StringUtils.isEmpty(s)) {
                break;
            }
            String[] split = s.split(" ");
            String time = split[0];
            String id = split[split.length - 1];
            ans.put(id, time);

        }
        return ans;
    }

    private static void base64() {
        byte[] test = new byte[]{78, 71, 82, 109, 90, 71, 85, 50, 78, 50, 74, 104, 78, 84, 103, 121, 89, 87, 78, 105, 77, 84, 82, 106, 89, 84, 73, 119, 90, 106, 89, 121, 89, 106, 66, 107, 90, 68, 73, 48, 79, 71, 74, 105, 89, 122, 107, 50, 77, 122, 65, 119, 89, 122, 81, 119, 77, 106, 99, 121, 77, 84, 104, 107, 79, 84, 86, 106, 90, 106, 108, 106, 90, 87, 73, 48, 78, 71, 85, 48, 90, 84, 77, 122, 77, 71, 85, 51, 78, 122, 74, 104, 78, 84, 85, 51, 89, 109, 73, 48, 77, 106, 100, 104, 77, 50, 81, 120, 78, 68, 69, 121, 78, 122, 108, 106, 89, 50, 89, 49, 79, 87, 89, 120, 77, 109, 78, 105, 78, 84, 69, 122, 79, 68, 90, 108, 90, 84, 107, 51, 79, 71, 70, 104, 77, 68, 69, 50, 77, 68, 86, 104, 77, 106, 82, 109, 90, 84, 103, 50, 90, 68, 66, 107, 78, 87, 85, 120, 90, 106, 77, 119, 78, 106, 104, 109, 78, 84, 89, 50, 78, 109, 70, 106, 77, 122, 65, 49, 90, 71, 81, 122, 77, 68, 73, 49, 77, 68, 81, 48, 89, 50, 81, 52, 77, 71, 85, 50, 78, 71, 86, 108, 78, 87, 78, 106, 78, 109, 70, 109, 89, 50, 73, 48, 89, 122, 73, 49, 78, 84, 99, 51, 79, 68, 66, 104, 79, 87, 81, 48, 78, 122, 89, 53, 90, 84, 65, 51, 90, 106, 99, 120, 79, 68, 85, 120, 78, 87, 73, 119, 77, 122, 86, 109, 78, 109, 85, 49, 89, 106, 107, 121, 78, 71, 89, 50, 77, 71, 86, 104, 78, 71, 90, 108, 78, 68, 69, 119, 77, 87, 74, 106, 77, 106, 69, 51, 77, 84, 78, 108, 78, 122, 85, 53, 77, 71, 69, 122, 78, 50, 90, 108, 78, 106, 100, 107, 90, 106, 73, 121, 90, 68, 81, 122, 79, 84, 107, 51, 77, 50, 78, 105, 89, 122, 81, 122, 89, 122, 70, 106, 77, 106, 74, 104, 77, 122, 69, 120, 78, 109, 81, 48, 79, 68, 85, 52, 77, 87, 69, 120, 78, 87, 89, 122, 78, 68, 107, 122, 89, 109, 70, 105, 79, 71, 70, 104, 78, 122, 99, 49, 90, 84, 73, 120, 79, 68, 90, 104, 78, 109, 73, 49, 77, 84, 77, 120, 89, 50, 69, 53, 89, 84, 107, 53, 78, 109, 90, 108, 78, 50, 81, 51, 79, 87, 81, 51, 77, 87, 86, 108, 89, 50, 82, 108, 78, 122, 73, 122, 90, 71, 90, 109, 78, 106, 69, 122, 77, 84, 65, 50, 77, 71, 82, 107, 90, 68, 73, 120, 77, 71, 73, 119, 78, 84, 90, 107, 90, 109, 85, 49, 89, 50, 74, 105, 77, 68, 86, 107, 90, 65, 61, 61};
        String testStr = new String(test);
        System.out.println(testStr);
        System.out.println(Arrays.toString("NGRmZGU2N2JhNTgyYWNiMTRjYTIwZjYyYjBkZDI0OGJiYzk2MzAwYzQwMjcyMThkOTVjZjljZWI0NGU0ZTMzMGU3NzJhNTU3YmI0MjdhM2QxNDEyNzljY2Y1OWYxMmNiNTEzODZlZTk3OGFhMDE2MDVhMjRmZTg2ZDBkNWUxZjMwNjhmNTY2NmFjMzA1ZGQzMDI1MDQ0Y2Q4MGU2NGVlNWNjNmFmY2I0YzI1NTc3ODBhOWQ0NzY5ZTA3ZjcxODUxNWIwMzVmNmU1YjkyNGY2MGVhNGZlNDEwMWJjMjE3MTNlNzU5MGEzN2ZlNjdkZjIyZDQzOTk3M2NiYzQzYzFjMjJhMzExNmQ0ODU4MWExNWYzNDkzYmFiOGFhNzc1ZTIxODZhNmI1MTMxY2E5YTk5NmZlN2Q3OWQ3MWVlY2RlNzIzZGZmNjEzMTA2MGRkZDIxMGIwNTZkZmU1Y2JiMDVkZA==".getBytes()));
        byte[] decode = Base64.getDecoder().decode("NGRmZGU2N2JhNTgyYWNiMTRjYTIwZjYyYjBkZDI0OGJiYzk2MzAwYzQwMjcyMThkOTVjZjljZWI0NGU0ZTMzMGU3NzJhNTU3YmI0MjdhM2QxNDEyNzljY2Y1OWYxMmNiNTEzODZlZTk3OGFhMDE2MDVhMjRmZTg2ZDBkNWUxZjMwNjhmNTY2NmFjMzA1ZGQzMDI1MDQ0Y2Q4MGU2NGVlNWNjNmFmY2I0YzI1NTc3ODBhOWQ0NzY5ZTA3ZjcxODUxNWIwMzVmNmU1YjkyNGY2MGVhNGZlNDEwMWJjMjE3MTNlNzU5MGEzN2ZlNjdkZjIyZDQzOTk3M2NiYzQzYzFjMjJhMzExNmQ0ODU4MWExNWYzNDkzYmFiOGFhNzc1ZTIxODZhNmI1MTMxY2E5YTk5NmZlN2Q3OWQ3MWVlY2RlNzIzZGZmNjEzMTA2MGRkZDIxMGIwNTZkZmU1Y2JiMDVkZA==");
//        System.out.println(Arrays.toString(decode));

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
