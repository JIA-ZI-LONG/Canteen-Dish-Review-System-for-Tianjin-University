-- =================================================================
-- TjuFood 数据库 v3.2 (增强完整版)
-- 核心修正与增强：
-- 1. 统一并优化了所有表的结构，与最终代码匹配。
-- 2. 为所有表、所有字段添加了中文注释。
-- 3. 整合了监控、审计、API管理等高级功能所需的数据表。
-- 4. 优化了索引和约束，确保数据完整性和查询性能。
-- =================================================================

-- 如果数据库已存在，先删除（仅限开发环境，生产环境请勿执行！）
/*
USE master;
GO
IF DB_ID('tju_food7') IS NOT NULL
BEGIN
    ALTER DATABASE tju_food7 SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE tju_food7;
END
GO
*/

-- 创建数据库
CREATE DATABASE tju_food7;
GO
USE tju_food7;
GO

-- =================================================================
-- 模块 1: 用户与权限 (User & Permissions)
-- =================================================================

-- 表 1.1: 普通用户表
CREATE TABLE tb_user (
    id BIGINT NOT NULL PRIMARY KEY,                      -- 用户ID (主键, 程序生成)
    password NVARCHAR(100) NOT NULL,                       -- 登录密码 (加密存储)
    email NVARCHAR(100) UNIQUE NOT NULL,                  -- 注册邮箱 (唯一, 用于登录)
    phone VARCHAR(20) NULL,                               -- 手机号
    nickname NVARCHAR(50) NOT NULL,                         -- 用户昵称
    icon NVARCHAR(255) NULL,                              -- 头像图片的URL
    gender TINYINT NOT NULL DEFAULT 2,                      -- 性别 (0=女, 1=男, 2=未知)
    birthday DATE NULL,                                   -- 生日
    bio NVARCHAR(300) NULL,                               -- 个人简介
    campus NVARCHAR(50) NOT NULL,                           -- 所属校区
    credits INT NOT NULL DEFAULT 0,                         -- 用户积分
    level INT NOT NULL DEFAULT 1,                           -- 用户等级
    status TINYINT NOT NULL DEFAULT 0,                      -- 用户状态 (0=正常, 1=禁用)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 账号创建时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE()        -- 信息最后更新时间
);
GO

-- 表 1.2: 角色表
CREATE TABLE tb_role (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,           -- 角色ID (主键, 自增)
    name NVARCHAR(50) NOT NULL UNIQUE,                      -- 角色名称 (唯一)
    description NVARCHAR(255) NULL,                       -- 角色描述
    status TINYINT NOT NULL DEFAULT 0,                      -- 角色状态 (0=正常, 1=禁用)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 创建时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE()        -- 更新时间
);
GO

-- 表 1.3: 管理员表
CREATE TABLE tb_admin (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,           -- 管理员ID (主键, 自增)
    username NVARCHAR(50) NOT NULL UNIQUE,                  -- 管理员登录账号 (唯一)
    password NVARCHAR(100) NOT NULL,                       -- 管理员密码 (加密存储)
    name NVARCHAR(50) NOT NULL,                           -- 管理员真实姓名或职位
    avatar NVARCHAR(2048) NULL,                           -- 管理员头像URL (新增)
    role_id BIGINT NOT NULL,                                -- 所属角色ID (外键)
    status TINYINT NOT NULL DEFAULT 0,                      -- 管理员状态 (0=正常, 1=禁用)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 创建时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 更新时间
    CONSTRAINT FK_admin_role FOREIGN KEY (role_id) REFERENCES tb_role(id) -- 外键约束
);
GO
CREATE INDEX idx_admin_role_id ON tb_admin(role_id);
GO


-- =================================================================
-- 模块 2: 核心基础数据 (Core Data)
-- =================================================================

-- 表 2.1: 校区表
CREATE TABLE tb_campus (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,           -- 校区ID (主键, 自增)
    name NVARCHAR(50) NOT NULL UNIQUE,                      -- 校区名称 (唯一)
    address NVARCHAR(255) NULL                            -- 校区地址
);
GO

-- 表 2.2: 食堂类型表
CREATE TABLE tb_canteen_type (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,           -- 食堂类型ID (主键, 自增)
    name NVARCHAR(50) NOT NULL UNIQUE,                      -- 类型名称 (唯一)
    icon NVARCHAR(255) NULL,                              -- 该类型的图标URL
    sort INT NOT NULL DEFAULT 0                             -- 排序值 (用于调整显示顺序)
);
GO

