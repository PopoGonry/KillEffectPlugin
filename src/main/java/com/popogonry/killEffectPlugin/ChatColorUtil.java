package com.popogonry.killEffectPlugin;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.md_5.bungee.api.ChatColor;

public class ChatColorUtil {
    private static final Pattern RGB_PATTERN = Pattern.compile("&RGB\\{(\\d{1,3}),\\s*(\\d{1,3}),\\s*(\\d{1,3})\\}(.+)");

    public static String translateRGBColors(String message) {
        Matcher matcher = RGB_PATTERN.matcher(message);
        StringBuffer buffer = new StringBuffer();

        while(matcher.find()) {
            int r = Integer.parseInt(matcher.group(1));
            int g = Integer.parseInt(matcher.group(2));
            int b = Integer.parseInt(matcher.group(3));
            String text = matcher.group(4);
            r = Math.max(0, Math.min(255, r));
            g = Math.max(0, Math.min(255, g));
            b = Math.max(0, Math.min(255, b));
            ChatColor color = ChatColor.of(new Color(r, g, b));
            String var10000 = String.valueOf(color);
            String coloredText = var10000 + text + "§r";
            matcher.appendReplacement(buffer, coloredText);
        }

        matcher.appendTail(buffer);
        return buffer.toString();
    }
}