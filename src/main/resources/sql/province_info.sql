DROP TABLE IF EXISTS `province_info`;
CREATE TABLE `province_info`  (
                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '省份id',
                                  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省份名称',
                                  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
                                  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                                  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of province_info
-- ----------------------------
INSERT INTO `province_info` VALUES (11, '北京市', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (12, '天津市', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (13, '河北省', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (14, '山西省', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (15, '内蒙古自治区', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (21, '辽宁省', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (22, '吉林省', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (23, '黑龙江省', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (31, '上海市', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (32, '江苏省', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (33, '浙江省', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (34, '安徽省', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (35, '福建省', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (36, '江西省', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (37, '山东省', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (41, '河南省', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (42, '湖北省', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (43, '湖南省', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (44, '广东省', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (45, '广西壮族自治区', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (46, '海南省', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (50, '重庆市', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (51, '四川省', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (52, '贵州省', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (53, '云南省', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (54, '西藏自治区', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (61, '陕西省', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (62, '甘肃省', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (63, '青海省', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (64, '宁夏回族自治区', '2023-06-25 13:48:39', NULL, 0);
INSERT INTO `province_info` VALUES (65, '新疆维吾尔自治区', '2023-06-25 13:48:39', NULL, 0);
