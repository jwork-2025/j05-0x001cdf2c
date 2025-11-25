@echo off
REM 简单编译脚本
echo 编译游戏引擎...

REM 创建输出目录
mkdir build\classes 2>nul

REM 编译所有Java文件
javac -d build\classes ^
    -cp . ^
    com/gameengine/math/Vector2.java ^
    com/gameengine/input/InputManager.java ^
    com/gameengine/core/Component.java ^
    com/gameengine/core/GameObject.java ^
    com/gameengine/components/TransformComponent.java ^
    com/gameengine/components/PhysicsComponent.java ^
    com/gameengine/components/RenderComponent.java ^
    com/gameengine/graphics/Renderer.java ^
    com/gameengine/core/GameEngine.java ^
    com/gameengine/core/GameLogic.java ^
    com/gameengine/scene/Scene.java ^
    com/gameengine/example/GameExample.java

if %errorlevel% equ 0 (
    echo 编译成功！
    echo 运行游戏: java -cp build\classes com.gameengine.example.GameExample
) else (
    echo 编译失败！
    exit /b 1
)