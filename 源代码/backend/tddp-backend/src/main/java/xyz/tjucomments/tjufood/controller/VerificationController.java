package xyz.tjucomments.tjufood.controller;

import cn.hutool.core.codec.Base64;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import xyz.tjucomments.tjufood.dto.Result;
// 新增引入
import xyz.tjucomments.tjufood.dto.SendCodeDTO;
import xyz.tjucomments.tjufood.service.IUserService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static xyz.tjucomments.tjufood.utils.constants.RedisConstants.LOGIN_CODE_KEY;
import static xyz.tjucomments.tjufood.utils.constants.RedisConstants.LOGIN_CODE_TTL;

@RestController
@RequestMapping("/api/verifications")
@Tag(name = "12. 验证码服务", description = "提供图形和邮箱验证码的接口")
public class VerificationController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    // 新增注入 IUserService
    @Resource
    private IUserService userService;

    private static final int PUZZLE_WIDTH = 50;
    private static final int PUZZLE_HEIGHT = 50;
    private static final int BG_IMAGE_WIDTH = 300;
    private static final int BG_IMAGE_HEIGHT = 150;


    @SneakyThrows
    @Operation(summary = "获取滑动拼图验证码")
    @GetMapping("/captcha")
    public Result getCaptcha() {
        // 1. 动态扫描并随机获取一张背景图片
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        org.springframework.core.io.Resource[] resources = resolver.getResources("classpath:images/*.png");

        if (resources.length == 0) {
            return Result.fail("验证码背景图片资源未找到，请检查 'src/main/resources/images' 目录");
        }
        org.springframework.core.io.Resource resource = resources[new Random().nextInt(resources.length)];

        // 2. 读取图片并调整为标准尺寸
        InputStream inputStream = resource.getInputStream();
        BufferedImage originalImage = ImageIO.read(inputStream);
        BufferedImage backgroundImage = new BufferedImage(BG_IMAGE_WIDTH, BG_IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        backgroundImage.getGraphics().drawImage(originalImage, 0, 0, BG_IMAGE_WIDTH, BG_IMAGE_HEIGHT, null);
        backgroundImage.getGraphics().dispose();

        // 3. 随机生成拼图的位置
        Random random = new Random();
        int puzzleX = random.nextInt(BG_IMAGE_WIDTH - PUZZLE_WIDTH * 2) + PUZZLE_WIDTH;
        int puzzleY = random.nextInt(BG_IMAGE_HEIGHT - PUZZLE_HEIGHT);

        // 4. 生成拼图块和带空缺的背景图
        BufferedImage puzzleImage = new BufferedImage(PUZZLE_WIDTH, PUZZLE_HEIGHT, BufferedImage.TYPE_INT_ARGB);

        // 创建一个Graphics2D对象，用于在背景图上绘制
        Graphics2D bgGraphics = backgroundImage.createGraphics();

        // 从背景图中抠出拼图块的像素
        int[][] puzzleData = new int[PUZZLE_WIDTH][PUZZLE_HEIGHT];
        for (int i = 0; i < PUZZLE_WIDTH; i++) {
            for (int j = 0; j < PUZZLE_HEIGHT; j++) {
                int rgb = backgroundImage.getRGB(puzzleX + i, puzzleY + j);
                puzzleData[i][j] = rgb;
                puzzleImage.setRGB(i, j, rgb);
            }
        }

        // 在背景图的抠图位置绘制一个半透明的灰色块，形成“空缺”效果
        bgGraphics.setColor(new Color(0, 0, 0, 128)); // 半透明黑色
        bgGraphics.fillRect(puzzleX, puzzleY, PUZZLE_WIDTH, PUZZLE_HEIGHT);
        bgGraphics.dispose();

        // 5. 将图片转为 Base64
        ByteArrayOutputStream puzzleStream = new ByteArrayOutputStream();
        ImageIO.write(puzzleImage, "png", puzzleStream);
        String puzzleBase64 = "data:image/png;base64," + Base64.encode(puzzleStream.toByteArray());

        ByteArrayOutputStream backgroundStream = new ByteArrayOutputStream();
        ImageIO.write(backgroundImage, "png", backgroundStream);
        String backgroundBase64 = "data:image/png;base64," + Base64.encode(backgroundStream.toByteArray());

        // 6. 生成 UUID 并将正确的 X 坐标存入 Redis
        String uuid = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + uuid, String.valueOf(puzzleX), LOGIN_CODE_TTL, TimeUnit.MINUTES);

        // 7. 封装数据并返回
        xyz.tjucomments.tjufood.dto.CaptchaVo captchaVo = new xyz.tjucomments.tjufood.dto.CaptchaVo();
        captchaVo.setUuid(uuid);
        captchaVo.setBackgroundImage(backgroundBase64);
        captchaVo.setPuzzleImage(puzzleBase64);
        captchaVo.setY(puzzleY);

        return Result.ok(captchaVo);
    }
    // ========== 新增方法 ==========
    @Operation(summary = "发送邮箱验证码")
    @PostMapping("/code")
    public Result sendCode(@RequestBody SendCodeDTO sendCodeDTO) {
        // 调用 Service 层中的方法
        return userService.sendCode(sendCodeDTO);
    }

}