package xyz.tjucomments.tjufood.dto;

import lombok.Data;

@Data
public class CaptchaVo {
    private String uuid;
    private String backgroundImage; // Base64编码的背景图
    private String puzzleImage;     // Base64编码的拼图
    private int y;                  // 拼图的垂直坐标
}