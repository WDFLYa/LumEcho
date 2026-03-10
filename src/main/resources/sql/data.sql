TRUNCATE TABLE posts;
TRUNCATE TABLE resource_file;
TRUNCATE TABLE user;
TRUNCATE TABLE categories;

INSERT INTO categories (name, status) VALUES
                                          ('日常', 1),
                                          ('技术', 1),
                                          ('美食', 1),
                                          ('旅行', 1),
                                          ('摸鱼', 1);  -- 全部启用

INSERT INTO `user` (account, phone, password, username, email, bio, status, role, create_time, update_time, deleted_at) VALUES
                                                                                                                            ('xiaomao', '13800138001', '$2a$10$dummy', '小猫同学', 'xiaomao@example.com', '喜欢晒太阳和写代码 ☀️', 1, 'USER', NOW() - INTERVAL 30 DAY, NOW(), NULL),
                                                                                                                            ('doudou', '13800138002', '$2a$10$dummy', '豆豆', 'doudou@example.com', '手冲咖啡爱好者 ☕', 1, 'USER', NOW() - INTERVAL 25 DAY, NOW(), NULL),
                                                                                                                            ('starlight', '13800138003', '$2a$10$dummy', '星野', 'starlight@example.com', '追星星的人 ✨', 1, 'USER', NOW() - INTERVAL 20 DAY, NOW(), NULL),
                                                                                                                            ('bunbun', '13800138004', '$2a$10$dummy', '布丁兔', 'bunbun@example.com', '甜品治愈一切 🍮', 1, 'USER', NOW() - INTERVAL 15 DAY, NOW(), NULL),
                                                                                                                            ('codepanda', '13800138005', '$2a$10$dummy', '码力熊猫', 'panda@example.com', '一边吃竹子一边 debug 🐼', 1, 'USER', NOW() - INTERVAL 10 DAY, NOW(), NULL);

INSERT INTO resource_file (biz_type, biz_id, file_url, file_name, created_by, created_time) VALUES
                                                                                                ('AVATAR', 1, 'https://example.com/avatars/xiaomao.jpg', 'avatar.png', 1, NOW() - INTERVAL 30 DAY),
                                                                                                ('AVATAR', 2, 'https://example.com/avatars/doudou.jpg', 'avatar.png', 2, NOW() - INTERVAL 25 DAY),
                                                                                                ('AVATAR', 3, 'https://example.com/avatars/starlight.jpg', 'avatar.png', 3, NOW() - INTERVAL 20 DAY),
                                                                                                ('AVATAR', 4, 'https://example.com/avatars/bunbun.jpg', 'avatar.png', 4, NOW() - INTERVAL 15 DAY),
                                                                                                ('AVATAR', 5, 'https://example.com/avatars/codepanda.jpg', 'avatar.png', 5, NOW() - INTERVAL 10 DAY);


INSERT INTO posts (
    post_type, category_id, user_id, title, content,
    view_count, like_count, comment_count,
    pinned, post_status,
    create_time, update_time, deleted_at
) VALUES
      (1, 1, 1, '阳台番茄长出第一片叶子啦！', '每天浇水都好期待～🌱', 89, 15, 3, 0, 1, NOW() - INTERVAL 1 HOUR, NOW(), NULL),
      (1, 1, 1, '今天捡到一只小蜗牛', '给它取名“慢慢”，明天送回花园 🐌', 76, 12, 4, 0, 1, NOW() - INTERVAL 5 HOUR, NOW(), NULL),
      (1, 1, 1, '自制柠檬蜂蜜水', '夏天就要清爽一夏！🍋', 102, 21, 6, 0, 1, NOW() - INTERVAL 10 HOUR, NOW(), NULL),
      (1, 1, 1, '周末去公园发呆', '云朵像棉花糖，风很温柔 ☁️', 65, 9, 2, 0, 1, NOW() - INTERVAL 15 HOUR, NOW(), NULL),

      (1, 3, 2, '手冲瑰夏成功了！', '花香爆炸，喝一口就开心 💦', 134, 28, 8, 0, 1, NOW() - INTERVAL 2 HOUR, NOW(), NULL),
      (1, 3, 2, '自制提拉米苏', '咖啡+可可粉=幸福公式 🍰', 112, 24, 7, 0, 1, NOW() - INTERVAL 8 HOUR, NOW(), NULL),
      (1, 3, 2, '发现一家宝藏面包店', '羊角包酥到掉渣！🥐', 98, 19, 5, 0, 1, NOW() - INTERVAL 18 HOUR, NOW(), NULL),
      (1, 3, 2, '夏日水果茶配方', '西瓜+薄荷=解暑神器 🍉', 87, 16, 4, 0, 1, NOW() - INTERVAL 1 DAY, NOW(), NULL),

      (1, 2, 3, '用 WebGL 画了一颗会动的星球', '附源码，欢迎 fork 🌍', 245, 52, 14, 1, 1, NOW() - INTERVAL 3 HOUR, NOW(), NULL),
      (1, 2, 3, 'React 性能优化小技巧', 'useMemo 真的是万能的吗？⚛️', 198, 41, 11, 0, 1, NOW() - INTERVAL 9 HOUR, NOW(), NULL),
      (1, 2, 3, '搭建了自己的博客', '静态生成 + GitHub Pages，超快！🚀', 176, 35, 9, 0, 1, NOW() - INTERVAL 20 HOUR, NOW(), NULL),
      (1, 2, 3, '学 TypeScript 第一周', 'interface 和 type 到底怎么选？🤔', 143, 29, 8, 0, 1, NOW() - INTERVAL 2 DAY, NOW(), NULL),

      (1, 3, 4, '抹茶千层蛋糕教程', '零失败！空气感层层叠叠 🍰', 167, 36, 10, 0, 1, NOW() - INTERVAL 4 HOUR, NOW(), NULL),
      (1, 1, 4, '雨天最适合做甜品', '听着雨声打奶油，超治愈 🌧️', 121, 25, 6, 0, 1, NOW() - INTERVAL 12 HOUR, NOW(), NULL),
      (1, 3, 4, '低糖红豆麻薯', '软糯Q弹，健康无负担 🫘', 109, 22, 5, 0, 1, NOW() - INTERVAL 22 HOUR, NOW(), NULL),
      (1, 1, 4, '整理了我的书桌', '干净桌面=高效心情 📚', 92, 18, 4, 0, 1, NOW() - INTERVAL 3 DAY, NOW(), NULL),

      (1, 2, 5, 'Rust 写了个小工具', '再也不用手动重命名文件了 🦀', 210, 44, 13, 0, 1, NOW() - INTERVAL 6 HOUR, NOW(), NULL),
      (1, 5, 5, '上班摸鱼画了只熊猫', '用 ASCII art 写的，看懂了吗？🐼', 88, 17, 9, 0, 1, NOW() - INTERVAL 16 HOUR, NOW(), NULL),
      (1, 2, 5, 'Docker 镜像瘦身指南', '从 2GB 到 200MB 的秘诀 🐳', 187, 39, 12, 0, 1, NOW() - INTERVAL 1 DAY + INTERVAL 4 HOUR, NOW(), NULL),
      (1, 5, 5, '午休做了个梦', '梦见自己变成了一只会写代码的熊猫 😴', 75, 14, 7, 0, 1, NOW() - INTERVAL 3 DAY + INTERVAL 6 HOUR, NOW(), NULL);

