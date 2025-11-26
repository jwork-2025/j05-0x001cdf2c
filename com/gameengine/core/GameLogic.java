//游戏逻辑

package com.gameengine.core;

import com.gameengine.components.TransformComponent;
import com.gameengine.components.EnemyComponent;
import com.gameengine.components.PhysicsComponent;
import com.gameengine.core.GameObject;
import com.gameengine.input.InputManager;
import com.gameengine.math.Vector2;
import com.gameengine.scene.Scene;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.List;

/**
 * 游戏逻辑类，处理具体的游戏规则
 */
public class GameLogic {
    private Scene scene;
    private InputManager inputManager;
    private ExecutorService pool;

    public GameLogic(Scene scene) {
        this.scene = scene;
        this.inputManager = InputManager.getInstance();
        this.pool = Executors.newFixedThreadPool(10);
        // 固定创建10个线程来处理
    }

    /**
     * 处理玩家输入
     */
    public void handlePlayerInput() {
        List<GameObject> players = scene.findGameObjectsByComponent(TransformComponent.class);// 查找游戏对象
        if (players.isEmpty())
            return;

        GameObject player = players.get(0);
        TransformComponent transform = player.getComponent(TransformComponent.class);
        PhysicsComponent physics = player.getComponent(PhysicsComponent.class);

        if (transform == null || physics == null)
            return;

        Vector2 movement = new Vector2();

        if (inputManager.isKeyPressed(87) || inputManager.isKeyPressed(38)) { // W或上箭头
            movement.y -= 1;
        }
        if (inputManager.isKeyPressed(83) || inputManager.isKeyPressed(40)) { // S或下箭头
            movement.y += 1;
        }
        if (inputManager.isKeyPressed(65) || inputManager.isKeyPressed(37)) { // A或左箭头
            movement.x -= 1;
        }
        if (inputManager.isKeyPressed(68) || inputManager.isKeyPressed(39)) { // D或右箭头
            movement.x += 1;
        }
        if (inputManager.isKeyPressed(32)) { // 空格
            player.createBullet(transform.getPosition());
        }
        if (movement.magnitude() > 0) {
            movement = movement.normalize().multiply(200);
            physics.setVelocity(movement);
        }

        // 边界检查
        Vector2 pos = transform.getPosition();
        if (pos.x < 0)
            pos.x = 0;
        if (pos.y < 0)
            pos.y = 0;
        if (pos.x > 1600 - 20)
            pos.x = 1600 - 20;
        if (pos.y > 900 - 20)
            pos.y = 900 - 20;
        transform.setPosition(pos);
    }

    /**
     * 更新物理系统
     */

    private void single_process(PhysicsComponent physics) {
        TransformComponent transform = physics.getOwner().getComponent(TransformComponent.class);
        if (transform != null) {
            Vector2 pos = transform.getPosition();
            Vector2 velocity = physics.getVelocity();

            if (pos.x <= 0 || pos.x >= 1600 - 15) {
                velocity.x = -velocity.x;
                physics.setVelocity(velocity);
            }
            if (pos.y <= 0 || pos.y >= 900 - 15) {
                velocity.y = -velocity.y;
                physics.setVelocity(velocity);
            }

            // 确保在边界内
            if (pos.x < 0)
                pos.x = 0;
            if (pos.y < 0)
                pos.y = 0;
            if (pos.x > 1600 - 15)
                pos.x = 1600 - 15;
            if (pos.y > 900 - 15)
                pos.y = 900 - 15;
            transform.setPosition(pos);
        }
    }

    public void updatePhysics() {
        List<PhysicsComponent> physicsComponents = scene.getComponents(PhysicsComponent.class);
        // 这里我们直接计数 要是数量每超过10个 就当做一批任务提交上去 否则串行执行
        if (physicsComponents.size() < 10)
            for (PhysicsComponent physics : physicsComponents)
                single_process(physics);
        else {
            // 并行处理部分
            for (int i = 0; i < physicsComponents.size(); i += 10) {
                // 10个为一组提交
                int head = i;
                pool.submit(() -> {
                    for (int j = head; j < head + 10; j++)
                        single_process(physicsComponents.get(j));
                });
            }
        }
    }

    /**
     * 检查碰撞
     */
    private void single_check(GameObject enemy) {
        TransformComponent enemyTransform = enemy.getComponent(TransformComponent.class);
        if (enemyTransform == null)
            return;

        if (900 - enemyTransform.getPosition().y < 20)// 敌人突破防线
        {
            List<GameObject> players = scene.findGameObjectsByComponent(TransformComponent.class);// 查找游戏对象
            if (players.isEmpty())
                return;

            GameObject player = players.get(0);
            player.gameOver = 1;
        }
        // 直接查找所有游戏对象，然后过滤出子弹
        for (GameObject obj : scene.getGameObjects()) // 对于每一个子弹
        {
            if (obj.getName().equals("Bullet")) {
                TransformComponent BulletTransform = obj.getComponent(TransformComponent.class);
                PhysicsComponent BulletPhysics = obj.getComponent(PhysicsComponent.class);
                if (BulletTransform != null) {
                    float distance = enemyTransform.getPosition().distance(BulletTransform.getPosition());
                    if (distance < 30) {
                        // 碰撞！重置玩家位置
                        enemyTransform.setPosition(new Vector2(0, 0));
                        (enemy.getComponent(PhysicsComponent.class)).setFriction(0.0f);
                        // 也让子弹消失
                        BulletTransform.setPosition(new Vector2(1600, 900));
                        BulletPhysics.setFriction(0.0f);
                        break;
                    }
                    if (BulletTransform.getPosition().y - 0 < 30) {
                        BulletTransform.setPosition(new Vector2(1600, 900));
                        BulletPhysics.setFriction(0.0f);
                    }
                }
            }
        }
    }

    public int checkCollisions() {
        // 直接查找玩家对象
        List<GameObject> enemys = scene.findGameObjectsByComponent(EnemyComponent.class);

        if (enemys.isEmpty())
            return 0;

        // 这里直接把每个都变成多线程
        // 并行处理部分
        for (int i = 0; i < enemys.size(); i++) // 对于每一个敌人
        {
            GameObject enemy = enemys.get(i);
            pool.submit(() -> {
                single_check(enemy);
            });
        }
        
        List<GameObject> players = scene.findGameObjectsByComponent(TransformComponent.class);// 查找游戏对象
        if (players.isEmpty())
            return 0;
        GameObject player = players.get(0);
        if(player.gameOver==1)
            return 1;

        return 0;
    }
}
