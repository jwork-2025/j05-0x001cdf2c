package com.gameengine.example;

import java.util.Random;

import com.gameengine.components.RenderComponent;
import com.gameengine.components.TransformComponent;
import com.gameengine.core.GameObject;
import com.gameengine.graphics.Renderer;
import com.gameengine.math.Vector2;

public final class EntityFactory {
    private EntityFactory() {
    }

    public static GameObject createPlayerVisual(Renderer renderer) {
        return new GameObject("Player") {
            private Vector2 basePosition;

            @Override
            public void update(float dt) {
                super.update(dt);
                TransformComponent tc = getComponent(TransformComponent.class);
                if (tc != null)
                    basePosition = tc.getPosition();
            }

            @Override
            public void render() {
                if (basePosition == null)
                    return;
                // 渲染腿部（裤子）
                renderer.drawRect(
                        basePosition.x - 8, basePosition.y - 5, 6, 20, // 左腿
                        0.0f, 0.0f, 0.8f, 1.0f // 深蓝色
                );
                renderer.drawRect(
                        basePosition.x + 2, basePosition.y - 5, 6, 20, // 右腿
                        0.0f, 0.0f, 0.8f, 1.0f // 深蓝色
                );

                // 渲染身体（衣服）
                renderer.drawRect(
                        basePosition.x - 10, basePosition.y - 35, 20,
                        30, // 身体主体
                        1.0f, 0.0f, 0.0f, 1.0f // 红色
                );

                // 渲染腰带
                renderer.drawRect(
                        basePosition.x - 10, basePosition.y - 25, 20, 5, // 腰带
                        1.0f, 1.0f, 0.0f, 1.0f // 黄色
                );

                // 渲染手臂
                renderer.drawRect(
                        basePosition.x - 16, basePosition.y - 30, 6, 25, // 左臂
                        1.0f, 0.0f, 0.0f, 1.0f // 红色
                );
                renderer.drawRect(
                        basePosition.x + 10, basePosition.y - 30, 6, 25, // 右臂
                        1.0f, 0.0f, 0.0f, 1.0f // 红色
                );

                // 渲染手部
                renderer.drawRect(
                        basePosition.x - 18, basePosition.y - 5, 8, 5, // 左手
                        1.0f, 0.8f, 0.6f, 1.0f // 肤色
                );
                renderer.drawRect(
                        basePosition.x + 10, basePosition.y - 5, 8, 5, // 右手
                        1.0f, 0.8f, 0.6f, 1.0f // 肤色
                );

                // 渲染颈部
                renderer.drawRect(
                        basePosition.x - 4, basePosition.y - 45, 8, 5, // 颈部
                        1.0f, 0.8f, 0.6f, 1.0f // 肤色
                );

                // 渲染头部
                renderer.drawRect(
                        basePosition.x - 8, basePosition.y - 65, 16, 20, // 头部主体
                        1.0f, 0.8f, 0.6f, 1.0f // 肤色
                );

                // 渲染头发（前额）
                renderer.drawRect(
                        basePosition.x - 8, basePosition.y - 65, 16, 5, // 前额头发
                        0.2f, 0.1f, 0.0f, 1.0f // 深棕色
                );

                // 渲染葫芦头饰
                renderer.drawRect(
                        basePosition.x - 6, basePosition.y - 75, 12, 10, // 葫芦底部
                        0.0f, 0.6f, 0.0f, 1.0f // 绿色
                );
                renderer.drawRect(
                        basePosition.x - 4, basePosition.y - 85, 8, 10, // 葫芦顶部
                        0.0f, 0.8f, 0.0f, 1.0f // 亮绿色
                );

                // 渲染面部特征
                // 眼睛
                renderer.drawRect(
                        basePosition.x - 4, basePosition.y - 60, 3, 3, // 左眼
                        0.0f, 0.0f, 0.0f, 1.0f // 黑色
                );
                renderer.drawRect(
                        basePosition.x + 1, basePosition.y - 60, 3, 3, // 右眼
                        0.0f, 0.0f, 0.0f, 1.0f // 黑色
                );

                // 嘴巴
                renderer.drawRect(
                        basePosition.x - 3, basePosition.y - 50, 6, 2, // 嘴巴
                        0.8f, 0.2f, 0.2f, 1.0f // 红色
                );
            }
        };
    }

    public static GameObject createEnemy(Renderer renderer, float w, float h, float r, float g, float b, float a) {
        return new GameObject("Enemy") {
            private Vector2 basePosition;

            @Override
            public void update(float dt) {
                super.update(dt);
                TransformComponent tc = getComponent(TransformComponent.class);
                if (tc != null)
                    basePosition = tc.getPosition();
            }

            @Override
            public void render() {
                // 渲染小型敌机（红色，灵活型）
                renderer.drawRect(
                        basePosition.x - 12, basePosition.y, 24, 20, // 机身
                        1.0f, 0.0f, 0.0f, 1.0f // 红色
                );

                // 机翼
                renderer.drawRect(
                        basePosition.x - 20, basePosition.y + 5, 8, 10, // 左机翼
                        0.8f, 0.0f, 0.0f, 1.0f // 暗红色
                );
                renderer.drawRect(
                        basePosition.x + 12, basePosition.y + 5, 8, 10, // 右机翼
                        0.8f, 0.0f, 0.0f, 1.0f // 暗红色
                );

                // 机头
                renderer.drawRect(
                        basePosition.x - 6, basePosition.y + 20, 12, 8, // 机头
                        0.9f, 0.9f, 0.9f, 1.0f // 浅灰色
                );
            }

        };
    }