-- 表 2.3: 食堂表
CREATE TABLE tb_canteen (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,           -- 食堂ID (主键, 自增)
    name NVARCHAR(100) NOT NULL,                           -- 食堂名称
    campus_id BIGINT NOT NULL,                              -- 所属校区ID (外键)
    type_id BIGINT NOT NULL,                                -- 食堂类型ID (外键)
    introduction NVARCHAR(500) NULL,                      -- 食堂介绍
    address NVARCHAR(255) NOT NULL,                       -- 食堂详细地址
    floor NVARCHAR(20) NULL,                              -- 所在楼层信息
    open_hours NVARCHAR(255) NULL,                        -- 营业时间描述
    avg_price INT NULL,                                   -- 人均价格 (聚合计算得出)
    score FLOAT NOT NULL DEFAULT 0,                         -- 综合评分 (聚合计算得出)
    taste_score FLOAT NOT NULL DEFAULT 0,                   -- 口味评分 (聚合计算得出)
    environment_score FLOAT NOT NULL DEFAULT 0,             -- 环境评分 (聚合计算得出)
    service_score FLOAT NOT NULL DEFAULT 0,                 -- 服务评分 (聚合计算得出)
    comments INT NOT NULL DEFAULT 0,                        -- 评价总数 (聚合计算得出)
    open_status TINYINT NOT NULL DEFAULT 1,                 -- 营业状态 (0=休息, 1=营业)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 创建时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 更新时间
    CONSTRAINT FK_canteen_campus FOREIGN KEY (campus_id) REFERENCES tb_campus(id),
    CONSTRAINT FK_canteen_type FOREIGN KEY (type_id) REFERENCES tb_canteen_type(id)
);
GO
CREATE INDEX idx_canteen_campus_id ON tb_canteen(campus_id);
CREATE INDEX idx_canteen_type_id ON tb_canteen(type_id);
GO

-- 表 2.4: 窗口类型表
CREATE TABLE tb_stall_type (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,           -- 窗口类型ID (主键, 自增)
    name NVARCHAR(50) NOT NULL UNIQUE,                      -- 类型名称 (唯一)
    icon NVARCHAR(255) NULL,                              -- 该类型的图标URL
    sort INT NOT NULL DEFAULT 0                             -- 排序值 (用于调整显示顺序)
);
GO

-- 表 2.5: 窗口表
CREATE TABLE tb_stall (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,           -- 窗口ID (主键, 自增)
    name NVARCHAR(100) NOT NULL,                           -- 窗口名称
    canteen_id BIGINT NOT NULL,                             -- 所属食堂ID (外键)
    type_id BIGINT NOT NULL,                                -- 窗口类型ID (外键)
    location NVARCHAR(50) NULL,                           -- 窗口在食堂内的具体位置
    introduction NVARCHAR(500) NULL,                      -- 窗口介绍
    open_hours NVARCHAR(255) NULL,                        -- 营业时间描述
    avg_price INT NULL,                                   -- 人均价格 (聚合计算得出)
    score FLOAT NOT NULL DEFAULT 0,                         -- 综合评分 (聚合计算得出)
    taste_score FLOAT NOT NULL DEFAULT 0,                   -- 口味评分 (聚合计算得出)
    environment_score FLOAT NOT NULL DEFAULT 0,             -- 环境评分 (聚合计算得出)
    service_score FLOAT NOT NULL DEFAULT 0,                 -- 服务评分 (聚合计算得出)
    price_score FLOAT NOT NULL DEFAULT 0,                   -- 性价比评分 (聚合计算得出)
    comments INT NOT NULL DEFAULT 0,                        -- 评价总数
    total_score DECIMAL(10, 2) NOT NULL DEFAULT 0,        -- 总评分（用于增量计算）
    total_taste_score DECIMAL(10, 2) NOT NULL DEFAULT 0,  -- 口味总分（用于增量计算）
    total_environment_score DECIMAL(10, 2) NOT NULL DEFAULT 0, -- 环境总分（用于增量计算）
    total_service_score DECIMAL(10, 2) NOT NULL DEFAULT 0, -- 服务总分（用于增量计算）
    total_price_score DECIMAL(10, 2) NOT NULL DEFAULT 0,  -- 性价比总分（用于增量计算）
    open_status TINYINT NOT NULL DEFAULT 1,                 -- 营业状态 (0=休息, 1=营业)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 创建时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 更新时间
    CONSTRAINT FK_stall_canteen FOREIGN KEY (canteen_id) REFERENCES tb_canteen(id),
    CONSTRAINT FK_stall_type FOREIGN KEY (type_id) REFERENCES tb_stall_type(id)
);
GO
CREATE INDEX idx_stall_canteen_id ON tb_stall(canteen_id);
CREATE INDEX idx_stall_type_id ON tb_stall(type_id);
GO

