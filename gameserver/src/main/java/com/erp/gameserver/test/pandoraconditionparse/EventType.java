package com.erp.gameserver.test.pandoraconditionparse;

import java.util.HashMap;
import java.util.Map;

/**
 * condition组件的事件类型枚举
 *
 * @author erp
 */
public enum EventType {

    //约定规则
    /** 默认不通过event */
    EVENT_OFF("Event_0000000", true, "始终不通过event,Ds->final{ false }"),
    /** 默认通过event */
    EVENT_ON("Event_0000001", true, "始终通过event,Ds->final{ true }"),

    /** 开服天数 */
    SERVER_OPEN_DAY("Event_2660020", true, "开服天数"),


    //饮料/装备 制造
    /** 累计制造任意饮料的数量 */
    MAKE_DRINKS_TOTAL("Event_1000001", true, "累计制造任意饮料的数量,Ds->counter{CounterType.DRINK_MAKE,CounterResetType.NEVER }"),
    /** 接受任务后制造任意饮料的数量 */
    MAKE_DRINKS_TOTAL_TIME("Event_2000001", false, "接受任务后制造任意饮料的数量,Ds->counter{CounterType.DRINK_MAKE,CounterResetType.NEVER }"),
    /** 累计制造指定品质c饮料的数量 */
    TOTAL_MAKE_DRINKS_GRADE("Event_1000003", true, "累计制造指定品质c饮料的数量,Ds->counter{CounterType.DRINK_MAKE,CounterResetType.NEVER,CounterQueryIndex.DRINK_MAKE_BY_QUALITY=? }"),
    /** 累计制造指定饮料e的数量 */
    TOTAL_MAKE_DRINKS_TAG("Event_1000009", true, "累计制造指定饮料e的数量,Ds->counter{CounterType.DRINK_MAKE,CounterResetType.NEVER,CounterQueryIndex.DRINK_MAKE_BY_TAG=? }"),
    /** 累计制造任意装备的数量 */
    TOTAL_MAKE_EQUIP("Event_1001001", true, "累计制造任意装备的数量,Ds->counter{CounterType.EQUIP_MAKE,CounterResetType.NEVER }"),
    /** 累计制造指定品质c的装备的数量 */
    TOTAL_MAKE_EQUIP_QUALITY("Event_1001015", true, "累计制造指定品质c的装备的数量,Ds->counter{CounterType.EQUIP_MAKE,CounterResetType.NEVER,CounterQueryIndex.EQUIP_MAKE_BY_QUALITY=? }"),
    /** 累计制造指定装备e的数量 */
    TOTAL_MAKE_EQUIP_TAG("Event_1001018", true, "累计制造指定装备e的数量,Ds->counter{CounterType.EQUIP_MAKE,CounterResetType.NEVER,CounterQueryIndex.DRINK_MAKE_BY_TAG=? }"),
    /** 累计祭炼任意装备的次数 */
    EQUIP_STRENGTHEN_ALL_TOTAL("Event_1031003", true, "累计祭炼任意装备的次数,Ds->counter{CounterType.EQUIP_STRENGTHEN,CounterResetType.NEVER }"),
    /** 累计强化任意装备到b级 */
    EQUIP_STRENGTHEN_LEVEL("Event_1030001", true, "累计强化任意装备的等级,Ds->counter{CounterType.EQUIP_STRENGTHEN_LEVEL,CounterResetType.NEVER }"),
    /** 累计制造指定多种类型a、品质c的装备的数量 */
    TOTAL_MAKE_EQUIP_MULTI_TYPE_QUALITY("Event_1001019", true, "累计制造指定多种类型a、品质c的装备的数量,Ds->counter{CounterType.EQUIP_MAKE,CounterResetType.NEVER，CounterQueryIndex.EQUIP_MAKE_BY_QUALITY=？,CounterQueryIndex.EQUIP_MAKE_BY_TYPE in () }"),
    /** 累计获得XX品质的核心的数量（任意来源，获得就算） */
    TOTAL_GAIN_CORE_QUALITY("Event_1001022", true, "累计获得XX品质的核心的数量（任意来源，获得就算）,Ds->counter{CounterType.EQUIP_MAKE, CounterResetType.NEVER, CounterQueryIndex.EQUIP_MAKE_BY_QUALITY=?}"),
    /** 累计获得XX品质XX类型装备的数量（任意来源，获得就算。且类型允许配数组） */
    TOTAL_GAIN_EQUIP_QUALITY_TYPE("Event_1001023", true, "累计获得XX品质XX类型装备的数量（任意来源，获得就算。且类型允许配数组, Ds->{CounterType.EQUIP_MAKE, CounterResetType.NEVER, CounterQueryIndex.EQUIP_MAKE_BY_TYPE=?, CounterQueryIndex.EQUIP_MAKE_BY_QUALITY=?}"),
    /** 累计获得a品质以上的装备 */
    TOTAL_GAIN_EQUIP_QUALITY("Event_1010601", true, "获得a品质及以上装备的数量 Ds->counter{CounterType.HUNTING_GROUND_EQUIP_SELL, CounterResetType.NEVER, CounterQueryIndex.HUNTING_GROUND_EQUIP_SELL_BY_QUALITY=?, CounterType.EQUIP_GAIN, CounterResetType.NEVER, CounterQueryIndex.EQUIP_GAIN_BY_QUALITY=?}"),
    /** 累计获得a品质以上的丹药 */
    TOTAL_GAIN_DRINK_QUALITY("Event_1010602", true, "获得a品质及以上丹药的数量 Ds->counter{CounterType.ITEM_GAIN, CounterResetType.NEVER, CounterQueryIndex.ITEM_GAIN_BY_QUALITY=?, CounterQueryIndex.ITEM_GAIN_BY_FUNCTION_TYPE = ItemFunctionType.EXP_DRINK.getType()}"),

    // 道具
    /** 累计服用任意饮料的数量 */
    DRINK_USE_TOTAL("Event_1023000", true, "累计服用任意饮料的数量,Ds->counter{CounterType.ITEM_USE,CounterResetType.NEVER,CounterQueryIndex.ITEM_USE_BY_FUNCTION_TYPE=ItemFunctionType.EXP_DRINK.getType() }"),
    /** 累计获得指定道具 */
    ITEM_GAIN("Event_1010208", true, "累计获得指定道具,Ds->counter{CounterType.ITEM_GAIN,CounterResetType.NEVER,CounterQueryIndex.ITEM_GAIN_BY_TAG=? }"),
    /** 历史上从未获得过道具 */
    ITEM_NOT_GAIN("Event_1010209", true, true, "历史上从未获得过道具Ds->counter{CounterType.ITEM_GAIN,CounterResetType.NEVER,CounterQueryIndex.ITEM_GAIN_BY_TAG=? }"),
    /** 累计通过指定途径f获得的指定道具e的数量 */
    ITEM_GAIN_SOURCE("Event_1010205", true, "累计通过指定途径f获得的指定材料e的数量,Ds->counter{CounterType.ITEM_GAIN,CounterResetType.NEVER,CounterQueryIndex.ITEM_GAIN_BY_TAG=?,CounterQueryIndex.ITEM_GAIN_BY_SOURCE=? }"),
    /** 累计使用指定功能类型的道具的数量 */
    TOTAL_ITEM_USE_FUNCTION_TYPE("Event_1023005", true, "累计使用指定功能类型的道具的数量,Ds->counter{CounterType.ITEM_USE,CounterResetType.NEVER，CounterQueryIndex.ITEM_USE_BY_FUNCTION_TYPE in () }"),
    /** 今日使用指定功能类型的道具的数量 */
    TODAY_ITEM_USE_FUNCTION_TYPE("Event_3800005", true, true, "今日使用指定功能类型的道具的数量,Ds->counter{CounterType.ITEM_USE,CounterResetType.DAILY，CounterQueryIndex.ITEM_USE_BY_FUNCTION_TYPE in ()  }"),
    /** 累计消耗指定道具/材料e的数量 */
    ITEM_COST_TAG("Event_1022005", true, "累计消耗指定道具/材料e的数量,Ds->counter{CounterType.ITEM_COST,CounterResetType.NEVER ,CounterQueryIndex.ITEM_COST_BY_TAG=? }"),
    /** 接受任务后使用a功能类型道具的次数 */
    TIME_ITEM_USE_FUNCTION_TYPE("Event_2660008", false, "接受任务后使用a功能类型道具的次数,Ds->counter{CounterType.ITEM_USE,CounterResetType.NEVER,CounterQueryIndex.ITEM_USE_BY_FUNCTION_TYPE in () }"),
    /** 累计鉴定装备的数量 */
    ITEM_EQUIP_APPRAISAL("Event_1001020", true, "累计鉴定装备的数量 Ds->counter{CounterType.ITEM_USE, CounterResetType.NEVER, CounterQueryIndex.ITEM_USE_BY_FUNCTION_TYPE = ItemFunctionType.EQUIP_APPRAISAL.getType()}"),
    /** 当前拥有的a道具的数量 */
    ITEM_CURRENT_NUM_TOTAL("Event_itemOwnNumNow", true, true, "当前拥有的a道具的数量 Ds->role.getBag().getItems().get(tag)"),

