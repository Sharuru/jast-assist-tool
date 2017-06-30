/*
Navicat PGSQL Data Transfer

Source Server         : PostgreSQL@127.0.0.1
Source Server Version : 90602
Source Host           : localhost:5432
Source Database       : tracer
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 90602
File Encoding         : 65001

Date: 2017-06-30 23:42:48
*/


-- ----------------------------
-- Sequence structure for tracer_roll_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."tracer_roll_id_seq";
CREATE SEQUENCE "public"."tracer_roll_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 4
 CACHE 1;
SELECT setval('"public"."tracer_roll_id_seq"', 4, true);

-- ----------------------------
-- Sequence structure for tracer_setting_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."tracer_setting_id_seq";
CREATE SEQUENCE "public"."tracer_setting_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 3
 CACHE 1;
SELECT setval('"public"."tracer_setting_id_seq"', 3, true);

-- ----------------------------
-- Sequence structure for tracer_user_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."tracer_user_id_seq";
CREATE SEQUENCE "public"."tracer_user_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 4
 CACHE 1;
SELECT setval('"public"."tracer_user_id_seq"', 4, true);

-- ----------------------------
-- Table structure for tracer_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."tracer_role";
CREATE TABLE "public"."tracer_role" (
"id" int4 DEFAULT nextval('tracer_roll_id_seq'::regclass) NOT NULL,
"user_id" int4 NOT NULL,
"role_name" varchar(255) COLLATE "default" NOT NULL,
"created_at" timestamptz(6) NOT NULL,
"updated_at" timestamptz(6) NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of tracer_role
-- ----------------------------
INSERT INTO "public"."tracer_role" VALUES ('4', '4', 'ROLE_ADMIN', '2017-06-30 23:24:50+08', '2017-06-30 23:24:53+08');

-- ----------------------------
-- Table structure for tracer_setting
-- ----------------------------
DROP TABLE IF EXISTS "public"."tracer_setting";
CREATE TABLE "public"."tracer_setting" (
"id" int4 DEFAULT nextval('tracer_setting_id_seq'::regclass) NOT NULL,
"setting_group" varchar(32) COLLATE "default" NOT NULL,
"settings" varchar(255) COLLATE "default",
"created_at" timestamptz(6) NOT NULL,
"updated_at" timestamptz(6) NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of tracer_setting
-- ----------------------------
INSERT INTO "public"."tracer_setting" VALUES ('150', 'GROUP_GIT', '{"repoAddress":"https://github.com/Sharuru/jast-file-tracer.git","repoBranch":"master","repoLocalPath":"D:\\JFTtest"}', '2017-06-30 23:28:28.92+08', '2017-06-30 23:34:22.142+08');

-- ----------------------------
-- Table structure for tracer_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."tracer_user";
CREATE TABLE "public"."tracer_user" (
"id" int4 DEFAULT nextval('tracer_user_id_seq'::regclass) NOT NULL,
"username" varchar(255) COLLATE "default" NOT NULL,
"nickname" varchar(255) COLLATE "default" NOT NULL,
"password" varchar(255) COLLATE "default" NOT NULL,
"email" varchar(255) COLLATE "default",
"enable" bool NOT NULL,
"created_at" timestamptz(6) NOT NULL,
"updated_at" timestamptz(6) NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of tracer_user
-- ----------------------------
INSERT INTO "public"."tracer_user" VALUES ('4', 'admin', 'Admin', 'admin', 'admin@admin.com', 't', '2017-06-30 23:12:52+08', '2017-06-30 23:12:55+08');

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------
ALTER SEQUENCE "public"."tracer_roll_id_seq" OWNED BY "tracer_role"."id";
ALTER SEQUENCE "public"."tracer_setting_id_seq" OWNED BY "tracer_setting"."id";
ALTER SEQUENCE "public"."tracer_user_id_seq" OWNED BY "tracer_user"."id";

-- ----------------------------
-- Primary Key structure for table tracer_role
-- ----------------------------
ALTER TABLE "public"."tracer_role" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tracer_setting
-- ----------------------------
ALTER TABLE "public"."tracer_setting" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tracer_user
-- ----------------------------
ALTER TABLE "public"."tracer_user" ADD PRIMARY KEY ("id");
