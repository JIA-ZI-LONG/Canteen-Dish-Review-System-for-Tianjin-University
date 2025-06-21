-- 创建文件管理表
-- 用于统一管理食堂、校区、窗口、菜品和轮播图的文件

-- 检查表是否存在，如果不存在则创建
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='tb_file' AND xtype='U')
BEGIN
    CREATE TABLE tb_file (
        id BIGINT PRIMARY KEY,
        entity_id BIGINT NOT NULL COMMENT '关联实体ID (食堂ID、校区ID、窗口ID、菜品ID等)',
        type_id INT NOT NULL COMMENT '文件类型 (0=食堂, 1=校区, 2=窗口, 3=菜品, 4=轮播图)',
        url NVARCHAR(1000) NOT NULL COMMENT '文件访问URL',
        object_name NVARCHAR(500) NULL COMMENT 'MinIO对象名称',
        original_name NVARCHAR(255) NULL COMMENT '原始文件名',
        file_size BIGINT NULL COMMENT '文件大小(字节)',
        content_type NVARCHAR(100) NULL COMMENT '文件MIME类型',
        description NVARCHAR(500) NULL COMMENT '文件描述',
        sort_order INT DEFAULT 0 COMMENT '排序顺序',
        status INT DEFAULT 1 COMMENT '状态 (0=禁用, 1=启用)',
        create_time DATETIME2 DEFAULT GETDATE() COMMENT '创建时间',
        update_time DATETIME2 DEFAULT GETDATE() COMMENT '更新时间',
        creator_id BIGINT NULL COMMENT '创建者ID',
        updater_id BIGINT NULL COMMENT '更新者ID'
    );
    
    PRINT '已创建 tb_file 表';
END
ELSE
BEGIN
    PRINT 'tb_file 表已存在';
END

-- 创建索引
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'idx_file_entity_type')
BEGIN
    CREATE INDEX idx_file_entity_type ON tb_file(entity_id, type_id);
    PRINT '已创建 idx_file_entity_type 索引';
END

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'idx_file_type')
BEGIN
    CREATE INDEX idx_file_type ON tb_file(type_id);
    PRINT '已创建 idx_file_type 索引';
END

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'idx_file_object_name')
BEGIN
    CREATE INDEX idx_file_object_name ON tb_file(object_name);
    PRINT '已创建 idx_file_object_name 索引';
END

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'idx_file_status')
BEGIN
    CREATE INDEX idx_file_status ON tb_file(status);
    PRINT '已创建 idx_file_status 索引';
END

-- 创建更新时间触发器
IF NOT EXISTS (SELECT * FROM sys.triggers WHERE name = 'tr_tb_file_update_time')
BEGIN
    EXEC('
    CREATE TRIGGER tr_tb_file_update_time
    ON tb_file
    AFTER UPDATE
    AS
    BEGIN
        UPDATE tb_file 
        SET update_time = GETDATE()
        FROM tb_file f
        INNER JOIN inserted i ON f.id = i.id
    END
    ');
    PRINT '已创建 tr_tb_file_update_time 触发器';
END

-- 插入示例数据（可选）
-- 轮播图示例数据
IF NOT EXISTS (SELECT * FROM tb_file WHERE type_id = 4)
BEGIN
    INSERT INTO tb_file (id, entity_id, type_id, url, description, sort_order, status)
    VALUES 
    (1, 0, 4, '/static/images/banner1.jpg', '首页轮播图1', 1, 1),
    (2, 0, 4, '/static/images/banner2.jpg', '首页轮播图2', 2, 1),
    (3, 0, 4, '/static/images/banner3.jpg', '首页轮播图3', 3, 1);
    
    PRINT '已插入轮播图示例数据';
END

PRINT '文件管理表创建完成！';

-- 显示表结构
SELECT 
    COLUMN_NAME as '列名',
    DATA_TYPE as '数据类型',
    CHARACTER_MAXIMUM_LENGTH as '最大长度',
    IS_NULLABLE as '允许空值',
    COLUMN_DEFAULT as '默认值'
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'tb_file'
ORDER BY ORDINAL_POSITION;
