CREATE DATABASE snack_shop_db;
USE snack_shop_db;

CREATE TABLE user (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `username` varchar(50) NOT NULL UNIQUE,
                        `password` varchar(100) NOT NULL,
                        `real_name` varchar(50) NOT NULL,
                        `address` varchar(200) NOT NULL,
                        `phone` varchar(11) NOT NULL,
                        `role` varchar(20) DEFAULT 'USER',
                        `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                        PRIMARY KEY (`id`)
);
CREATE TABLE product(
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `name` varchar(100) NOT NULL,
                           `category` varchar(50),
                           `price` decimal(10,2) NOT NULL,
                           `stock` int NOT NULL,
                           `description` text,
                           `image_url` varchar(200),
                           `status` varchar(20) DEFAULT 'ON', -- ON/OFF
                           `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                           PRIMARY KEY (`id`)
);

CREATE TABLE cart_item (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `user_id` bigint NOT NULL,
                             `product_id` bigint NOT NULL,
                             `quantity` int NOT NULL,
                             `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                             PRIMARY KEY (`id`),
                             FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
                             FOREIGN KEY (`product_id`) REFERENCES `product`(`id`) ON DELETE CASCADE
);


CREATE TABLE order (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `order_number` varchar(32) NOT NULL UNIQUE,
                         `user_id` bigint NOT NULL,
                         `receiver_name` varchar(50) NOT NULL,
                         `receiver_phone` varchar(11) NOT NULL,
                         `receiver_address` varchar(200) NOT NULL,
                         `total_amount` decimal(10,2) NOT NULL,
                         `status` varchar(20) DEFAULT 'PENDING', -- PENDING, PAID, CANCELLED
                         `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                         `pay_time` datetime,
                         PRIMARY KEY (`id`),
                         FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
);

CREATE TABLE order_item (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `order_id` bigint NOT NULL,
                              `product_id` bigint NOT NULL,
                              `product_name` varchar(100) NOT NULL,
                              `price` decimal(10,2) NOT NULL,
                              `quantity` int NOT NULL,
                              PRIMARY KEY (`id`),
                              FOREIGN KEY (`order_id`) REFERENCES `order`(`id`) ON DELETE CASCADE
);

INSERT INTO user (`username`, `password`, `real_name`, `address`, `phone`, `role`) VALUES
                                                                                         ('admin', '$2a$10$NkM3CqLQ5zQ5zQ5zQ5zQ5u', '管理员', '北京市朝阳区', '13800000000', 'ADMIN'),
                                                                                         ('user1', '$2a$10$NkM3CqLQ5zQ5zQ5zQ5zQ5u', '张三', '上海市浦东新区', '13912345678', 'USER');