-- 表 2.6: 菜品表
CREATE TABLE tb_dish (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,           -- 菜品ID (主键, 自增)
    name NVARCHAR(100) NOT NULL,                           -- 菜品名称
    stall_id BIGINT NOT NULL,                               -- 所属窗口ID (外键)
    category NVARCHAR(50) NULL,                           -- 菜品分类 (如: '主食')
    price DECIMAL(10, 2) NOT NULL,                        -- 价格
    description NVARCHAR(500) NULL,                       -- 菜品描述
    liked INT NOT NULL DEFAULT 0,                           -- 被点赞数
    status TINYINT NOT NULL DEFAULT 1,                      -- 售卖状态 (0=下架, 1=上架)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 创建时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 更新时间
    CONSTRAINT FK_dish_stall FOREIGN KEY (stall_id) REFERENCES tb_stall(id)
);
GO
CREATE INDEX idx_dish_stall_id ON tb_dish(stall_id);
GO


-- =================================================================
-- 模块 3: 内容与互动 (Content & Interaction)
-- =================================================================

-- 表 3.1: 博客/笔记表
CREATE TABLE tb_blog (
    id BIGINT NOT NULL PRIMARY KEY,                       -- 博客ID (主键, 程序生成)
    user_id BIGINT NOT NULL,                                -- 作者的用户ID (外键)
    title NVARCHAR(200) NOT NULL,                         -- 博客标题
    image_url NVARCHAR(255) NULL,                         -- 封面图片URL
    content NVARCHAR(MAX) NOT NULL,                      -- 博客正文 (使用MAX以支持更长内容)
    liked INT NOT NULL DEFAULT 0,                           -- 被点赞的数量
    comments INT NOT NULL DEFAULT 0,                        -- 被评论的数量
    status TINYINT NOT NULL DEFAULT 0,                      -- 状态 (0=待审核, 1=通过, 2=拒绝, 3=用户隐藏, 4=管理员删除)
    is_top TINYINT NOT NULL DEFAULT 0,                      -- 是否置顶 (1=是, 0=否)
    is_quality TINYINT NOT NULL DEFAULT 0,                  -- 是否加精 (1=是, 0=否)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 创建时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 更新时间
    CONSTRAINT FK_blog_user FOREIGN KEY (user_id) REFERENCES tb_user(id)
);
GO
CREATE INDEX idx_blog_user_id ON tb_blog(user_id);
GO


-- 表 3.2: 举报表
CREATE TABLE tb_report (
    id BIGINT NOT NULL PRIMARY KEY,                       -- 举报ID (主键, 程序生成)
    user_id BIGINT NOT NULL,                                -- 举报者ID (外键)
    target_id BIGINT NOT NULL,                              -- 被举报对象ID
    type TINYINT NOT NULL,                                  -- 被举报对象类型 (1=博客, 2=评价, 3=评论, 4=用户)
    reason NVARCHAR(255) NOT NULL,                         -- 举报原因
    description NVARCHAR(500) NULL,                       -- 详细描述
    status TINYINT NOT NULL DEFAULT 0,                      -- 处理状态 (0=未处理, 1=已处理, 2=已驳回)
    handle_admin_id BIGINT NULL,                            -- 处理的管理员ID (外键)
    result NVARCHAR(255) NULL,                           -- 处理结果备注 (修正字段名)
    handle_time DATETIME2 NULL,                           -- 处理时间 (修正字段名)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 举报创建时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE()        -- 记录更新时间
);
GO
ALTER TABLE tb_report ADD CONSTRAINT FK_report_user FOREIGN KEY (user_id) REFERENCES tb_user(id);
GO
ALTER TABLE tb_report ADD CONSTRAINT FK_report_admin FOREIGN KEY (handle_admin_id) REFERENCES tb_admin(id);
GO
CREATE INDEX idx_report_target_id_type ON tb_report(target_id, type);
GO