    // 货币
    /** 累计消耗指定货币的数量 */
    MONEY_COST_TOTAL("Event_1021002", true, "累计消耗指定货币的数量,Ds->counter{CounterType.MONEY_COST,CounterResetType.NEVER,CounterQueryIndex.MONEY_COST_BY_TAG=? }"),
    /** 接受任务后通过分解装备获得a货币（a为Money表tag）的数量达到@ */
    MONEY_GAIN_BY_EQUIP_NEW_TIME("Event_EquipBreakMoneyGetTask", false, "接受任务后通过分解装备获得a货币（a为Money表tag）的数量达到@,Ds->counter{}"),
    /** 每日累计获得X个参数a的货币 */
    MONEY_GAIN_TAG_DAILY("Event_MoneyCollectionDaily", true, true, "每日累计获得X个参数a的货币,Ds->counter{CounterType.MONEY_GAIN,CounterResetType.DAILY"),
    /** 每日累计使用x个参数a的货币 */
    MONEY_COST_TAG_DAILY("Event_MoneyUseNumberDaily", true, true, "每日累计使用x个参数a的货币,Ds->counter{CounterType.MONEY_COST,CounterResetType.DAILY"),

    // 技能
    /** 玩家任意技能升级的次数 */
    SKILL_LEVEL_UP_ALL_TOTAL("Event_1100003", true, "玩家任意技能升级的次数,Ds->counter{CounterType.SKILL_LEVEL_UP_TOTAL,CounterResetType.NEVER }"),
    /** 玩家任意方向d的技能最高达到X级 */
    SKILL_MAX_LEVEL("Event_1100002", true, "玩家任意方向d的技能最高达到X级,Ds->diff{ role.skillTree.skillTreeMap }"),
    /** 玩家指定技能达到的等级 */
    SKILL_LEVEL("Event_1100004", true, "玩家指定技能达到的等级,Ds->diff{role.skillTree.skillTreeMap.get(skillTreeTag).getLevel() }"),
    /** 玩家指定方向d的技能累计升级的次数 */
    SKILL_LEVEL_UP_TIMES("Event_1100005", true, "玩家指定方向d的技能累计升级的次数,Ds->counter{CounterType.SKILL_LEVEL_UP_TOTAL,CounterResetType.NEVER,CounterQueryIndex.SKILL_LEVEL_UP_BY_OCCUPATIO=? }"),
    /** 任意方向，累计解锁X个a阶技能 */
    SKILL_UNLOCK_STAGE_NUM("Event_1112004", true, "任意方向，累计解锁X个a阶技能,Ds->diff{role.skillTree.skillTreeMap }"),

    // region 玩家等级
    /** 玩家任意修炼方向最大修炼等级达到a */
    ROLE_HIGH_TRAINING_LEVEL_STAGE("Event_1300005", true, "玩家任意修炼方向最大修炼等级达到a,Ds->diff{role.getTraining().getTrainingProgressMap() }"),
    /** 玩家指定修炼方向b修炼等级达到a */
    ROLE_TRAINING_LEVEL_BY_TYPE("Event_1300006", true, "玩家指定修炼方向b修炼等级达到a,Ds->diff{role.getTraining().getTrainingProgressMap().get({config.a}).getLevel() }"),
    /** 玩家任意修炼方向最小修炼等级达到a */
    ROLE_LOW_TRAINING_LEVEL_STAGE("Event_1300007", true, "玩家任意修炼方向最小修炼等级达到a,Ds->diff{role.getTraining().getTrainingProgressMap() }"),
    /** 玩家任意修炼方向最大修炼等级小于a */
    ROLE_HIGH_TRAINING_LEVEL_STAGE_LT("Event_1300008", true, true, "玩家任意修炼方向最大修炼等级小于a,Ds->diff{role.getTraining().getTrainingProgressMap() }"),
    /** 玩家指定修炼方向b修炼等级小于a */
    ROLE_TRAINING_LEVEL_BY_TYPE_LT("Event_1300009", true, true, "玩家指定修炼方向b修炼等级小于a,Ds->diff{role.getTraining().getTrainingProgressMap() }"),
    /** 玩家任意修炼方向最小修炼等级小于a */
    ROLE_LOW_TRAINING_LEVEL_STAGE_LT("Event_1300010", true, true, "玩家任意修炼方向最小修炼等级小于a,Ds->diff{role.getTraining().getTrainingProgressMap() }"),
    /** 玩家突破等级[成功/失败]的次数 */
    ROLE_LEVEL_BREAK_STATE_TOTAL("Event_1310002", true, "玩家突破等级[成功/失败]的次数,Ds->counter{CounterType.ROLE_LEVEL_BREAK,CounterResetType.NEVER,CounterQueryIndex.ROLE_LEVEL_BREAK_BY_STATE=? }"),
    /** 玩家累计任意方向训练时间 */
    ROLE_TRAINING_TIME_ALL_TOTAL("Event_1330001", true, "玩家累计任意方向训练时间,Ds->diff{role.getBase().startTrainingTimeMillis }"),
    /** 玩家累计吐纳次数 */
    ROLE_TRAINING_BURST_TOTAL("Event_1340001", true, "玩家累计吐纳次数,Ds->counter{CounterType.BURST,CounterResetType.NEVER }"),
    /** 玩家当前训练方向 */
    ROLE_CURRENT_TRAINING_TYPE("Event_1900006", true, true, "玩家当前训练方向,Ds->diff{role.getTraining().getCurrentTrainingType() }"),
    // endregion

    //怪物击杀
    /** 累计通过任意途径击杀任意等级的任意怪兽的数量 */
    MONSTER_KILL("Event_1610001", true, "累计通过任意途径击杀任意等级的任意怪兽的数量,Ds->diff{role.getCounterLite().getTotalKillMonsterCount() }"),
    /** 累计通过任意途径击杀任意等级的指定怪兽e的数量 */
    MONSTER_KILL_TAG("Event_1610002", true, "累计通过任意途径击杀任意等级的指定怪兽e的数量,Ds->counter{CounterType.MONSTER_KILL,CounterResetType.NEVER,CounterQueryIndex.MONSTER_KILL_BY_TAG"),
    /** 历史累计获得充值积分大于等于 */
    ROLE_CHARGE_POINT("Event_1700001", true, "历史累计获得充值积分大于等于,Ds->counter{CounterType.DRINK_MAKE,CounterResetType.NEVER }"),
    /** 历史累计获得充值积分小于 */
    ROLE_CHARGE_POINT_LT("Event_1700002", true, true, "历史累计获得充值积分小于,Ds->diff{ role.getCharge().getTotalChargePoints() }"),
    /** 历史充值过a计费点档位的次数 */
    ROLE_CHARGE_PAYMENT_TIMES("Event_1700003", true, "历史充值过a计费点档位的次数,Ds->diff{ role.getCharge().getChargeRecordMap().get(paymentTag).getTotalChargeTimes() }"),

    /** 接受任务后在猎场击杀指定怪兽e的数量 */
    MONSTER_KILL_HUNTING_GROUND_TAG("Event_2610004", false, "接受任务后在猎场击杀指定怪兽e的数量,Ds->counter{CounterType.MONSTER_KILL,CounterResetType.NEVER,CounterQueryIndex.MONSTER_KILL_BY_BATTLE_BIZ_TYPE=BattleBizType.HUNTING_GROUND.getType(),CounterQueryIndex.MONSTER_KILL_BY_TAG=? }"),
    /** 接受任务后于猎场击败任意妖兽的数量 */
    MONSTER_KILL_HUNTING_GROUND_TIME("Event_2611001", false, "接受任务后于猎场击败任意妖兽的数量,Ds->counter{CounterType.MONSTER_KILL,CounterResetType.NEVER,CounterQueryIndex.MONSTER_KILL_BY_BATTLE_BIZ_TYPE=BattleBizType.HUNTING_GROUND.getType() }"),
    /** 接受任务后通过猎场中指定地图f击杀任意怪兽的数量 */
    MONSTER_KILL_HUNTING_GROUND_SENSE_TIME("Event_2610005", false, "接受任务后通过猎场中指定地图f击杀任意怪兽的数量,Ds->counter{CounterType.MONSTER_KILL,CounterResetType.NEVER,CounterQueryIndex.MONSTER_KILL_BY_BATTLE_BIZ_TYPE=BattleBizType.HUNTING_GROUND.getType(),CounterQueryIndex.MONSTER_KILL_BY_BIZ_TAG=? }"),
    /** 接受任务后通过猎场中指定地图f击杀指定怪兽e的数量 */
    MONSTER_KILL_HUNTING_GROUND_SENSE_MONSTER_TIME("Event_2610007", false, "接受任务后通过猎场中指定地图f击杀指定怪兽e的数量,Ds->counter{CounterType.MONSTER_KILL,CounterResetType.NEVER,CounterQueryIndex.MONSTER_KILL_BY_BATTLE_BIZ_TYPE=BattleBizType.HUNTING_GROUND.getType(),CounterQueryIndex.MONSTER_KILL_BY_BIZ_TAG=?,CounterQueryIndex.MONSTER_KILL_BY_TAG=? }"),
    /** 累计于猎场击败X只妖兽 */
    MONSTER_KILL_HUNTING_GROUND("Event_1611001", true, "累计于猎场击败X只妖兽,Ds->counter{CounterType.MONSTER_KILL,CounterResetType.NEVER ,CounterQueryIndex.MONSTER_KILL_BY_BATTLE_BIZ_TYPE=BattleBizType.HUNTING_GROUND.getType() }"),
    /** 累计在猎场击杀指定类型怪物b的数量 */
    MONSTER_KILL_HUNTING_GROUND_MONSTER_TYPE("Event_1610003", true, "累计在猎场击杀指定类型怪物b的数量,Ds->counter{CounterType.MONSTER_KILL,CounterResetType.NEVER,CounterQueryIndex.MONSTER_KILL_BY_MONSTER_TYPE=? }"),
    /** 累计在猎场击杀指定怪兽e的数量 */
    MONSTER_KILL_HUNTING_GROUND_MONSTER("Event_1610004", true, "累计在猎场击杀指定怪兽e的数量,Ds->counter{CounterType.MONSTER_KILL,CounterResetType.NEVER,CounterQueryIndex.MONSTER_KILL_BY_BATTLE_BIZ_TYPE=BattleBizType.HUNTING_GROUND.getType(),CounterQueryIndex.MONSTER_KILL_BY_TAG=? }"),
    /** 累计通过猎场中指定地图f击杀任意怪兽的数量 */
    MONSTER_KILL_HUNTING_GROUND_SENSE("Event_1610005", true, "累计通过猎场中指定地图f击杀任意怪兽的数量,Ds->counter{CounterType.MONSTER_KILL,CounterResetType.NEVER,CounterQueryIndex.MONSTER_KILL_BY_BATTLE_BIZ_TYPE=BattleBizType.HUNTING_GROUND.getType(),CounterQueryIndex.CounterQueryIndex.MONSTER_KILL_BY_BIZ_TAG=? }"),
    /** 累计在试炼场击杀指定怪兽e的数量 */
    MONSTER_KILL_TRIAL_YARD_MONSTER("Event_1610006", true, "累计在试炼场击杀指定怪兽e的数量,Ds->counter{CounterType.MONSTER_KILL,CounterResetType.NEVER,CounterQueryIndex.MONSTER_KILL_BY_BATTLE_BIZ_TYPE=BattleBizType.TRIAL_YARD.getType(),CounterQueryIndex.MONSTER_KILL_BY_TAG=? }"),
    /** 累计通过猎场中指定地图f击杀指定怪兽e的数量 */
    MONSTER_KILL_HUNTING_GROUND_SENSE_MONSTER("Event_1610007", true, "累计通过猎场中指定地图f击杀指定怪兽e的数量,Ds->counter{CounterType.MONSTER_KILL,CounterResetType.NEVER,CounterQueryIndex.MONSTER_KILL_BY_BATTLE_BIZ_TYPE=BattleBizType.HUNTING_GROUND.getType(),CounterQueryIndex.MONSTER_KILL_BY_BIZ_TAG=?,CounterQueryIndex.MONSTER_KILL_BY_TAG=? }"),
    /** 接受任务后在主线推关中击杀任意怪物的数量 */
    MONSTER_KILL_MAIN_LINE_MOPUP_TIME("Event_mainStageMonsterKillNumTask", false, "接受任务后在主线推关中击杀任意怪物的数量,Ds->counter{CounterType.MONSTER_KILL,CounterResetType.NEVER ,CounterQueryIndex.MONSTER_KILL_BY_BATTLE_BIZ_TYPE=BattleBizType.MAIN_LINE_MOPUP.getType() }"),

