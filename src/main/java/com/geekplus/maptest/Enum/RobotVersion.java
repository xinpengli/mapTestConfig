package com.geekplus.maptest.Enum;

public enum RobotVersion {

 NEW,OLD;




    /**
    根据给的string（枚举值）,返回对应的枚举，
     values()就是当前枚举的所有值集合，
     */
    public static RobotVersion valueOfName(String name) {
        if (name == null || "".equals(name)) {
            return null;
        }

        for (RobotVersion ent : values()) {
            if (ent.name().equalsIgnoreCase(name)) {
                return ent;
            }
        }

        return null;
    }

}