-- =================================================================
-- 模块 4: 用户激励 (User Engagement)
-- =================================================================

-- 表 4.1: 积分记录表
CREATE TABLE tb_credit_record (
    id BIGINT NOT NULL PRIMARY KEY,                       -- 记录ID (主键, 程序生成)
    user_id BIGINT NOT NULL,                                -- 用户ID (外键)
    action_type NVARCHAR(50) NOT NULL,                      -- 积分获取类型
    credits INT NOT NULL,                                   -- 变动积分 (正或负)
    description NVARCHAR(255) NULL,                       -- 变动描述
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 创建时间
    CONSTRAINT FK_credit_user FOREIGN KEY (user_id) REFERENCES tb_user(id)
);
GO
CREATE INDEX idx_credit_record_user_id ON tb_credit_record(user_id);
GO

-- 表 4.2: 优惠券模板表
CREATE TABLE tb_voucher (
    id BIGINT NOT NULL PRIMARY KEY,                       -- 优惠券模板ID
    title NVARCHAR(100) NOT NULL,                         -- 标题
    description NVARCHAR(255) NULL,                       -- 描述
    type TINYINT NOT NULL DEFAULT 0,                        -- 类型 (0=满减券, 1=折扣券)
    price DECIMAL(10, 2) NULL,                            -- 面额
    discount DECIMAL(5, 2) NULL,                          -- 折扣率
    min_amount DECIMAL(10, 2) NOT NULL DEFAULT 0,           -- 最低消费
    canteen_id BIGINT NULL,                                 -- 限定食堂ID
    start_time DATETIME2 NOT NULL,                          -- 生效时间
    end_time DATETIME2 NOT NULL,                            -- 过期时间
    stock INT NOT NULL DEFAULT 0,                           -- 库存
    required_credits INT NOT NULL DEFAULT 0,                -- 兑换所需积分
    status TINYINT NOT NULL DEFAULT 0,                      -- 状态 (0=未开始, 1=进行中, 2=已结束)
    version INT NOT NULL DEFAULT 0,                         -- 乐观锁版本号
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 创建时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE()        -- 更新时间
);
GO

-- 表 4.3: 用户优惠券表
CREATE TABLE tb_user_voucher (
    id BIGINT NOT NULL PRIMARY KEY,                       -- 用户券ID (主键, 程序生成)
    user_id BIGINT NOT NULL,                                -- 用户ID (外键)
    voucher_id BIGINT NOT NULL,                             -- 优惠券模板ID (外键)
    status TINYINT NOT NULL DEFAULT 0,                      -- 状态 (0=未使用, 1=已使用, 2=已过期)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 领取时间
    use_time DATETIME2 NULL,                              -- 使用时间
    CONSTRAINT FK_user_voucher_user FOREIGN KEY (user_id) REFERENCES tb_user(id),
    CONSTRAINT FK_user_voucher_voucher FOREIGN KEY (voucher_id) REFERENCES tb_voucher(id)
);
GO
CREATE INDEX idx_user_voucher_user_id ON tb_user_voucher(user_id);
CREATE INDEX idx_user_voucher_voucher_id ON tb_user_voucher(voucher_id);
GO

-- 表 4.4: 用户激励规则表
CREATE TABLE tb_incentive_rule (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,           -- 规则ID (主键, 自增)
    action_type NVARCHAR(50) NOT NULL UNIQUE,               -- 行为类型 (程序用, 如 'POST_BLOG')
    credits INT NOT NULL,                                   -- 奖励积分
    daily_limit INT NOT NULL DEFAULT 1,                     -- 每日奖励上限次数
    description NVARCHAR(255) NULL,                       -- 规则描述
    status TINYINT NOT NULL DEFAULT 1,                      -- 规则状态 (0=禁用, 1=启用)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 创建时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE()        -- 更新时间
);
GO


-- =================================================================
-- 模块 5: 系统管理与监控 (System Management & Monitoring)
-- =================================================================