    //试炼场
    /** 累计镇妖塔通关层数 */
    TRIAL_YARD_LEVEL("Event_1650001", true, "累计镇妖塔通关层数,Ds->diff{ role.getTrialYard().getLevel() }"),
    /** 累计镇妖塔挑战次数 */
    TRIAL_YARD_CHALLENGE("Event_1650002", true, "累计镇妖塔挑战次数,Ds->counter{CounterType.TRIAL_YARD_CHALLENGE,CounterResetType.NEVER }"),


    // 书籍
    /** 指定书籍e达到的层级 */
    BOOK_CHAPTER("Event_1150001", true, "指定书籍e达到的层级,Ds->diff{ role.getBook().getBookMap().get(bookTag).getChapter() }"),
    /** 指定书籍e达到指定层级圆满 */
    BOOK_CHAPTER_MASTER("Event_1150002", true, "指定书籍e达到指定层级圆满,Ds->diff { roleBook.getBookMap().get(bookTag) }"),
    /** 累计达到圆满的书籍数量 */
    BOOK_MASTER("Event_1160001", true, "累计达到圆满的书籍数量,Ds->diff{ role.getBook().getBookMap() }"),
    /** 研读书籍X次 */
    BOOK_LEARN_ALL_TOTAL("Event_1160002", true, "研读书籍X次,Ds->counter{CounterType.BOOK_LEARN,CounterResetType.NEVER }"),
    /** 指定层书籍完全掌握的本数 */
    BOOK_FLOOR_MASTER("Event_1160004", true, "指定层书籍完全掌握的本数,Ds->diff{ role.getBook().getBookMap() }"),
    /** 指定层书籍到达第X章的本数 */
    BOOK_FLOOR_CHAPTER("Event_1160005", true, "指定层书籍到达第X章的本数,Ds->diff{ role.getBook().getBookMap()R }"),


    /** 提升训练师的好感度到X */
    TRAINER_LEVEL_TOTAL("Event_1451002", true, "提升训练师的好感度到X,Ds->diff{ role.getTraining().getTrainerLevel() }"),

    /** 生活技能等级 */
    /** 酒吧等级 */
    BAR_GRADE("Event_1451001", true, "将酒吧提升的等级,Ds->diff{ role.getBar().getBarGrade() }"),
    /** 军工厂等级 */
    EQUIP_FACTORY_GRADE("Event_1451000", true, "将军工厂提升的等级,Ds->diff{ role.getEquipFactory().getEquipFactoryGrade() }"),

    //关卡
    /** 历史上累计完成指定tag的关卡的次数 */
    LEVEL_ACTION("Event_1660003", true, "历史上累计完成指定tag的关卡的次数,Ds->counter{CounterType.LEVEL_ACTION_PASS,CounterResetType.NEVER, CounterQueryIndex.LEVEL_ACTION_PASS_BY_TAG=? }"),
    /** 完成指定tag的关卡 */
    LEVEL_ACTION_TIME("Event_2660003", false, "完成指定tag的关卡,Ds->counter{CounterType.LEVEL_ACTION_PASS,CounterResetType.NEVER,CounterQueryIndex.LEVEL_ACTION_PASS_BY_TAG=? }"),
    /** 完成指定tag的传送阵,不能基于counter实现 */
    PASS_TRANSMISSION("Event_1670001", true, "完成指定tag的传送阵,Ds->diff{CounterType.DRINK_MAKE,CounterResetType.NEVER,role.getTransmission().getTransmissionMap().get(levelTag) }"),

    // 藏品
    /** 指定藏品修复至X星 */
    RELIC_STAR("Event_1032007", true, "指定藏品修复至X星,Ds->diff{ role.getRelic().getRelicMap() }"),
    /** 累计秘藏总研究次数达到x次 */
    RELIC_RESEARCH("Event_1032006", true, "累计秘藏总研究次数达到x次,Ds->counter{CounterType.RELIC_RESEARCH,CounterResetType.NEVER }"),
    /** 累计拥有指定星级以上的指定品质秘藏的数量 */
    RELIC_UP_STAR_NUM("Event_1032000", true, "累计拥有指定星级以上的指定品质秘藏的数量,Ds->diff{ role.getRelic().getRelicMap() }"),
    /** 累计获得指定星级秘藏的数量 */
    TOTAL_RELIC_STAR("Event_1010306", true, "累计获得指定星级秘藏的数量,Ds->diff{ role.getRelic().getRelicMap() }"),
    /** 累计获得指定品质秘藏的数量 */
    TOTAL_RELIC_QUALITY("Event_1010304", true, "累计获得指定品质秘藏的数量,Ds->diff{ role.getRelic().getRelicMap() }"),
    /** 累计拥有秘藏收集值的量 */
    TOTAL_RELIC_QA_VALUE("Event_1001026", true, "累计拥有秘藏收集值的量,Ds->diff{ role.getRelic().getRelicMap() }"),
    /** 累计拥有秘藏收集值的量 */
    TOTAL_ACTIVITY_TREASURE_DRAW_VALUE("Event_1001027", true, "无限宝藏历史累计积分达到（大于等于）,Ds->diff{ role.getActivity().getActivityMap().get(activityTag).getCardPoolData().getScore() }"),

    // 任务
    /** 历史上累计完成指定tag的任务的次数 */
    TASK_FINISH_TAG("Event_1660001", true, "历史上累计完成指定tag的任务的次数,Ds->counter{CounterType.TASK_FINISH,CounterResetType.NEVER,CounterQueryIndex.TASK_FINISH_BY_TAG=? }"),
    /** 历史上累计接受指定tag的任务的次数 */
    TASK_ACCEPT_TAG("Event_1660002", true, "历史上累计接受指定tag的任务的次数,Ds->counter{CounterType.TASK_ACCEPT,CounterResetType.NEVER,CounterQueryIndex.TASK_ACCEPT_BY_TAG=? }"),
    /** 接受任务后打败指定NPC的次数 */
    TASK_NPC_CHALLENGE_SUCCESS_TIME("Event_2620001", false, "接受任务后打败指定NPC的次数,Ds->counter{CounterType.TASK_CHALLENGE,CounterResetType.NEVER ,CounterQueryIndex.TASK_CHALLENGE_BY_NPC=? }"),
    /** 完成指定tag的任务 */
    TASK_FINISH("Event_2660001", true, "完成指定tag的任务,Ds->diff{ role.getTask().getTaskTypeMap() }"),
    /** 接受指定tag的任务 */
    TASK_ACCEPT("Event_2660002", true, "接受指定tag的任务,Ds->diff{  role.getTask().getTaskTypeMap() }"),
    /** 累计完成悬赏的次数 */
    REWARD_TASK_RECEIVED("Event_1900004", true, "累计完成悬赏的次数,Ds->counter{CounterType.REWARD_TASK_RECEIVED,CounterResetType.NEVER }"),
    /** 接受任务后，完成悬赏任务的次数 */
    TASK_REWARD_TASK_RECEIVED("Event_2660005", false, "接受任务后，完成悬赏任务的次数,Ds->counter{CounterType.REWARD_TASK_RECEIVED,CounterResetType.NEVER }"),
    // 属性
    /** 玩家指定属性达到XXX */
    ATTRIBUTE("Event_1320005", true, "玩家指定属性达到XXX,Ds->diff{ role.getAttribute().getTotalAttributes().get(attributeType) }"),

