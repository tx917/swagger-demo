package com.jingai.swagger.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.Map;

public class JsonAnalysis {

    /**
     * {id:会员id,name:会员名称,age:会员年龄,speciality:[{name:特长名称}]}格式的json字符
     * 串转成标准json字符串显示在html中
     * @param json
     * @return
     */
    public static String convert2Html(String json) {
        if(!StringUtils.hasText(json)) {
            return "{}";
        }
        json = convertJson(json);
        StringBuilder sb = new StringBuilder();
        try {
            if(json.startsWith("[")) {
                final JSONArray objects = JSON.parseArray(json);
                final Iterator<Object> iterator = objects.iterator();
                sb.append("[{\n");
                final JSONObject jsonObject = (JSONObject)iterator.next();
                printJson(sb, jsonObject, 1);
                sb.append("}]\n");
            } else {
                final JSONObject jsonObject = JSON.parseObject(json);
                sb.append("{\n");
                printJson(sb, jsonObject, 1);
                sb.append("}");
            }
            int lastComma = sb.lastIndexOf(",");
            sb = sb.replace(lastComma, lastComma + 1, "");
        } catch(Exception e) {
            sb.append("{\n    errMsg: ").append("json字符串解析失败：").append(json).append(",\n");
            sb.append("    exception: ").append(e.getMessage()).append("\n}");
        }
        return sb.toString();
    }

    /**
     * 将json转换成带换行的json字符串
     * @param sb
     * @param jsonObject
     * @param setback
     */
    private static void printJson(StringBuilder sb, JSONObject jsonObject, int setback) {
        String back = getBackStr(setback);
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            if(entry.getValue() instanceof JSONObject) {
                sb.append(back).append(entry.getKey()).append(": {\n");
                printJson(sb, (JSONObject) entry.getValue(), setback + 1);
                sb.append(back).append("},\n");
            } else if(entry.getValue() instanceof JSONArray) {
                final Iterator<Object> iterator = ((JSONArray)entry.getValue()).iterator();
                sb.append(back).append(entry.getKey()).append(": [{\n");
                final JSONObject temp = (JSONObject)iterator.next();
                printJson(sb, temp, setback + 1);
                sb.append(back).append("}],\n");
            } else {
                sb.append(back).append(entry.getKey()).append(": ").append(entry.getValue()).append(",\n");
            }
        }
    }

    /**
     * 转换成标准的json字符串
     * @param json
     * @return
     */
    private static String convertJson(String json) {
        json = json.replace(" ", "");
        json = json.replace("{", "{\"").replace("}", "\"}");
        json = json.replace(":", "\":\"");
        json = json.replace(",", "\",\"");
        json = json.replace("\"[", "[");
        json = json.replace("]\"", "]");
        json = json.replace("\"{", "{");
        json = json.replace("}\"", "}");
        return json;
    }

    private static String getBackStr(int setback) {
        StringBuilder back = new StringBuilder();
        for (int i = 0; i < setback; i++) {
//            back.append("    ");
            back.append("&nbsp;&nbsp;&nbsp;&nbsp;");
        }
        return back.toString();
    }

}