-- 表 5.1: 轮播图表
CREATE TABLE tb_banner (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,           -- 轮播图ID (主键, 自增)
    title NVARCHAR(100) NOT NULL,                         -- 标题
    image_url NVARCHAR(255) NOT NULL,                     -- 图片URL
    link_url NVARCHAR(255) NULL,                          -- 跳转链接
    sort INT NOT NULL DEFAULT 0,                            -- 排序值
    status TINYINT NOT NULL DEFAULT 1,                      -- 状态 (0=禁用, 1=启用)
    start_time DATETIME2 NOT NULL,                          -- 展示开始时间
    end_time DATETIME2 NOT NULL,                            -- 展示结束时间
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 创建时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE()        -- 更新时间
);
GO

-- 表 5.2: 系统公告表
CREATE TABLE tb_notice (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,           -- 公告ID (主键, 自增)
    admin_id BIGINT NOT NULL,                               -- 发布管理员ID (外键)
    title NVARCHAR(100) NOT NULL,                         -- 公告标题
    content NVARCHAR(MAX) NOT NULL,                       -- 公告内容
    type TINYINT NOT NULL DEFAULT 0,                        -- 类型 (0=普通, 1=重要)
    status TINYINT NOT NULL DEFAULT 0,                      -- 状态 (0=草稿, 1=已发布, 2=已撤回)
    publish_time DATETIME2 NOT NULL,                        -- 发布时间
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 创建时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 更新时间
    CONSTRAINT FK_notice_admin FOREIGN KEY (admin_id) REFERENCES tb_admin(id)
);
GO
CREATE INDEX idx_notice_admin_id ON tb_notice(admin_id);
GO



-- 表 5.3: 敏感词表
CREATE TABLE tb_sensitive_word (
    id BIGINT NOT NULL PRIMARY KEY,                       -- 敏感词ID (主键, 程序生成)
    word NVARCHAR(100) NOT NULL UNIQUE,                    -- 敏感词内容 (唯一)
    category NVARCHAR(50) NOT NULL DEFAULT '通用',         -- 分类
    level TINYINT NOT NULL DEFAULT 1,                       -- 敏感等级 (1-5)
    status TINYINT NOT NULL DEFAULT 1,                      -- 状态 (0=禁用, 1=启用)
    admin_id BIGINT NOT NULL,                               -- 添加的管理员ID (外键)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 创建时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 更新时间
    CONSTRAINT FK_sensitive_word_admin FOREIGN KEY (admin_id) REFERENCES tb_admin(id)
);
GO

-- 表 5.4: API接口访问日志表
CREATE TABLE tb_api_access_log (
    id BIGINT NOT NULL PRIMARY KEY,                       -- 日志ID (主键)
    api_path NVARCHAR(500) NOT NULL,                      -- 请求的API路径
    http_method NVARCHAR(10) NOT NULL,                    -- HTTP请求方法
    api_name NVARCHAR(200) NULL,                          -- API业务名称
    api_version NVARCHAR(20) NULL,                        -- API版本号
    caller_id BIGINT NULL,                                  -- 调用者ID
    caller_type INT NULL,                                   -- 调用者类型 (1=管理员, 2=用户, 3=匿名)
    caller_name NVARCHAR(100) NULL,                       -- 调用者名称
    request_params NTEXT NULL,                              -- 请求参数
    request_headers NTEXT NULL,                             -- 请求头
    request_size BIGINT NULL,                               -- 请求大小 (字节)
    response_status INT NOT NULL,                           -- 响应状态码
    response_data NTEXT NULL,                               -- 响应数据
    response_size BIGINT NULL,                              -- 响应大小 (字节)
    response_time BIGINT NOT NULL,                          -- 响应耗时 (毫秒)
    success INT NULL,                                       -- 是否成功 (1=是, 0=否)
    error_code NVARCHAR(50) NULL,                         -- 错误码
    error_message NVARCHAR(500) NULL,                     -- 错误信息
    client_ip NVARCHAR(50) NULL,                          -- 客户端IP地址
    ip_location NVARCHAR(200) NULL,                       -- IP归属地
    user_agent NVARCHAR(500) NULL,                        -- 浏览器或客户端信息
    referer NVARCHAR(500) NULL,                           -- 请求来源页面
    trace_id NVARCHAR(100) NULL,                          -- 链路追踪ID
    create_time DATETIME2 NULL DEFAULT GETDATE()            -- 创建时间
);
GO
CREATE INDEX idx_api_access_path ON tb_api_access_log(api_path);
CREATE INDEX idx_api_access_time ON tb_api_access_log(create_time);
GO