    // 改造人
    /** 改造人等级提升到X */
    MADE_MAN_LEVEL("Event_1451003", true, "改造人等级提升到X,Ds->diff{ role.getMadeMan().getLevel() }"),

    // 公会
    /** 是否加入了公会 */
    GUILD_JOIN("Event_6000001", true, true, "是否加入了公会,Ds->diff{ role.getGuild().getId() }"),
    /** 历史曾经加入过公会 */
    GUILD_JOINED("Event_JoinedGuild", true, "Ds->diff{role.getGuild().firstJoinTime > 0}"),
    /** 历史从未加入过公会 */
    GUILD_NEVER_JOIN("Event_NeverJoinedGuild", true, true, "Ds->diff{role.getGuild().firstJoinTime < 0}"),
    /** 本周参与公会建设的次数 */
    GUILD_BUILD_TIMES_WEEKLY("Event_GuildBuildTimesWeek", true, true, "本日参与公会建设的次数,Ds->counter{CounterType.GUILD_BUILD,CounterResetType.WEEKLY }"),

    //飞升之路
    //** 飞升之路红点奖励领取 */

    //商店
    /** 指定途径f购买指定商品的次数 */
    GOODS_BUY("Event_1011001", true, "指定途径f购买指定商品的次数,Ds->counter{CounterType.SHOP_BUY,CounterResetType.NEVER,CounterQueryIndex.SHOP_BUY_BY_TAG=? }"),
    /** 历史上获得过{a|goodsTag}的礼包 */
    HAS_GAIN_GOODS_BAG("Event_1700004", true, "历史上获得过{a|goodsTag}的礼包"),
    /** 历史上从未获得过{a|goodsTag}的礼包 */
    HAS_NOT_GAIN_GOODS_BAG("Event_1700005", true, true, "历史上从未获得过{a|goodsTag}的礼包"),

    //秘藏收藏活动
    /** 秘藏收藏活动领取完所有积分奖励 */
    RELIC_COLLECT_RECEIVE_ALL_REWARD("Event_5000004", false, "秘藏收藏活动领取完所有积分奖励, Ds"),

    // 藏宝图
    /** 最近一次使用的藏宝图编号 */
    TREASURE_MAP_LAST_US_MAP("Event_7000001", false, true, "最近一次使用的藏宝图编号,Ds->diff{ role.getRoleTreasureMap().getLastMapZone() }"),
    /** 最近一次挂机的猎场e */
    TREASURE_MAP_LAST_HUNTING_GROUND("Event_7000002", false, true, "最近挂机的猎场,Ds->diff{ role.getBattle().getHuntingGroundInfo().getLastHuntingGroundTag() }"),
    // 周循环活动
    /** 指定e周活动期间抽奖的次数 */
    ROLE_ACTIVITY_DRAW_TIMES("Event_2660009", false, "指定e周活动期间抽奖的次数,Ds->diff{ role.getActivity().getActivityMap().get(activityTag).getRoleTimes() }"),
    /** 当前全服务器指定e周活动期间抽奖的次数 */
    SERVER_ACTIVITY_DRAW_TIMES("Event_2660010", false, "当前全服务器指定e周活动期间抽奖的次数,Ds->diff{ role.getActivity().getActivityMap().get(activityTag).getRoleTimes() }"),

    /** 个人在周活动-物资捐赠活动开启期间捐赠（抽卡）的次数（本服） */
    ROLE_ACTIVITY_BUILD_DRAW_TIMES("Event_2660021", false, "个人在周活动-物资捐赠活动开启期间捐赠（抽卡）的次数（本服）,Ds->diff{ role.getActivity().getActivityMap().get(activityTag).getRoleTimes() }"),

    /** 本服所有玩家在周活动-物资捐赠活动开启期间捐赠（抽卡）的次数（本服） */
    SERVER_ACTIVITY_BUILD_DRAW_TIMES("Event_2660022", false, "本服所有玩家在周活动-物资捐赠活动开启期间捐赠（抽卡）的次数（本服）,Ds->diff{ role.getActivity().getActivityMap().get(activityTag).getRoleTimes() }"),


    /** 指定e周活动开启期间兑换f层商品任意次数 */
    WEEK_RELIC_SHOP_BUY_TIMES("Event_2660011", false, "指定e周活动开启期间兑换f层商品任意次数,Ds->diff{ role.getShop().getShopMap().get(shopTag).getShopPageMap().get(floor).getGoodsMap() }"),
    // 好友招募
    /** 好友招募领取完所有奖励 */
    FRIEND_INVITE_RECEIVE_ALL_REWARDS("Event_5000007", false, "好友招募领取完所有奖励 "),

    /** 兽潮周活动期间个人杀怪的次数（跨服） */


    ROLE_ACTIVITY_KILL_MONSTER_TIMES("Event_2660017", false, "兽潮周活动期间个人杀怪的次数（跨服）Ds->diff{ role.getActivity().getActivityMap().get(activityTag).getRoleTimes()}"),
    /** 兽潮周活动期间当前所有跨服玩家杀怪的次数（跨服） */
    SERVER_ACTIVITY_KILL_MONSTER_TIMES("Event_2660018", false, "兽潮周活动期间当前所有跨服玩家杀怪的次数（跨服）Ds->diff{ role.getActivity().getActivityMap().get(activityTag).getServerTimes()}"),

    // 问卷调查活动
    /** 问卷调查活动领取完所有奖励 */
    QUESTIONNAIRE_ACTIVITY_RECEIVE_ALL_REWARD("Event_5000005", false, "问卷调查活动领取完所有奖励 Ds->diff{ role.getActivity().getActivityMap().get(activityTag).getReceivedReward()}"),
    /** 玩家当前城市体型是否是a */
    ROLE_CURRENT_BODY_TYPE("Event_1010603", true, "玩家当前城市体型是否是a Ds->diff{role.getBase().getRoleTag()}"),

    // 城市升级赠礼
    /** 领取城市成长赠礼活动中的所有奖励 */
    URBAN_UP_RECEIVE_ALL_REWARD("Event_5000008", false, "领取城市成长赠礼活动中的所有奖励 "),


    // 使命令状
    /** 活动期间内获取日常任务活跃度+周常活跃度 */
    BATTLE_PASS_GAIN_POINT("Event_2660019", true, true, "活动期间内获取日常任务活跃度+周常活跃度"),

    // 首充活动
    /** 领取完首充活动的所有奖励 */
    FIRST_CHARGE_ACTIVITY_RECEIVE_ALL_REWARD("Event_5000009", true, "领取完首充活动的所有奖励 Ds->diff{ role.getActivity().getActivityMap().get(activityTag).getRewardReceiveMap().size()"),
    // 充值礼包
    /** 领取完新首充礼包中的所有奖励 */
    CHARGE_PACK_ACTIVITY_RECEIVE_ALL_REWARD("Event_5000011", true, "领取完新首充礼包中的所有奖励 Ds->diff{ role.getActivity().getActivityMap().get(activityTag).getRewardReceiveMap().size()"),

    // 历史累计
    /** 累计获得任意古宝的数量 */
    TOTAL_RELIC("Event_1010301", true, "累计获得任意古宝的数量,Ds->diff{ role.getRelic().getRelicMap().size() }"),
    /** 累计给任意古宝升星的星数 */
    TOTAL_RELIC_REPAIR("Event_1032004", true, "累计给任意古宝升星的星数,Ds->counter{CounterType.RELIC_REPAIR,CounterResetType.NEVER }"),
    /** 玩家历史最高战力值 */
    TOTAL_HISTORY_MAX_POWER("Event_1320001", true, "玩家历史最高战力值,Ds->diff{ role.getPower().getHistoryMaxPower() }"),
    /** 累计挑战猎场传送平台的次数 */
    TOTAL_HUNTING_GROUND_TRANSMISSION_CHALLENGE("Event_1651001", true, "累计挑战猎场传送平台的次数,Ds->counter{CounterType.HUNTING_GROUND_TRANSMISSION_CHALLENGE,CounterResetType.NEVER }"),
    /**
     * 累计在线活动结束
     * 实际含义是玩家累计登陆超过(大于)x天
     */
    TOTAL_ONLINE_ACTIVITY_LOGIN_DAY_MORE_THEN("Event_5000002", true, "累计在线活动结束(含义与描述不一致，实际含义是玩家累计登陆超过(大于)x天),Ds->diff{ role.getActivity().getActivityMap(activityTag).getLoginDay() }"),
    /** 七日登陆活动结束条件 */
    TOTAL_LOGIN_ACTIVITY_END("Event_5000001", true, "七日登陆活动结束条件,Ds->diff{ role.getActivity().getActivityMap }"),
    /** 七星活动结束条件 */
    TOTAL_HEBDOMADTASK_ACTIVITY_END("Event_5000003", true, "七星活动结束条件,Ds->diff{ role.getActivity().getActivityMap }"),

    // 成长基金
    /** 领取完成长基金a活动tag的所有奖励 */
    GROWTH_FUND_RECEIVE_ALL_REWARD("Event_5000010", true, "领取完成长基金a活动tag的所有奖励, Ds->diff{ role.getActivity().getActivityMap(activityTag) }"),


