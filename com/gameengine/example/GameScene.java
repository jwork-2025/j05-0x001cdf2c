package com.gameengine.example;

import java.util.Random;

import com.gameengine.components.EnemyComponent;
import com.gameengine.components.PhysicsComponent;
import com.gameengine.components.RenderComponent;
import com.gameengine.components.TransformComponent;
import com.gameengine.core.GameEngine;
import com.gameengine.core.GameLogic;
import com.gameengine.core.GameObject;
import com.gameengine.graphics.Renderer;
import com.gameengine.math.Vector2;
import com.gameengine.scene.Scene;

public class GameScene extends Scene {
        private Renderer renderer;
        private Random random;
        private float time;
        private GameEngine engine;
        private GameLogic gameLogic;

        public static float deltaTime;// 控制按键时间
        public static long lastTime;

        public GameScene(GameEngine engine) {
                super("GameScene"); // 调用父类构造函数
                this.engine = engine;
        }

        @Override
        public void initialize() {
                super.initialize();
                this.renderer = engine.getRenderer();
                this.random = new Random();
                this.time = 0;
                this.gameLogic = new GameLogic(this);

                // 创建游戏对象
                createPlayer();
                createEnemies();
                // createBullet();
                createDecorations();
        }

        @Override
        public void update(float deltaTime) {
                super.update(deltaTime);
                time += deltaTime;

                // 使用游戏逻辑类处理游戏规则
                gameLogic.handlePlayerInput();
                gameLogic.updatePhysics();
                if(gameLogic.checkCollisions()==1)
                {
                        MenuScene menu = new MenuScene(engine, "MainMenu");
                        engine.setScene(menu);
                }

                // 生成新敌人
                if (time > 0.5f) {
                        createEnemy();
                        createEnemy_1();
                        time = 0;
                }
        }

        @Override
        public void render() {
                // 绘制背景
                renderer.drawRect(0, 0, 1600, 900, 0.1f, 0.1f, 0.2f, 1.0f);

                // 渲染所有对象
                super.render();
        }