INSERT INTO resource_file (biz_type, biz_id, file_url, file_name, created_by, created_time) VALUES
                                                                                                ('POST_COVER', 1, 'https://example.com/covers/tomato_leaf.jpg', 'cover.jpg', 1, NOW() - INTERVAL 1 HOUR),
                                                                                                ('POST_COVER', 2, 'https://example.com/covers/snail.jpg', 'cover.jpg', 1, NOW() - INTERVAL 5 HOUR),
                                                                                                ('POST_COVER', 3, 'https://example.com/covers/lemon_water.jpg', 'cover.jpg', 1, NOW() - INTERVAL 10 HOUR),
                                                                                                ('POST_COVER', 4, 'https://example.com/covers/cloud_park.jpg', 'cover.jpg', 1, NOW() - INTERVAL 15 HOUR),
                                                                                                ('POST_COVER', 5, 'https://example.com/covers/geisha_coffee.jpg', 'cover.jpg', 2, NOW() - INTERVAL 2 HOUR),
                                                                                                ('POST_COVER', 6, 'https://example.com/covers/tiramisu.jpg', 'cover.jpg', 2, NOW() - INTERVAL 8 HOUR),
                                                                                                ('POST_COVER', 7, 'https://example.com/covers/croissant.jpg', 'cover.jpg', 2, NOW() - INTERVAL 18 HOUR),
                                                                                                ('POST_COVER', 8, 'https://example.com/covers/fruit_tea.jpg', 'cover.jpg', 2, NOW() - INTERVAL 1 DAY),
                                                                                                ('POST_COVER', 9, 'https://example.com/covers/webgl_planet.jpg', 'cover.jpg', 3, NOW() - INTERVAL 3 HOUR),
                                                                                                ('POST_COVER', 10, 'https://example.com/covers/react_optimize.jpg', 'cover.jpg', 3, NOW() - INTERVAL 9 HOUR),
                                                                                                ('POST_COVER', 11, 'https://example.com/covers/blog_github.jpg', 'cover.jpg', 3, NOW() - INTERVAL 20 HOUR),
                                                                                                ('POST_COVER', 12, 'https://example.com/covers/typescript_week1.jpg', 'cover.jpg', 3, NOW() - INTERVAL 2 DAY),
                                                                                                ('POST_COVER', 13, 'https://example.com/covers/matcha_mille.jpg', 'cover.jpg', 4, NOW() - INTERVAL 4 HOUR),
                                                                                                ('POST_COVER', 14, 'https://example.com/covers/rainy_day.jpg', 'cover.jpg', 4, NOW() - INTERVAL 12 HOUR),
                                                                                                ('POST_COVER', 15, 'https://example.com/covers/red_bean_mochi.jpg', 'cover.jpg', 4, NOW() - INTERVAL 22 HOUR),
                                                                                                ('POST_COVER', 16, 'https://example.com/covers/clean_desk.jpg', 'cover.jpg', 4, NOW() - INTERVAL 3 DAY),
                                                                                                ('POST_COVER', 17, 'https://example.com/covers/rust_tool.jpg', 'cover.jpg', 5, NOW() - INTERVAL 6 HOUR),
                                                                                                ('POST_COVER', 18, 'https://example.com/covers/panda_ascii.jpg', 'cover.jpg', 5, NOW() - INTERVAL 16 HOUR),
                                                                                                ('POST_COVER', 19, 'https://example.com/covers/docker_slim.jpg', 'cover.jpg', 5, NOW() - INTERVAL 1 DAY + INTERVAL 4 HOUR),
                                                                                                ('POST_COVER', 20, 'https://example.com/covers/panda_dream.jpg', 'cover.jpg', 5, NOW() - INTERVAL 3 DAY + INTERVAL 6 HOUR);