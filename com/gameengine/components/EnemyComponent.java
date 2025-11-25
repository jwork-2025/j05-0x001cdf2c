package com.gameengine.components;

import com.gameengine.core.Component;

import com.gameengine.math.Vector2;

public class EnemyComponent extends Component<EnemyComponent> {

    public EnemyComponent() {
    }

    @Override
    public void initialize() {
        // 初始化物理组件
    }

    @Override
    public void update(float deltaTime) {
        
    }

    @Override
    public void render() {
        // 物理组件不直接渲染
    }
}
