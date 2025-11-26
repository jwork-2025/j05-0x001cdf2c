//创建场景，角色 敌人 

package com.gameengine.example;

import com.gameengine.components.*;
import com.gameengine.core.GameObject;
import com.gameengine.core.GameEngine;
import com.gameengine.core.GameLogic;
import com.gameengine.graphics.Renderer;
import com.gameengine.math.Vector2;
import com.gameengine.scene.Scene;

import java.util.List;
import java.util.Random;

/**
 * 游戏示例
 */
public class GameExample {
        public static float deltaTime;
        public static long lastTime;
        public static void main(String[] args) 
        {
                System.out.println("启动游戏引擎...");

                try {
                        // 创建游戏引擎
                        GameEngine engine = new GameEngine(1600, 900, "游戏引擎");
                        MenuScene menuScene = new MenuScene(engine, "MainMenu");
                        //Scene gameScene = new GameScene(engine); // 匿名子类​
                        engine.setScene(menuScene);
                        //engine.setScene(gameScene);
                        engine.run();

                } catch (Exception e) {
                        System.err.println("游戏运行出错: " + e.getMessage());
                        e.printStackTrace();
                }

                System.out.println("游戏结束");
        }
}