    // 异界入侵相关事件（只适用于本期活动到下期活动开始）
    /** 活动期间入侵次数 */
    CROSS_SERVER_COMBAT_ATTACK_NUM("Event_2660012", true, "活动期间入侵次数"),
    /** 单次入侵最高获得积分 */
    CROSS_SERVER_COMBAT_ATTACK_MAX_SCORE("Event_2660013", true, "单次入侵最高获得积分"),
    /** 单次入侵最高杀人数 */
    CROSS_SERVER_COMBAT_ATTACK_MAX_KILL_PLAYER_NUM("Event_2660014", true, "单次入侵最高杀人数"),
    /** 活动期间杀人数 */
    CROSS_SERVER_COMBAT_ACTIVITY_KILL_PLAYER_NUM("Event_2660015", true, true, "活动期间杀人数"),
    /** 活动期间积分总和 */
    CROSS_SERVER_COMBAT_ACTIVITY_TOTAL_SCORE("Event_2660016", true, true, "活动期间积分总和"),

    // 护卫
    /** 指定 tag 的护卫品质大于等于 x */
    GUARD_TAG_QUALITY("Event_1500005", true, "指定 tag 的护卫品质大于等于 x, Ds->diff{role.getGuard().getGuards()}"),

    // 农场
    /** 农场解锁的地块数量大于等于XX */
    REFINERY_UNLOCK_LAND_NUM("Event_1003002", true, "农场解锁的地块数量大于等于XX, Ds->diff{role.getRefinery().getLands()}"),

    // 一段时间内事件
    /** 接受任务后制造任意装备的数量 */
    TIME_EQUIP_MAKE("Event_2001001", false, "接受任务后制造任意装备的数量,Ds->counter{CounterType.EQUIP_MAKE,CounterResetType.NEVER }"),
    /** 接受任务后在商店购买商品的次数 */
    TIME_SHOP_BUY_TAG("Event_2011001", false, "接受任务后在商店购买商品的次数,Ds->counter{CounterType.SHOP_BUY,CounterResetType.NEVER,CounterQueryIndex.SHOP_BUY_BY_TAG=? }"),
    /** 接受任务后饮用任意饮料的数量 */
    TIME_HAD_DRINKS("Event_2023000", false, "接受任务后饮用任意饮料的数量,Ds->counter{CounterType.HAD_DRINKS,CounterResetType.NEVER }"),
    /** 接受任务后玩家任意技能升级的等级数 */
    TIME_SKILL_LEVEL_UP("Event_2100003", false, "接受任务后玩家任意技能升级的等级数,Ds->counter{CounterType.SKILL_LEVEL_UP_TOTAL,CounterResetType.NEVER }"),
    /** 接受任务后解读书籍的次数 */
    TIME_BOOK_LEARN("Event_2160002", false, "接受任务后解读书籍的次数,Ds->counter{CounterType.BOOK_LEARN,CounterResetType.NEVER }"),
    /** 接受任务后玩家爆发次数 */
    TIME_ROLE_TRAINING_BURST("Event_2340001", false, "接受任务后玩家爆发次数,Ds->counter{CounterType.BURST,CounterResetType.NEVER }"),
    /** 接受任务后挑战猎场传送平台的次数 */
    TIME_HUNTING_GROUND_TRANSMISSION_CHALLENGE("Event_2651001", false, "接受任务后挑战猎场传送平台的次数,Ds->counter{CounterType.HUNTING_GROUND_TRANSMISSION_CHALLENGE,CounterResetType.NEVER }"),
    /** 接受任务后试炼场挑战次数 */
    TIME_TRIAL_YARD_CHALLENGE("Event_2650002", false, "接受任务后试炼场挑战次数,Ds->counter{CounterType.TRIAL_YARD_CHALLENGE,CounterResetType.NEVER }"),
    /** 接受任务后登陆游戏的天数 */
    TIME_LOGIN_DAY("Event_2800001", false, "接受任务后登陆游戏的天数,Ds->counter{CounterType.LOGIN_DAY_NUM,CounterResetType.NEVER }"),
    /** 接受任务后鉴定的装备数 */
    TIME_ITEM_EQUIP_APPRAISAL("Event_1001021", false, "接受任务后鉴定的装备数"),
    /** 接受任务后获得接受任务后获得XX品质的核心的数量（任意来源，获得就算） */
    TIME_GAIN_CORE_QUALITY("Event_1001024", false, "接受任务后获得接受任务后获得XX品质的核心的数量（任意来源，获得就算）Ds->counter{CounterType.EQUIP_MAKE, CounterResetType.NEVER, CounterQueryIndex.EQUIP_MAKE_BY_QUALITY=?}"),
    /** 接受任务后获得XX品质XX类型装备的数量（任意来源，获得就算。且类型允许配数组） */
    TIME_GAIN_EQUIP_QUALITY_TYPE("Event_1001025", false, "接受任务后获得XX品质XX类型装备的数量（任意来源，获得就算。且类型允许配数组）Ds->counter{role.getCounter(), CounterService.Query.build(CounterType.EQUIP_MAKE, CounterResetType.NEVER, CounterQueryIndex.EQUIP_MAKE_BY_QUALITY=?}"),

    // 每日事件
    /** 今日制造任意饮料的数量 */
    DAILY_DRINK_MAKE("Event_3000001", true, true, "今日制造任意饮料的数量,Ds->counter{CounterType.DRINK_MAKE,CounterResetType.DAILY }"),
    /** 今日制造任意装备的数量 */
    DAILY_EQUIP_MAKE("Event_3001001", true, true, "今日制造任意装备的数量,Ds->counter{CounterType.EQUIP_MAKE,CounterResetType.DAILY }"),
    /** 今日购买商品的次数 */
    DAILY_SHOP_BUY_TAG("Event_3011001", true, true, "今日购买商品的次数,Ds->counter{CounterType.SHOP_BUY,CounterResetType.DAILY ,CounterQueryIndex.SHOP_BUY_BY_TA=?}"),
    /** 今日刷新的次数 */
    DAILY_SHOP_RESET_TAG("Event_3800006", true, true, "今日刷新的次数,Ds->counter{CounterType.SHOP_RESET,CounterResetType.DAILY ,CounterQueryIndex.SHOP_RESET_BY_TAG=?}"),
    /** 今日饮用任意饮料的数量 */
    DAILY_DRINK_USE("Event_3023000", true, true, "今日饮用任意饮料的数量,Ds->counter{CounterType.ITEM_USE,CounterResetType.DAILY,CounterQueryIndex.ITEM_USE_BY_FUNCTION_TYPE=ItemFunctionType.EXP_DRINK.getType() }"),
    /** 今日强化装备的等级数 */
    DAILY_EQUIP_STRENGTHEN("Event_3031003", true, true, "今日强化装备的等级数,Ds->counter{CounterType.EQUIP_STRENGTHEN,CounterResetType.DAILY }"),
    /** 今日玩家任意技能升级的等级数 */
    DAILY_SKILL_LEVEL_UP("Event_3100003", true, true, "今日玩家任意技能升级的等级数,Ds->counter{CounterType.SKILL_LEVEL_UP_TOTAL,CounterResetType.DAILY }"),
    /** 今日解读书籍的次数 */
    DAILY_BOOK_LEARN("Event_3160002", true, true, "今日解读书籍的次数,Ds->counter{CounterType.BOOK_LEARN,CounterResetType.DAILY }"),
    /** 今日玩家爆发次数 */
    DAILY_ROLE_TRAINING_BURST("Event_3340001", true, true, "今日玩家爆发次数,Ds->counter{CounterType.BURST,CounterResetType.DAILY }"),
    /** 今日试炼场挑战次数 */
    DAILY_TRIAL_YARD_CHALLENGE("Event_3650002", true, true, "今日试炼场挑战次数,Ds->counter{CounterType.TRIAL_YARD_CHALLENGE,CounterResetType.DAILY }"),
    /** 今日挑战猎场传送平台的次数 */
    DAILY_HUNTING_GROUND_TRANSMISSION_CHALLENGE("Event_3651001", true, true, "今日挑战猎场传送平台的次数,Ds->counter{CounterType.HUNTING_GROUND_TRANSMISSION_CHALLENGE,CounterResetType.DAILY }"),
    /** 今日登陆游戏的天数 */
    DAILY_LOGIN_DAY("Event_3800001", true, true, "今日登陆游戏的天数,Ds->counter{CounterType.LOGIN_DAY_NUM,CounterResetType.DAILY }"),
    /** 今日领取悬赏任务的次数 */
    DAILY_REWARD_TASK_RECEIVED("Event_3800003", true, true, "今日领取悬赏任务的次数,Ds->counter{CounterType.REWARD_TASK_RECEIVED,CounterResetType.DAILY }"),
    /** 每日消耗x个tag为A的道具 */
    DAILY_ITEM_COST_TAG("Event_ItemCostTimeDaily", true, true, "每日使用x个tag为A的道具,Ds->counter{CounterType.ITEM_COST,CounterResetType.DAILY"),