-- 零食商品
INSERT INTO product (`name`, `category`, `price`, `stock`, `description`, `image_url`) VALUES
                                                                                             ('乐事薯片 原味', '膨化食品', 8.50, 100, '经典原味，香脆可口', 'https://ts1.tc.mm.bing.net/th/id/OIP-C.uUTREUvtfdvpznv10C9yvwHaHa?w=129&h=128&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2'),
                                                                                             ('德芙巧克力 丝滑牛奶', '糖果巧克力', 22.00, 50, '丝滑口感，入口即化', 'https://ts1.tc.mm.bing.net/th/id/OIP-C.-IUfH_bgjLegOmNNQTI9cQHaEk?w=192&h=128&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2'),
                                                                                             ('三只松鼠 每日坚果', '坚果炒货', 68.00, 30, '混合坚果，营养均衡', 'https://ts1.tc.mm.bing.net/th/id/OIP-C.J01wvk9IN_ejWdKFIeNJ3QHaHa?w=193&h=193&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2'),
                                                                                             ('奥利奥 巧克力味夹心', '饼干糕点', 12.50, 80, '扭一扭，舔一舔，泡一泡', 'https://ts1.tc.mm.bing.net/th/id/OIP-C.lyIaZ0inOZSZgxyg6032cwHaOD?w=193&h=344&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2'),
                                                                                             ('康师傅冰红茶 1l', '饮料', 4.50, 200, '冰爽柠檬味', 'https://ts1.tc.mm.bing.net/th/id/OIP-C.z-kskftqi3Or58pjG9yo7QHaHa?w=128&h=128&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2'),
                                                                                             ('臭宝 螺蛳粉', '方便速食', 8.50, 100, '正宗柳州风味', 'https://cn.bing.com/th?id=OPAC.JxO3qc7%2f%2fOQqPQ474C474&o=5&pid=21.1&w=128&h=128&qlt=100&dpr=2&o=2&bw=6&bc=FFFFFF'),
                                                                                             ('三养 火鸡面', '方便速食', 7.50, 100, '火鸡面界鼻祖', 'https://ts1.tc.mm.bing.net/th/id/OIP-C.NGNTyQHDnUmSWbIcGtnZ5QHaHa?w=130&h=128&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2'),
                                                                                             ('王中王 火腿肠', '休闲食品', 18.50, 200, '无淀粉版', 'https://cn.bing.com/th?id=OPAC.a1ZUYlJ4I0UALQ474C474&o=5&pid=21.1&w=140&h=140&qlt=100&dpr=2&o=2&pcl=f5f5f5'),
                                                                                             ('娃哈哈 营养快线', '乳制品', 5.50, 100, '多种口味任你选', 'https://tse4-mm.cn.bing.net/th/id/OIP-C.VA7QeXB2z32d5qZMp2Lk-QHaFw?w=281&h=218&c=7&r=0&o=7&dpr=2&pid=1.7&rm=3'),
                                                                                             ('卫龙 大面筋辣条', '方便速食', 5.00, 150, '甜辣口味，童年回忆', 'https://cn.bing.com/th?id=OPAC.idLNIe0UyYmnhg474C474&o=5&pid=21.1&w=140&h=140&qlt=100&dpr=2&o=2&c=8&pcl=f5f5f5'),
                                                                                             ('可口可乐 500ml', '饮料', 3.00, 100, '经典美味', 'https://tse4-mm.cn.bing.net/th/id/OIP-C.DCvwjKmJnQflgXFFR2AzyAHaHa?w=199&h=199&c=7&r=0&o=7&dpr=2&pid=1.7&rm=3'),
                                                                                             ('麻辣王子', '休闲食品', 7.50, 100, '很麻很辣', 'https://ts1.tc.mm.bing.net/th/id/OIP-C.Hk2hgIGMO4dJlyVlC0wTfQHaHa?w=193&h=193&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2'),
                                                                                             ('手剥笋', '方便速食', 8.50, 100, '正宗川渝风味', 'https://ts1.tc.mm.bing.net/th/id/OIP-C.UXjl4j-_rBG97bdjZy4TRgHaE9?w=193&h=135&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2'),
                                                                                             ('好丽友 巧克力派', '饼干糕点', 18.00, 60, '松软蛋糕+巧克力涂层', 'https://cn.bing.com/th?id=OPAC.%2bHm1Ttgfhtg2LA474C474&o=5&pid=21.1&w=128&h=128&qlt=100&dpr=2&o=2&bw=6&bc=FFFFFF'),
                                                                                             ('老街口 五香味瓜子', '方便速食', 9.50, 100, '400g 大颗粒', 'https://tse3-mm.cn.bing.net/th/id/OIP-C.ZKFHAvBAps1Bq2Oy9HpDvAHaHa?w=174&h=180&c=7&r=0&o=7&dpr=2&pid=1.7&rm=3'),
                                                                                             ('旺仔牛奶 125ml', '乳制品', 4.50, 120, '甜甜的，童年的味道', 'https://ts1.tc.mm.bing.net/th/id/OIP-C.DzO-0MFB2sgrgeH3gHdSwwHaHR?w=193&h=189&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2');