        private void createPlayer() {

                // 创建葫芦娃 - 所有部位都在一个GameObject中
                GameObject player = new GameObject("Player") {
                        private Vector2 basePosition;
                        @Override
                        public void update(float deltaTime) {
                                super.update(deltaTime);
                                updateComponents(deltaTime);

                                // 更新所有部位的位置
                                updateBodyParts();
                        }

                        @Override
                        public void render() {
                                // 渲染所有部位
                                renderBodyParts();
                        }

                        private void updateBodyParts() {
                                TransformComponent transform = getComponent(
                                                TransformComponent.class);
                                if (transform != null) {
                                        basePosition = transform.getPosition();
                                }
                        }

                        private void renderBodyParts() {

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

                        public void createBullet(Vector2 postion) {
                                long currentTime = System.nanoTime();
                                deltaTime = (currentTime - lastTime) / 1_000_000_000.0f;
                                if (deltaTime < 0.1)
                                        return;
                                GameObject Bullet = new GameObject("Bullet") {
                                        private Vector2 basePosition;

                                        @Override
                                        public void update(float deltaTime) {
                                                super.update(deltaTime);
                                                updateComponents(deltaTime);

                                                // 更新所有部位的位置
                                                updateBodyParts();
                                        }

                                        @Override
                                        public void render() {
                                                // 渲染所有部位
                                                renderBodyParts();
                                        }

                                        private void updateBodyParts() {
                                                TransformComponent transform = getComponent(
                                                                TransformComponent.class);
                                                if (transform != null) {
                                                        basePosition = transform.getPosition();
                                                }
                                        }

                                        private void renderBodyParts() {
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
                                long Time_true = System.nanoTime();
                                lastTime = Time_true;
                                // 这里待定 应该是从人物身上发出

                                // 添加变换组件
                                TransformComponent transform = Bullet
                                                .addComponent(new TransformComponent(postion));

                                // 添加物理组件
                                PhysicsComponent physics = Bullet
                                                .addComponent(new PhysicsComponent(0.5f));

                                physics.setVelocity(new Vector2(
                                                0,
                                                -150));
                                physics.setFriction(1.00f);

                                addGameObject(Bullet);
                        }
                };

                // 添加变换组件
                TransformComponent transform = player
                                .addComponent(new TransformComponent(new Vector2(400, 300)));

                // 添加物理组件
                PhysicsComponent physics = player.addComponent(new PhysicsComponent(1.0f));
                physics.setFriction(0.95f);

                addGameObject(player);

        }

        private void createEnemies() {
                createEnemy();
                createEnemy_1();
        }

        private void createEnemy() {
                GameObject enemy = new GameObject("Enemy") {
                        private Vector2 basePosition;

                        @Override
                        public void update(float deltaTime) {
                                super.update(deltaTime);
                                updateComponents(deltaTime);

                                // 更新所有部位的位置
                                updateBodyParts();
                        }

                        @Override
                        public void render() {
                                // 渲染所有部位
                                renderBodyParts();
                        }

                        private void updateBodyParts() {
                                TransformComponent transform = getComponent(
                                                TransformComponent.class);
                                if (transform != null) {
                                        basePosition = transform.getPosition();
                                }
                        }

                        private void renderBodyParts() {
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

                Vector2 position;

                position = new Vector2(
                                random.nextFloat() * 1600, // 这个函数本身生成0到1 的随机浮点数
                                0);

                // 添加变换组件
                TransformComponent transform = enemy
                                .addComponent(new TransformComponent(position));

                EnemyComponent en = enemy.addComponent(new EnemyComponent());
                // 添加物理组件
                PhysicsComponent physics = enemy.addComponent(new PhysicsComponent(0.5f));

                physics.setVelocity(new Vector2(
                                0,
                                40));
                physics.setFriction(1.00f);

                addGameObject(enemy);
        }

        // 这是大型的敌人
        private void createEnemy_1() {
                GameObject enemy = new GameObject("Enemy_1") {
                        private Vector2 basePosition;

                        @Override
                        public void update(float deltaTime) {
                                super.update(deltaTime);
                                updateComponents(deltaTime);

                                // 更新所有部位的位置
                                updateBodyParts();
                        }

                        @Override
                        public void render() {
                                // 渲染所有部位
                                renderBodyParts();
                        }

                        private void updateBodyParts() {
                                TransformComponent transform = getComponent(
                                                TransformComponent.class);
                                if (transform != null) {
                                        basePosition = transform.getPosition();
                                }
                        }

                        private void renderBodyParts() {
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

                Vector2 position;

                position = new Vector2(
                                random.nextFloat() * 1600, // 这个函数本身生成0到1 的随机浮点数
                                0);

                // 添加变换组件
                TransformComponent transform = enemy
                                .addComponent(new TransformComponent(position));

                EnemyComponent en = enemy.addComponent(new EnemyComponent());
                // 添加物理组件
                PhysicsComponent physics = enemy.addComponent(new PhysicsComponent(0.5f));

                physics.setVelocity(new Vector2(
                                0,
                                (random.nextFloat() - 0.5f) * 30 + 10));
                physics.setFriction(1.00f);

                addGameObject(enemy);
        }

        private void createDecorations() {
                for (int i = 0; i < 5; i++) {
                        createDecoration();
                }
        }

        private void createDecoration() {

                GameObject decoration = new GameObject("Decoration") {
                        @Override
                        public void update(float deltaTime) {
                                super.update(deltaTime);
                                updateComponents(deltaTime);
                        }

                        @Override
                        public void render() {
                                renderComponents();
                        }
                };

                // 随机位置
                Vector2 position = new Vector2(
                                random.nextFloat() * 1600,
                                random.nextFloat() * 900);

                // 添加变换组件
                TransformComponent transform = decoration
                                .addComponent(new TransformComponent(position));

                // 添加渲染组件
                RenderComponent render = decoration.addComponent(new RenderComponent(
                                RenderComponent.RenderType.CIRCLE,
                                new Vector2(5, 5),
                                new RenderComponent.Color(0.5f, 0.5f, 1.0f, 0.8f)));
                render.setRenderer(renderer);

                addGameObject(decoration);
        }

}