    // 每周事件
    /** 本周制造任意饮料的数量 */
    WEEKLY_DRINK_MAKE("Event_4000001", true, true, "本周制造任意饮料的数量,Ds->counter{CounterType.DRINK_MAKE,CounterResetType.WEEKLY }"),
    /** 本周制造任意装备的数量 */
    WEEKLY_EQUIP_MAKE("Event_4001001", true, true, "本周制造任意装备的数量,Ds->counter{CounterType.EQUIP_MAKE,CounterResetType.WEEKLY }"),
    /** 本周购买商品的次数 */
    WEEKLY_SHOP_BUY_TAG("Event_4011001", true, true, "本周购买商品的次数,Ds->counter{CounterType.SHOP_BUY,CounterResetType.WEEKLY,CounterQueryIndex.SHOP_BUY_BY_TAG=? }"),
    /** 本周饮用任意饮料的数量 */
    WEEKLY_DRINK_USE("Event_4023000", true, true, "本周饮用任意饮料的数量,Ds->counter{CounterType.ITEM_USE,CounterResetType.WEEKLY,CounterQueryIndex.ITEM_USE_BY_FUNCTION_TYPE=ItemFunctionType.EXP_DRINK.getType() }"),
    /** 本周强化装备的等级数 */
    WEEKLY_EQUIP_STRENGTHEN("Event_4031003", true, true, "本周强化装备的等级数,Ds->counter{CounterType.EQUIP_STRENGTHEN,CounterResetType.WEEKLY }"),
    /** 本周玩家任意技能升级的等级数 */
    WEEKLY_SKILL_LEVEL_UP("Event_4100003", true, true, "本周玩家任意技能升级的等级数,Ds->counter{CounterType.SKILL_LEVEL_UP_TOTAL,CounterResetType.WEEKLY }"),
    /** 本周解读书籍的次数 */
    WEEKLY_BOOK_LEARN("Event_4160002", true, true, "本周解读书籍的次数,Ds->counter{CounterType.BOOK_LEARN,CounterResetType.WEEKLY }"),
    /** 本周玩家爆发次数 */
    WEEKLY_ROLE_TRAINING_BURST("Event_4340001", true, true, "本周玩家爆发次数,Ds->counter{CounterType.BURST,CounterResetType.WEEKLY }"),
    /** 本周试炼场挑战次数 */
    WEEKLY_TRIAL_YARD_CHALLENGE("Event_4650002", true, true, "本周试炼场挑战次数,Ds->counter{CounterType.TRIAL_YARD_CHALLENGE,CounterResetType.WEEKLY }"),
    /** 本周挑战猎场传送平台的次数 */
    WEEKLY_HUNTING_GROUND_TRANSMISSION_CHALLENGE("Event_4651001", true, true, "本周挑战猎场传送平台的次数,Ds->counter{CounterType.HUNTING_GROUND_TRANSMISSION_CHALLENGE,CounterResetType.WEEKLY }"),
    /** 本周登陆游戏的天数 */
    WEEKLY_LOGIN_DAY("Event_4800001", true, true, "本周登陆游戏的天数,Ds->counter{CounterType.LOGIN_DAY_NUM,CounterResetType.WEEKLY }"),
    /** 本周累计使用x个参数a的货币 */
    WEEKLY_MONEY_COST_TAG("Event_MoneyUseNumberWeek", true, true, "本周累计使用x个参数a的货币,Ds->counter{CounterType.MONEY_COST,CounterResetType.WEEKLY"),
    /** 累计农场种植的次数 */
    TOTAL_REFINERY_REFINE_TIMES("Event_1003001", true, "累计农场种植的次数,Ds->counter{CounterType.REFINERY_REFINE,CounterResetType.NEVER }"),
    /** 历史获得过指定等级的护卫 */
    TOTAL_GUARD_LEVEL_GAIN("Event_1500002", true, "历史获得过指定等级的护卫,Ds->counter{CounterType.GUARD_LEVEL_NUMS,CounterResetType.NEVER ,CounterQueryIndex.GUARD_GAIN_NUM_LEVE=?}"),
    /** 历史获得过指定品质的护卫 */
    TOTAL_GUARD_QUALITY_GAIN("Event_1500003", true, "历史获得过指定品质的护卫,Ds->counter{CounterType.GUARD_QUALITY_NUMS,CounterResetType.NEVER,CounterQueryIndex.GUARD_GAIN_NUM_QUALITY=? }"),
    /** 本日参与公会建设的次数 */
    GUILD_CULTIVATING_BUILD_TODAY_TIMES("Event_6000002", true, true, "本日参与公会建设的次数,Ds->counter{CounterType.GUILD_BUILD,CounterResetType.DAILY }"),
    /** 今日竞技场挑战的次数（不论输赢） */
    ARENA_CHALLENGE_TIME_TODAY("Event_3800002", true, true, "今日竞技场挑战的次数（不论输赢),Ds->counter{CounterType.ARENA_CHALLENGE,CounterResetType.DAILY }"),
    /** 累计竞技场挑战的次数（不论输赢） */
    ARENA_CHALLENGE_TIME_TOTAL("Event_1900002", true, "累计竞技场挑战的次数（不论输赢),Ds->counter{CounterType.ARENA_CHALLENGE,CounterResetType.NEVER }"),
    /** 本周竞技场挑战的次数（不论输赢） */
    ARENA_CHALLENGE_TIME_WEEKLY("Event_3810002", true, true, "累计竞技场挑战的次数（不论输赢),Ds->counter{CounterType.ARENA_CHALLENGE,CounterResetType.WEEKLY }"),
    /** 今日参与活动狩猎巨兽的次数 */
    TODAY_HUNTING_MONSTER_JOIN("Event_3800004", true, true, "今日参与活动狩猎巨兽的次数,Ds->counter{CounterType.HUNTING_MONSTER_JOIN,CounterResetType.DAILY }"),
    /** 累计参与活动狩猎巨兽的次数 */
    TOTAL_HUNTING_MONSTER_JOIN("Event_1900003", true, "累计参与活动狩猎巨兽的次数,Ds->counter{CounterType.HUNTING_MONSTER_JOIN,CounterResetType.NEVER }"),
    /** 接受任务后参与活动狩猎巨兽的次数 */
    TIME_HUNTING_MONSTER_JOIN("Event_2660004", false, "接受任务后参与活动狩猎巨兽的次数,Ds->counter{CounterType.HUNTING_MONSTER_JOIN,CounterResetType.NEVER }"),
    /** 接受任务后参与竞技场挑战的次数 */
    TIME_ARENA_CHALLENGE_TIME("Event_2660006", false, "接受任务后参与竞技场挑战的次数,Ds->counter{CounterType.ARENA_CHALLENGE,CounterResetType.NEVER }"),
    /** 接受任务后完成公会建设的次数 */
    TIME_GUILD_CULTIVATING_BUILD_TIMES("Event_2660007", false, "接受任务后完成公会建设的次数,Ds->counter{CounterType.GUILD_BUILD,CounterResetType.NEVER }"),
    /** 当前工会等级 */
    ROLE_GUILD_LEVEL("Event_6000003", true, true, "当前工会等级,Ds->diff{ role.getGuild().getLevel() }"),
    /** 本周参与活动狩猎巨兽的次数 */
    WEEKLY_HUNTING_MONSTER_JOIN("Event_3800014", true, true, "本周参与活动狩猎巨兽的次数,Ds->counter{CounterType.HUNTING_MONSTER_JOIN,CounterResetType.WEEKLY }"),
    /** 本命最大等级达到（不论物理或元素方向） */
    ROLE_WING_MAN_LEVEL("Event_1300011", true, "本命最大等级达到（不论物理或元素方向,Ds->diff{  role.getWingMan().getLevel() }"),
    /** 每日采矿次数 */
    DAILY_ORE_MINING_TIMES("Event_MiningNumberDaily1", true, true, "每日采矿次数,Ds->counter{CounterType.ORE_MINING,CounterResetType.DAILY }"),
    /** 每周采矿时间，单位秒 */
    WEEKLY_ORE_MINING_TIME("Event_MiningTimeWeek1", true, true, "每周采矿时间，单位秒,Ds->diff{ role.getMineralGround().getWeeklyMiningTime() }"),
    /** 本周累积获得X个任意货币的数量 */
    WEEKLY_MONEY_GAIN_CNT("Event_MoneyCollectionWeek", true, true, "本周累积获得任意货币的数量,Ds->counter{CounterType.MONEY_GAIN,CounterResetType.WEEKLY }"),
    /** 每触发a的被数次商店（被触发器成功触发显示的商店） */
    TRIGGER_SHOP_TRIGGER_TIMES("Event_1800003", true, true, "触发商店被触发的次数 % a == 0,Ds->counter{CounterType.TRIGGER_SHOP,CounterResetType.NEVER}"),
    /** 接受任务后消耗参数a的货币总数量（参数a为数组） */
    TASK_MULTIPLE_MONEY_CONSUME("Event_MoneyConsumeTask", false, "接受任务后消耗参数a的货币总数量（参数a为数组）Ds->counter{CounterType.MONEY_COST,CounterQueryIndex.MONEY_COST_BY_TAG,CounterResetType.NEVER }"),
    /** 接受任务后消耗参数a的货币总数量（参数a为数组） */
    TOTAL_MULTIPLE_MONEY_CONSUME("Event_MoneyConsumeTotal", true, "接受任务后消耗参数a的货币总数量（参数a为数组）Ds->counter{CounterType.MONEY_COST,CounterQueryIndex.MONEY_COST_BY_TAG,CounterResetType.NEVER }"),
    /** 接受任务后获得参数a的货币（money类型）总数量（参数a为数组） */
    TASK_MULTIPLE_MONEY_GAIN("Event_MoneyGetTask", false, "接受任务后获得参数a的货币（money类型）总数量（参数a为数组）Ds->counter{CounterType.MONEY_GAIN,CounterQueryIndex.MONEY_GAIN_BY_TAG,CounterResetType.NEVER }"),
    /** 累计获得参数a的货币（money）总数量（参数a为数组） */
    TOTAL_MULTIPLE_MONEY_GAIN("Event_MoneyGetTotal", true, "累计获得参数a的货币（money）总数量（参数a为数组）Ds->counter{CounterType.MONEY_GAIN,CounterQueryIndex.MONEY_GAIN_BY_TAG,CounterResetType.NEVER }"),
    /** 接任务后装备强化的次数（装备强化这一行为的次数） */
    TASK_EQUIP_STRENGTH("Event_EquipStrengthTask", false, "接任务后装备强化的次数（装备强化这一行为的次数）Ds->counter{CounterType.EQUIP_STRENGTHEN,CounterResetType.NEVER }"),
    /** 接任务后藏品研究的次数（藏品研究这一行为的次数） */
    TASK_RELIC_RESEARCH("Event_RelicResearchTask", false, "接任务后藏品研究的次数（藏品研究这一行为的次数）Ds->counter{CounterType.RELIC_RESEARCH,CounterResetType.NEVER }"),
    /** 接任务后种植的次数（种植这一行为的次数） */
    TASK_PLANT_TIMES("Event_PlantTimesTask", false, "接任务后种植的次数（种植这一行为的次数）Ds->counter{CounterType.REFINERY_REFINE,CounterResetType.NEVER }"),
    /** 累计获取的物理方向经验量 */
    TOTAL_TRAININGEXP_PHYSICS("Event_TrainingExpPhysics", true, "累计获取的物理方向经验量"),
    /** 累计获取的元素方向经验量 */
    TOTAL_TRAININGEXP_ELEMENT("Event_TrainingExpElement", true, "累计获取的元素方向经验量"),
    /** 玩家通过了参数为a的主线关卡 */
    TOTAL_MAINSTAGE_PASS("Event_MainStagePass", true, "玩家通过了参数为a的主线关卡（a：MainStage表的tag) Ds -> diff{RoleMainlineMopup.currentLevel.cfg.serial > targetLeve.cfg.serial}"),
    /** 玩家未通过参数为a的主线关卡 */
    TOTAL_MAINSTAGE_NO_PASS("Event_MainStageNoPass", true, "玩家未通过参数为a的主线关卡（a：MainStage表的tag）Ds -> diff{RoleMainlineMopup.currentLevel.cfg.serial <= targetLeve.cfg.serial}"),