    public static GameObject createEnemy_1(Renderer renderer, float w, float h, float r, float g, float b, float a) {
        return new GameObject("Enemy_1") {
            private Vector2 basePosition;

            @Override
            public void update(float dt) {
                super.update(dt);
                TransformComponent tc = getComponent(TransformComponent.class);
                if (tc != null)
                    basePosition = tc.getPosition();
            }

            @Override
            public void render() {
                // 渲染大型敌机（绿色，重型）- 调整重心向下
                renderer.drawRect(
                        basePosition.x - 20, basePosition.y - 60, 40,
                        30, // 主体 - 下移20像素
                        0.0f, 0.8f, 0.0f, 1.0f // 绿色
                );

                // 大型机翼
                renderer.drawRect(
                        basePosition.x - 35, basePosition.y - 50, 15,
                        20, // 左大机翼 - 下移20像素
                        0.0f, 0.6f, 0.0f, 1.0f // 暗绿色
                );
                renderer.drawRect(
                        basePosition.x + 20, basePosition.y - 50, 15,
                        20, // 右大机翼 - 下移20像素
                        0.0f, 0.6f, 0.0f, 1.0f // 暗绿色
                );

                // 多个发动机
                for (int i = 0; i < 3; i++) {
                    renderer.drawRect(
                            basePosition.x - 15 + i * 10,
                            basePosition.y - 55, 6, 10, // 发动机 -
                                                        // 下移20像素
                            0.1f, 0.1f, 0.1f, 1.0f // 黑色
                    );
                }

                // 驾驶舱
                renderer.drawRect(
                        basePosition.x - 8, basePosition.y - 45, 16, 8, // 驾驶舱
                                                                        // -
                                                                        // 下移20像素
                        0.2f, 0.8f, 1.0f, 1.0f // 天蓝色
                );
            }

        };
    }

    public static GameObject createBullet(Renderer renderer, float w, float h, float r, float g, float b, float a) {
        return new GameObject("Bullet") {
            private Vector2 basePosition;
            private Random random;
            private float time;
            @Override
            public void update(float dt) {
                super.update(dt);
                TransformComponent tc = getComponent(TransformComponent.class);
                if (tc != null)
                    basePosition = tc.getPosition();
            }

            @Override
            public void render() {
                 this.random = new Random();
                if (basePosition == null)
                    return;

                // 导弹头部（尖形，朝上）
                renderer.drawRect(
                        basePosition.x - 2,
                        basePosition.y - 12, 4, 8, // 导弹尖端
                        1.0f, 0.9f, 0.9f, 1.0f // 亮白色
                );

                // 导弹主体
                renderer.drawRect(
                        basePosition.x - 3,
                        basePosition.y - 8, 6, 10, // 导弹身体
                        0.8f, 0.8f, 0.8f, 1.0f // 灰色
                );

                // 导弹尾部翼片
                renderer.drawRect(
                        basePosition.x - 5,
                        basePosition.y + 2, 2, 4, // 左翼
                        0.6f, 0.6f, 0.6f, 1.0f // 深灰色
                );
                renderer.drawRect(
                        basePosition.x + 3,
                        basePosition.y + 2, 2, 4, // 右翼
                        0.6f, 0.6f, 0.6f, 1.0f // 深灰色
                );

                // 拖尾特效 - 多层渐变
                // 内层火焰（最亮）
                renderer.drawRect(
                        basePosition.x - 2,
                        basePosition.y + 4, 4, 8,
                        1.0f, 1.0f, 0.0f, 0.8f // 亮黄色
                );

                // 中层火焰
                renderer.drawRect(
                        basePosition.x - 3,
                        basePosition.y + 8, 6, 6,
                        1.0f, 0.5f, 0.0f, 0.6f // 橙色
                );

                // 外层火焰
                renderer.drawRect(
                        basePosition.x - 4,
                        basePosition.y + 12, 8, 4,
                        1.0f, 0.0f, 0.0f, 0.4f // 红色
                );

                // 烟雾效果
                renderer.drawRect(
                        basePosition.x - 5,
                        basePosition.y + 16, 10, 3,
                        0.3f, 0.3f, 0.3f, 0.3f // 灰色烟雾
                );

                // 动态拖尾效果（随时间波动）
                float tailPulse = (float) Math.sin(time * 8)
                        * 0.3f + 0.7f;

                // 脉冲火焰效果
                renderer.drawRect(
                        basePosition.x - 3,
                        basePosition.y + 4, 6,
                        6 * tailPulse,
                        1.0f, 0.8f, 0.2f,
                        tailPulse * 0.6f // 脉冲黄色
                );

                // 火花粒子效果
                for (int i = 0; i < 2; i++) {
                    float sparkX = basePosition.x - 4
                            + random.nextFloat()
                                    * 8;
                    float sparkY = basePosition.y + 8
                            + random.nextFloat()
                                    * 6;

                    renderer.drawRect(
                            sparkX - 1, sparkY - 1,
                            2, 2,
                            1.0f, 1.0f, 0.5f,
                            tailPulse * 0.7f // 亮黄色火花
                    );
                }
            }

        };
    }
}