-- 表 5.5: 审计日志表
CREATE TABLE tb_audit_log (
    id BIGINT NOT NULL PRIMARY KEY,                       -- 日志ID (主键)
    log_type INT NOT NULL,                                  -- 日志类型 (1=操作, 2=登录, 3=安全)
    operator_id BIGINT NULL,                                -- 操作员ID
    operator_type INT NULL,                                 -- 操作员类型 (1=管理员, 2=用户)
    operator_name NVARCHAR(100) NULL,                       -- 操作员名称
    module NVARCHAR(50) NULL,                             -- 功能模块
    operation NVARCHAR(100) NULL,                         -- 操作类型
    description NVARCHAR(500) NULL,                       -- 操作描述
    method NVARCHAR(10) NULL,                             -- 请求方法
    url NVARCHAR(500) NULL,                               -- 请求URL
    params NTEXT NULL,                                      -- 请求参数
    result NTEXT NULL,                                      -- 返回结果
    status INT NULL,                                        -- 操作状态 (1=成功, 0=失败)
    error_msg NVARCHAR(500) NULL,                         -- 错误信息
    execute_time BIGINT NULL,                               -- 执行耗时 (毫秒)
    ip NVARCHAR(50) NULL,                                 -- 操作IP
    ip_location NVARCHAR(200) NULL,                       -- IP归属地
    user_agent NVARCHAR(500) NULL,                        -- User Agent
    browser NVARCHAR(50) NULL,                            -- 浏览器类型
    os NVARCHAR(50) NULL,                                 -- 操作系统
    device_type NVARCHAR(50) NULL,                        -- 设备类型
    risk_level INT NULL,                                    -- 风险等级 (1=低, 2=中, 3=高)
    create_time DATETIME2 NULL DEFAULT GETDATE()            -- 创建时间
);
GO
CREATE INDEX idx_audit_log_module ON tb_audit_log(module);
CREATE INDEX idx_audit_log_operator ON tb_audit_log(operator_id);
GO

-- 表 5.6: 定时任务管理表
CREATE TABLE tb_scheduled_task (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,           -- 任务ID (主键, 自增)
    task_name NVARCHAR(100) NOT NULL UNIQUE,                -- 任务名称 (唯一)
    task_group NVARCHAR(50) NOT NULL,                       -- 任务分组
    cron_expression NVARCHAR(100) NOT NULL,                 -- CRON表达式
    status TINYINT NOT NULL DEFAULT 0,                      -- 任务状态 (0=停止, 1=运行中, 2=暂停)
    description NVARCHAR(255) NULL,                       -- 任务描述
    last_run_time DATETIME2 NULL,                           -- 上次执行时间
    next_run_time DATETIME2 NULL,                           -- 下次计划执行时间
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 创建时间
    update_time DATETIME2 NOT NULL DEFAULT GETDATE()        -- 更新时间
);
GO


-- =================================================================
-- 模块 6: 统计与分析 (Statistics & Analysis)
-- =================================================================

-- 表 6.1: 排行榜表
CREATE TABLE tb_ranking (
    id BIGINT NOT NULL PRIMARY KEY,                       -- 排行ID (主键, 程序生成)
    type TINYINT NOT NULL,                                  -- 排行类型 (0=菜品热度, 1=食堂评分, etc.)
    target_id BIGINT NOT NULL,                              -- 排行榜中的目标ID
    score FLOAT NOT NULL,                                   -- 用于排行的分值
    rank INT NOT NULL,                                      -- 排名
    update_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 更新时间
    CONSTRAINT UQ_ranking UNIQUE (type, target_id)
);
GO
CREATE INDEX idx_ranking_type_score ON tb_ranking(type, score DESC);
GO

-- 表 6.2: 统计数据表
CREATE TABLE tb_statistics (
    id BIGINT NOT NULL PRIMARY KEY,                       -- 统计ID (主键, 程序生成)
    type TINYINT NOT NULL,                                  -- 统计类型 (0=用户日活, 1=评分分布)
    target_id BIGINT NULL,                                  -- 统计目标ID (可选)
    period VARCHAR(20) NOT NULL,                          -- 统计周期 ('day', 'week')
    date DATE NOT NULL,                                     -- 统计日期
    data NVARCHAR(MAX) NOT NULL,                          -- 统计数据 (JSON字符串)
    create_time DATETIME2 NOT NULL DEFAULT GETDATE(),       -- 生成时间
    CONSTRAINT UQ_statistics UNIQUE (type, target_id, period, date)
);
GO