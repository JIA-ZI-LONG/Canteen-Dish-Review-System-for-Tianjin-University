-- 测试激励管理功能的示例数据

-- 插入一些测试用户的积分记录
INSERT INTO tb_credit_record (id, user_id, action_type, credits, description, create_time) VALUES
(1001, 1, 'USER_SIGN_IN', 5, '每日签到奖励', DATEADD(HOUR, -2, GETDATE())),
(1002, 1, 'POST_BLOG', 10, '发布博客', DATEADD(HOUR, -1, GETDATE())),
(1003, 2, 'USER_SIGN_IN', 5, '每日签到奖励', DATEADD(HOUR, -3, GETDATE())),
(1004, 2, 'ADD_COMMENT', 2, '发表评论', DATEADD(MINUTE, -30, GETDATE())),
(1005, 3, 'LIKE_BLOG', 1, '点赞博客', DATEADD(MINUTE, -15, GETDATE())),
(1006, 1, 'ADD_COMMENT', 2, '发表评论', DATEADD(MINUTE, -10, GETDATE())),
(1007, 2, 'LIKE_BLOG', 1, '点赞博客', DATEADD(MINUTE, -5, GETDATE())),
(1008, 3, 'POST_BLOG', 10, '发布博客', GETDATE());

-- 更新用户积分（模拟累计效果）
UPDATE tb_user SET credits = 50 WHERE id = 1;
UPDATE tb_user SET credits = 30 WHERE id = 2;
UPDATE tb_user SET credits = 20 WHERE id = 3;

-- 查询验证数据
SELECT 
    ir.action_type,
    ir.credits,
    ir.daily_limit,
    ir.description,
    ir.status,
    COUNT(cr.id) as usage_count,
    SUM(cr.credits) as total_credits_given
FROM tb_incentive_rule ir
LEFT JOIN tb_credit_record cr ON ir.action_type = cr.action_type
GROUP BY ir.action_type, ir.credits, ir.daily_limit, ir.description, ir.status
ORDER BY usage_count DESC;
