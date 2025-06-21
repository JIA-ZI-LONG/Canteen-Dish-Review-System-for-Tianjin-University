-- 秒杀资格校验与库存预扣减脚本

-- KEYS[1]: 库存Key (e.g., seckill:stock:voucherId)
-- KEYS[2]: 订单集合Key (e.g., seckill:order:voucherId)
-- ARGV[1]: 用户ID (userId)

-- 1. 判断库存是否充足
if(tonumber(redis.call('get', KEYS[1])) <= 0) then
    -- 返回1代表库存不足
    return 1
end

-- 2. 判断用户是否已下单 (使用Set集合判断)
if(redis.call('sismember', KEYS[2], ARGV[1]) == 1) then
    -- 返回2代表用户重复下单
    return 2
end

-- 3. 扣减库存 (原子操作)
redis.call('incrby', KEYS[1], -1)

-- 4. 将用户ID添加到订单集合中，防止重复下单
redis.call('sadd', KEYS[2], ARGV[1])

-- 5. 返回0代表成功
return 0