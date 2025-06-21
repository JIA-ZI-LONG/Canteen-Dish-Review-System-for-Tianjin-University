-- 插入默认激励规则数据
-- 确保表存在
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='tb_incentive_rule' AND xtype='U')
BEGIN
    CREATE TABLE tb_incentive_rule (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        action_type NVARCHAR(50) NOT NULL COMMENT '行为类型',
        credits INT NOT NULL COMMENT '奖励积分',
        daily_limit INT COMMENT '每日限制次数',
        description NVARCHAR(200) COMMENT '规则描述',
        status INT DEFAULT 1 COMMENT '状态 (0=禁用, 1=启用)',
        create_time DATETIME2 DEFAULT GETDATE() COMMENT '创建时间'
    );
END

-- 清空现有数据（如果需要重新初始化）
-- DELETE FROM tb_incentive_rule;

-- 插入默认激励规则
INSERT INTO tb_incentive_rule (action_type, credits, daily_limit, description, status) VALUES
('USER_SIGN_IN', 5, 1, '每日签到奖励', 1),
('POST_BLOG', 10, 3, '发布博客奖励', 1),
('ADD_COMMENT', 2, 10, '发表评论奖励', 1),
('LIKE_BLOG', 1, 20, '点赞博客奖励', 1),
('SHARE_BLOG', 3, 5, '分享博客奖励', 1),
('COMPLETE_PROFILE', 20, 1, '完善个人资料奖励', 1),
('FIRST_LOGIN', 50, 1, '首次登录奖励', 1),
('INVITE_FRIEND', 30, 5, '邀请好友奖励', 1);

-- 创建索引
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'idx_incentive_rule_action_type')
BEGIN
    CREATE INDEX idx_incentive_rule_action_type ON tb_incentive_rule(action_type);
END

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'idx_incentive_rule_status')
BEGIN
    CREATE INDEX idx_incentive_rule_status ON tb_incentive_rule(status);
END