    /** 本日挑战boss次数 */
    BOSS_CHALLENGE_TIME_DAILY("Event_BossChallengeTimeDaily", true, true, "本日挑战boss次数,Ds->counter{CounterType.BOSS_CHALLENGE,CounterResetType.DAILY }"),
    /** 本周挑战boss次数 */
    BOSS_CHALLENGE_TIME_WEEK("Event_BossChallengeTimeWeek", true, true, "本周挑战boss次数,Ds->counter{CounterType.MONEY_GAIN,CounterResetType.WEEKLY }"),

    /** 本日使用x次火把 */
    ARTILLERY_USE_TIME_DAILY("Event_ArtilleryUseTimeDaily", true, true, "本日使用x次火把,Ds->counter{CounterType.USE_ARTILLERY,CounterResetType.DAILY }"),
    /** 本周使用x次火把 */
    ARTILLERY_USE_TIME_WEEK("Event_ArtilleryUseTimeWeek", true, true, "本周使用x次火把,Ds->counter{CounterType.USE_ARTILLERY,CounterResetType.WEEKLY }"),

    /** 本日领取挂机奖励 */
    COLLECTION_REWARD_TIME_DAILY("Event_CollectionRewardTimeDaily", true, true, "本日领取挂机奖励,Ds->counter{CounterType.COLLECTION_REWARD,CounterResetType.DAILY }"),
    /** 本周领取挂机奖励 */
    COLLECTION_REWARD_TIME_WEEK("Event_CollectionRewardTimeWeek", true, true, "本周领取挂机奖励,Ds->counter{CounterType.COLLECTION_REWARD,CounterResetType.WEEKLY }"),

    /** 本日工坊种植次数 */
    WORK_SHOP_TIME_DAILY("Event_WorkshopWorkTimeDaily", true, true, "本日工坊种植次数,Ds->counter{CounterType.REFINERY_REFINE,CounterResetType.DAILY }"),
    /** 本周工坊种植次数 */
    WORK_SHOP_TIME_WEEK("Event_WorkshopWorkTimeWeek", true, true, "本周工坊种植次数,Ds->counter{CounterType.REFINERY_REFINE,CounterResetType.WEEKLY }"),

    /** 玩家挑战了参数为a的主线关卡（胜利或失败）（a为MainStage表的tag) */
    TOTAL_MAINSTAGE_CHALLENGE("Event_MainStageChallenge", true, "玩家挑战了参数为a的主线关卡 Ds -> diff{RoleMainlineMopup.currentLevel.cfg.serial >= targetLeve.cfg.serial}"),

    /** 玩家当前穿戴的装备数量大于等于@ */
    WEAR_EQUIP_NUM("Event_EquipWearNumOver", true, "玩家当前穿戴的装备数量大于等于@ Ds->equipService.getCurrentWearEquipMap(role).size() }"),

    /** 玩家当前上阵护卫的数量大于等于@ */
    GUARD_USE_NUM("Event_GuardUseNumOver", true, " 玩家当前上阵护卫的数量大于等于@ Ds->Ds -> diff{RoleGuard.outwardGuards.size  }"),

    /** 玩家获得的物理方向经验足够升级到参数a（a：RoleLevelUp表的tag）（物理方向） */
    ROLE_TRAINING_LEVEL_PHYSICS("Event_ExpLevelPhysics", true, "玩家当前训练方向,Ds->diff{role.getTraining().getTrainingProgressMap().get(OccupationType.PHYSICAL.getType().getExp,and ExpCurrency}"),

    /** 玩家获得的元素方向经验足够升级到参数a（a：RoleLevelUp表的tag）（元素方向） */
    ROLE_TRAINING_LEVEL_ELEMENT("Event_ExpLevelElement", true, "玩家当前训练方向,Ds->diff{role.getTraining().getTrainingProgressMap().get(OccupationType.ELEM.getType().getExp and ExpCurrency}"),

    /** 玩家通过了参数为a的主线关卡（a：MainStage表的tag）的第b波怪物 */
    TOTAL_MAINSTAGE_WAVE_PASS("Event_MainStageWavePass", true, ""),

    // 抽奖
    /** 本日在a卡池抽卡的次数 */
    DRAW_TIMES_DAILY("Event_DrawTimesDaily", true, true, "Ds->counter{CounterType.DRAW_CARD,CounterResetType.DAILY,CounterQueryIndex.DRAW_CARD_BY_TAG=? }"),
    /** 本周在a卡池抽卡的次数 */
    DRAW_TIMES_WEEKLY("Event_DrawTimesWeekly", true, true, "Ds->counter{CounterType.DRAW_CARD,CounterResetType.WEEKLY,CounterQueryIndex.DRAW_CARD_BY_TAG=? }"),
    /** 历史累计在a卡池抽卡的次数 */
    DRAW_TIMES_TOTAL("Event_DrawTimes", true, "Ds->counter{CounterType.DRAW_CARD,CounterResetType.NEVER,CounterQueryIndex.DRAW_CARD_BY_TAG=? }"),
    /** a活动tag曾开启过（开启过或开启中都算） */
    EVENT_EVENTS_OPENED("Event_EventsOpened", true, "Ds->logic{activityService.getStatus != Lock}"),

    /** 本周黄金矿工累计获得分数 */
    GOLD_MINER_SCORE_WEEKLY("Event_goldMinerScoreWeekly", true, true, "Ds->role.getMiniGame().getMiner().getAccScore()"),
    /** 历史累计跨服竞技场名次 */
    EVENT_CROSS_ARENA_RANK("Event_CrossArena", true, true, "Ds->counter{CounterType.CROSS_ARENA_RANK,CounterResetType.NEVER"),


    /** 本日领取任意广告礼包的数量 */
    RECEIVE_AD_PACK_TIMES_DAILY("Event_ADPackTimesDaily", true, true, "今日领取礼包,Ds->counter{CounterType.SHOP_BUY,CounterResetType.DAILY ,CounterQueryIndex.SHOP_BUY_BY_TA=?}"),

    /** 累计领取任意广告礼包的数量 */
    RECEIVE_AD_PACK_TIMES_TOTAL("Event_ADPackTimesTotal", true, true, "累计领取礼包数量，Ds->counter{CounterType.DRAW_CARD,CounterResetType.NEVER,CounterQueryIndex.DRAW_CARD_BY_TAG=? }"),

    /** 本周参与公会采集的次数（获取过采集经验即为达成） */
    GUILD_MINING_TIMES_WEEK("Event_GuildMiningTimesWeek", true, true, "Ds->counter{CounterType.GUILD_GATHER,CounterResetType.WEEKLY}"),
    /** 累计参与公会乱斗的次数（入场乱斗地图即为达成） */
    GUILD_COMBAT_TIMES_TOTAL("Event_GuildCombatTimesTotal", true, true, "Ds->counter{CounterType.JOIN_GUILD_COMBAT,CounterResetType.NEVER}"),
    /** 本周参与公会乱斗的次数（入场乱斗地图即为达成） */
    GUILD_COMBAT_TIMES_WEEK("Event_GuildCombatTimesWeek", true, true, "Ds->counter{CounterType.JOIN_GUILD_COMBAT,CounterResetType.WEEKLY}"),
    /** 本日完成公会委托的数量(接受委托即为达成） */
    GUILD_COMMISSION_DAILY("Event_guildCommissionDaily", true, true, "Ds->counter{CounterType.GUILD_COMMISSION,CounterResetType.DAILY}"),
    /** 本周参与过公会讨伐的次数（对公会讨伐里的怪物造成过任意伤害即为参与） */
    GUILD_BOSS_TIMES_WEEKLY("Event_guildCrusadeWeekly", true, true, "Ds->counter{CounterType.GUILD_BOSS,CounterResetType.WEEKLY}"),
    /** 本周参与过公会对决的次数（在活动中主动发起过任意一场战斗即为参与） */
    GUILD_BATTLE_TIMES_WEEKLY("Event_guildBatWeekly", true, true, "Ds->counter{CounterType.GUILD_DUEL,CounterResetType.WEEKLY}"),

