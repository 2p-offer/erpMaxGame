package com.erp.gameserver.test.json_net;

import java.util.List;
import java.util.Map;

public class BagMsg {

    // 使用道具
    public static class BagUseItemRequest {
        private static final long serialVersionUID = 1L;
        private ReqItem item;

        // Getter and Setter
        public ReqItem getItem() {
            return item;
        }

        public void setItem(ReqItem item) {
            this.item = item;
        }
    }

    public static class BagUseItemResponse {
        private static final long serialVersionUID = 1L;
        private List<RewardItem> reward;

        // Getter and Setter
        public List<RewardItem> getReward() {
            return reward;
        }

        public void setReward(List<RewardItem> reward) {
            this.reward = reward;
        }
    }

    public static class RewardItem {
        private String type;
        private String tag;
        private long num;
        private String id;
        private Map<String, String> cfgParam;
        private Map<String, String> extParam;

        // Getter and Setter
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public long getNum() {
            return num;
        }

        public void setNum(long num) {
            this.num = num;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Map<String, String> getCfgParam() {
            return cfgParam;
        }

        public void setCfgParam(Map<String, String> cfgParam) {
            this.cfgParam = cfgParam;
        }

        public Map<String, String> getExtParam() {
            return extParam;
        }

        public void setExtParam(Map<String, String> extParam) {
            this.extParam = extParam;
        }
    }

    public static class ReqItem {
        private String type;
        private String tag;
        private long num;

        // Getter and Setter
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public long getNum() {
            return num;
        }

        public void setNum(long num) {
            this.num = num;
        }
    }


}
