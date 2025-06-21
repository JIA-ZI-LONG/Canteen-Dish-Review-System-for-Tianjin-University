// 文件路径: yxq/back/src/main/java/xyz/tjucomments/tjufood/service/impl/DatabaseExportServiceImpl.java

package xyz.tjucomments.tjufood.service.impl;

import io.minio.MinioClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.config.MinioConfig;
import xyz.tjucomments.tjufood.service.IDatabaseExportService;
import xyz.tjucomments.tjufood.service.IMinioUrlService; // <<< 1. 导入新的URL服务接口

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
public class DatabaseExportServiceImpl implements IDatabaseExportService {

    // --- 核心依赖注入 ---
    @Resource
    private MinioClient minioClient;
    @Resource
    private MinioConfig minioConfig;
    @Resource
    private IMinioUrlService minioUrlService; // <<< 2. 注入我们创建的URL管理服务

    // --- 数据库连接信息 ---
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Override
    public String exportDatabase() throws Exception {
        // 1. 解析数据库连接字符串，获取服务器、端口和数据库名
        log.info("开始解析数据库连接信息...");
        String urlWithoutPrefix = dbUrl.substring(dbUrl.indexOf("//") + 2);
        String[] parts = urlWithoutPrefix.split(";");
        String serverAndPort = parts[0];
        String serverName;
        int port = 1433; // SQL Server默认端口
        if (serverAndPort.contains(":")) {
            serverName = serverAndPort.substring(0, serverAndPort.indexOf(":"));
            port = Integer.parseInt(serverAndPort.substring(serverAndPort.indexOf(":") + 1));
        } else {
            serverName = serverAndPort;
        }
        String databaseName = "";
        for (String part : parts) {
            if (part.toLowerCase().startsWith("databasename=")) {
                databaseName = part.substring(part.indexOf("=") + 1);
                break;
            }
        }
        if (databaseName.isEmpty()) {
            throw new RuntimeException("无法从JDBC URL中解析数据库名称");
        }
        log.info("数据库信息解析成功: Server={}, Port={}, DB={}", serverName, port, databaseName);

        // 2. 准备本地临时备份文件路径
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = String.format("backup-%s-%s.bak", databaseName, timeStamp);
        File backupFile = new File(System.getProperty("java.io.tmpdir"), fileName);
        String backupFilePath = backupFile.getAbsolutePath().replace('\\', '/');

        // 3. 构建并执行sqlcmd备份命令
        log.info("开始执行数据库备份，目标文件: {}", backupFilePath);
        String tsqlCommand = String.format("BACKUP DATABASE [%s] TO DISK = N'%s' WITH FORMAT, MEDIANAME = 'SQLServerBackups', NAME = 'Full Backup of %s';", databaseName, backupFilePath, databaseName);
        ProcessBuilder processBuilder = new ProcessBuilder("sqlcmd", "-S", serverName + "," + port, "-U", dbUsername, "-P", dbPassword, "-Q", tsqlCommand);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                log.info("SQLCMD输出: {}", line);
            }
        }
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("数据库备份命令(sqlcmd)执行失败。请检查SQL Server服务账号对临时目录的写入权限。");
        }
        log.info("数据库备份文件在本地生成成功。");

        // 4. 将备份文件上传到MinIO
        String objectName = "db_backups/" + fileName;
        try (FileInputStream fis = new FileInputStream(backupFile)) {
            minioClient.putObject(
                    io.minio.PutObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(objectName)
                            .stream(fis, backupFile.length(), -1)
                            .contentType("application/octet-stream")
                            .build());
            log.info("备份文件已成功上传至MinIO，对象名: {}", objectName);
        } finally {
            // 5. 无论上传成功与否，都删除本地的临时备份文件
            Files.deleteIfExists(backupFile.toPath());
            log.info("已删除本地临时备份文件: {}", backupFile.getAbsolutePath());
        }

        // 6. 【核心修改】调用 IMinioUrlService 生成长期有效的、可被管理的URL
        String finalUrl = minioUrlService.generatePresignedUrl(objectName);
        log.info("生成数据库备份的长期有效下载链接成功: {}", finalUrl);

        return finalUrl;
    }
}