    /** 陨石雨领完奖励 */
    METEOR_FINISH("Event_meteorShowerFinish", true, false, ""),

    // 新版开宝箱
    /** 宝箱等级等于@ */
    OPEN_CHEST_LEVEL("Event_OpenChestLevel", true, true, "Ds->role.getOpenChest().getChestLevel()"),
    /** 宝箱等级达到@（大于等于宝箱等级） */
    OPEN_CHEST_LEVEL_LE("Event_OpenChestLevel01", true, true, "Ds->role.getOpenChest().getChestLevel()"),
    /** 是否达到开宝箱可获得挑战书的上限（@1达到） */
    OPEN_CHEST_CHALLENGE_ITEM_MAX("Event_openChestChallengeItemMax", true, true, ""),
    /** 今日锤炼次数 */
    OPEN_CHEST_TIMES_DAILY("Event_openChestTimesDaily", true, true, ""),

    // 新版装备
    /** 接受任务后分解装备的数量达到@ */
    EQUIP_NEW_DECOMPOSE_NUM_TIME("Event_EquipBreakTimeTask", false, "Ds->role.getEquipNew().getDecomposeCount()"),
    /** 玩家穿戴的a品质及以上的装备的数量（a为品质数字，1,2,3……）达到@ */
    EQUIP_NEW_WEAR_NUM_QUALITY("Event_EquipWearNumQuality", true, "Ds->role.getEquipNew()"),

    /** 通关镇妖塔@层（@后的数为TrialTower表level列值，即第几层） */
    TRIAL_TOWER_PASS("Event_TrialTowerPass", true, false, "Ds->role.getTrialTower().getLevel()"),
    /** 仙友好感度等级达到 */
    FAIRY_FRIEND_LEVEL("Event_fairyFriendLevel", true, false, "Ds->role.getFairyFriend.getLevel().getFairyFriendsMap().get(tag).getLevelTag()"),
    /** 累计竞技场胜利次数 */
    ARENA_VICTORY_TIMES("Event_arenaVictoryTimes", true, false, "Ds->counter{CounterType.ARENA_CHALLENGE_WIM, CounterResetType.NEVER}"),

    /** 累计获得荒原传奇的数量 */
    LEGEND_GET_NUM("Event_legendGetNum", true, true, "Ds->role.getWildLegend().getLegendModelMap().size()"),
    /** 本日内升级荒原传奇的次数 */
    LEGEND_LEVEL_UP_TIMES_TODAY("Event_legendLevelUpTimesToday", true, true, "Ds->counter{CounterType.WILD_LEGEND,CounterResetType.DAILY}"),
    LEGEND_LEVEL_UP_TIMES_TASK("Event_legendLevelUpTimesTask", false, false, "Ds->counter{CounterType.WILD_LEGEND,CounterResetType.NEVER}"),
    /** 玩家累计抽取荒原传奇次数 */
    LEGEND_RECRUIT_TIMES("Event_legendRecruitTimes", true, false, "Ds->counter{CounterType.WILD_LEGEND_DRAW,CounterResetType.NEVER}"),
    /** 接受任务后抽取荒原传奇次数 */
    LEGEND_RECRUIT_TIMES_TASK("Event_legendRecruitTimesTask", false, false, "Ds->counter{CounterType.WILD_LEGEND_DRAW,CounterResetType.NEVER}"),

    /** 挑战冒险关卡参数A胜利或失败 */
    MAIN_ADVENTURE_CHALLENGE("Event_MainAdventureChallenge", true, true, "Ds->role.getTrialYard().getLastChallengeLevel()"),
    /** 首冲礼包是否全部买完（@1达到） */
    FIRST_CHARGE_FINISH("Event_FirstChargeFinish", true, true, ""),

    // 宠物
    /** 本日内升级护卫的次数 */
    PET_LEVEL_UP_TIMES_DAILY("Event_escortLevelUpTimesToday", true, true, "本日内升级护卫的次数 Ds->counter{CounterType.PET_LEVEL_UP,CounterResetType.DAILY}"),
    PET_LEVEL_UP_TIMES_TASK("Event_escortLevelUpTimesTask", false, false, "接受任务后升级护卫的次数 Ds->counter{CounterType.PET_LEVEL_UP,CounterResetType.NEVER}"),
    /** 玩家累计进行升级护卫这的次数 */
    ESCORT_LEVEL_UP_TIMES_TOTAL("Event_escortLevelUpTimesTotal", true, false, "玩家累计进行升级护卫这的次数 Ds->counter{CounterType.PET_LEVEL_UP,CounterResetType.NEVER}"),
    /** 玩家护卫历史最高等级达到@ */
    ESCORT_LEVEL_HIGHEST("Event_escortLevelHighest", true, false, "玩家护卫历史最高等级达到 Ds->role.getPet().getHistoryMaxLevel()"),

    /** 本日内制造护卫的次数 */
    PET_SUMMON_TIMES_DAILY("Event_escortMakeTimesToday", true, true, "本日内制造护卫的次数 Ds->counter{CounterType.PET_SUMMON,CounterResetType.DAILY}"),
    PET_SUMMON_TIMES_TASK("Event_escortMakeTimesTask", false, false, "接受任务后制造护卫的次数 Ds->counter{CounterType.PET_SUMMON,CounterResetType.NEVER}"),
    /** 玩家累计制造战争机器次数 */
    ESCORT_MAKE_TIMES_TOTAL("Event_escortMakeTimesTotal", true, false, "本日内制造护卫的次数 Ds->counter{CounterType.PET_SUMMON,CounterResetType.NEVER}"),

    // 飞艇（座驾）
    // 本日内升级飞艇的次数
    AIRSHIP_EXP_UP_TIMES_DAILY("Event_airshipLevelUpTimesToday", true, true, "本日内升级飞艇的次数 Ds->counter{CounterType.AIRSHIP_EXP_UP,CounterResetType.DAILY}"),
    /** 接受任务后升级飞艇的次数 */
    AIRSHIP_EXP_UP_TIMES_TASK("Event_airshipLevelUpTimesTask", false, false, "接受任务后升级飞艇的次数 Ds->counter{CounterType.AIRSHIP_EXP_UP,CounterResetType.NEVER}"),
    /** 玩家累计进行升级飞艇的次数 */
    AIRSHIP_LEVEL_UP_TIMES_TOTAL("Event_airshipLevelUpTimesTotal", true, false, "玩家累计进行升级飞艇的次数 Ds->counter{CounterType.AIRSHIP_EXP_UP,CounterResetType.NEVER}"),
    /** 玩家座驾等级达到a阶段@级 */
    AIRSHIP_LEVEL("Event_airshipLevel", true, false, "玩家座驾等级达到 Ds-> role.getAirship().getGradeTag() && role.getAirship().getLevel()"),

    /** 本日内进行异兽入侵挑战的次数 */
    CRUSADE_BOSS_TIMES_DAILY("Event_crusadeBossTimesToday", true, true, "本日内进行异兽入侵挑战的次数 Ds->counter{CounterType.CRUSADE_BOSS_CHALLENGE,CounterResetType.DAILY}"),
    CRUSADE_BOSS_TIMES_TASK("Event_crusadeBossTimesTask", false, false, "接受任务后进行异兽入侵挑战的次数 Ds->counter{CounterType.CRUSADE_BOSS_CHALLENGE,CounterResetType.NEVER}"),
    /** 玩家累计参与异兽入侵的次数 */
    CRUSADE_BOSS_TIMES_TOTAL("Event_crusadeBossTimesTotal", true, false, "玩家累计参与异兽入侵的次数 Ds->counter{CounterType.CRUSADE_BOSS_CHALLENGE,CounterResetType.NEVER}"),

    // 战令(寻道)
    /** 领完tag为a的战令的所有奖励（包括免费和付费奖励） */
    BATTLE_PASS_NEW_FINISH("Event_battlePassFinish", true, false, "领完tag为a的战令的所有奖励（包括免费和付费奖励） Ds -> "),
    /** 接受任务后仙友赠礼的次数 */
    FAIRY_FRIEND_GIFT_TASK("Event_fairyGiftTimesTask", false, false, "接受任务后仙友赠礼的次数 Ds->counter{CounterType.FAIRY_FRIEND_GIFT,CounterResetType.NEVER}"),
    /** 当前有护卫上阵 */
    EVENT_ESCORT_INTO_BATTLE("Event_escortIntoBattle", true, true, "当前有护卫上阵 Ds -> diff{RolePetData.wearPetId is not empty}");


    private static final Map<String, EventType> EVENT_TYPE_MAP = new HashMap<>();

    static {
        for (EventType type : EventType.values()) {
            EVENT_TYPE_MAP.put(type.getType(), type);
        }
    }


    private final String type;

    /** true:判断玩家历史数据  false：判断玩家段时间数据 */
    private final boolean origin;

    /** 可变的【可能由触发变为未触发】 */
    private final boolean variable;

    private final String desc;

    EventType(String type, boolean isOrigin, String desc) {
        this.type = type;
        this.origin = isOrigin;
        this.variable = false;
        this.desc = desc;
    }

    EventType(String type, boolean isOrigin, boolean variable, String desc) {
        this.type = type;
        this.origin = isOrigin;
        this.variable = variable;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public boolean isOrigin() {
        return origin;
    }

    public boolean isVariable() {
        return variable;
    }

    public String getDesc() {
        return desc;
    }
}
