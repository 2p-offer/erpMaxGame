package com.erp.gameserver.test.pandoraconditionparse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class GeneratorEventType {
    public static void main(String[] args) throws FileNotFoundException {
        String excelStr = getExcelStr();
        String[] split = excelStr.split(",");
        List<String> excelList = Arrays.stream(split).toList();
        File file = new File("/Users/erp/project/java/erpMaxGame/gameserver/src/main/java/com/erp/gameserver/test/EventType.java");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> list = reader.lines()
                .toList();
        String lastLine = "";
        for (String perLine : list) {
            if (excelList.stream().anyMatch(perLine::contains)) {
                System.out.println(lastLine);
                System.out.println(perLine);
            }
            lastLine = perLine;
        }


//                .filter(line -> excelList.stream().anyMatch(line::contains))
//                .forEach(System.out::println);
    }

    public static String getExcelStr() {
        String excel = "Event_0000000," +
                "Event_0000001," +
                "Event_1320001," +
                "Event_1320005," +
                "Event_2800001," +
                "Event_3800001," +
                "Event_4800001," +
                "Event_2660020," +
                "Event_PlotFinish," +
                "Event_1300005," +
                "Event_1300008," +
                "Event_MoneyConsumeLevel," +
                "Event_OpenChestLevel," +
                "Event_OpenChestLevel01," +
                "Event_EquipBreakTimeTask," +
                "Event_EquipBreakMoneyGetTask," +
                "Event_openChestTimesDaily," +
                "Event_EquipWearNumQuality," +
                "Event_EquipWearNumOver," +
                "Event_TrialTowerPass," +
                "Event_openChestChallengeItemMax," +
                "Event_arenaVictoryTimes," +
                "Event_1900002," +
                "Event_3800002," +
                "Event_2660006," +
                "Event_3810002," +
                "Event_fairyFriendLevel," +
                "Event_fairyGiftTimesTask," +
                "Event_legendGetNum," +
                "Event_legendLevelUpTimesToday," +
                "Event_legendLevelUpTimesTask," +
                "Event_legendRecruitTimes," +
                "Event_legendRecruitTimesTask," +
                "Event_MainAdventureChallenge," +
                "Event_2650002," +
                "Event_3650002," +
                "Event_4650002," +
                "Event_FirstChargeFinish," +
                "Event_1700005," +
                "Event_3011001," +
                "Event_4011001," +
                "Event_5000001," +
                "Event_5000002," +
                "Event_5000003," +
                "Event_1011001," +
                "Event_1700003," +
                "Event_1700004," +
                "Event_1700001," +
                "Event_1700002," +
                "Event_ADPackTimesDaily," +
                "Event_ADPackTimesTotal," +
                "Event_EventsOpened," +
                "Event_itemOwnNumNow," +
                "Event_3800005," +
                "Event_1010209," +
                "Event_ItemCostTimeDaily," +
                "Event_escortLevelUpTimesToday," +
                "Event_escortMakeTimesToday," +
                "Event_escortLevelUpTimesTask," +
                "Event_escortMakeTimesTask," +
                "Event_escortIntoBattle," +
                "Event_escortMakeTimesTotal," +
                "Event_escortLevelHighest," +
                "Event_escortLevelUpTimesTotal," +
                "Event_airshipLevelUpTimesToday," +
                "Event_airshipLevelUpTimesTask," +
                "Event_airshipLevel," +
                "Event_airshipLevelUpTimesTotal," +
                "Event_crusadeBossTimesToday," +
                "Event_crusadeBossTimesTask," +
                "Event_crusadeBossTimesTotal," +
                "Event_battlePassFinish," +
                "Event_JoinedGuild," +
                "Event_NeverJoinedGuild," +
                "Event_6000001," +
                "Event_6000003," +
                "Event_1660001," +
                "Event_1660002," +
                "Event_2660001," +
                "Event_2660002," +
                "Event_1660003," +
                "Event_2660003," +
                "Event_MoneyConsumeTask," +
                "Event_MoneyConsumeTotal," +
                "Event_MoneyGetTask," +
                "Event_MoneyGetTotal," +
                "Event_MoneyCollectionDaily," +
                "Event_MoneyUseNumberDaily," +
                "Event_MoneyUseNumberWeek," +
                "Event_BossChallengeTimeDaily," +
                "Event_BossChallengeTimeWeek," +
                "Event_DrawTimesDaily," +
                "Event_DrawTimesWeekly," +
                "Event_DrawTimes";
        return excel;
    }
}
