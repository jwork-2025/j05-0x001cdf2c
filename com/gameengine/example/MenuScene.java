package com.gameengine.example;

import com.gameengine.core.GameEngine;
import com.gameengine.graphics.Renderer;
import com.gameengine.input.InputManager;
import com.gameengine.math.Vector2;
import com.gameengine.scene.Scene;
import com.gameengine.recording.RecordingConfig;
import com.gameengine.recording.RecordingService;

import java.io.File;

public class MenuScene extends Scene {
    public enum MenuOption {
        START_GAME,
        REPLAY,
        EXIT
    }

    private Renderer renderer;
    private InputManager inputManager;
    private GameEngine engine;
    private int selectedIndex;
    private MenuOption[] options;
    private boolean selectionMade;
    private MenuOption selectedOption;
    // private List<String> replayFiles;
    private boolean showReplayInfo;
    private int debugFrames;

    public MenuScene(GameEngine engine, String name) {
        super(name);
        this.engine = engine;
        this.renderer = engine.getRenderer();
        this.inputManager = InputManager.getInstance();
        this.selectedIndex = 0;
        this.options = new MenuOption[] { MenuOption.START_GAME, MenuOption.REPLAY, MenuOption.EXIT };
        this.selectionMade = false;
        this.selectedOption = null;
        // this.replayFiles = new ArrayList<>();
        this.showReplayInfo = false;
    }

    private void loadReplayFiles() {
    }

    @Override
    public void initialize() {
        super.initialize();
        loadReplayFiles();
        selectedIndex = 0;
        selectionMade = false;
        debugFrames = 0;
    }

    // 在这里进入处理不同分支的逻辑
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        handleMenuSelection();

        if (selectionMade && selectedOption == MenuOption.START_GAME) {
            Scene gameScene = new GameScene(engine);
            engine.setScene(gameScene);
            try {
                // 当选择了开始游戏，那么就开始录制
                new File("recordings").mkdirs();
                String path = "recordings/session_" + System.currentTimeMillis() + ".jsonl";
                RecordingConfig cfg = new RecordingConfig(path);
                RecordingService svc = new RecordingService(cfg);
                engine.enableRecording(svc);
            } catch (Exception e) {

            }
        }
    }

    // 处理选项
    private void handleMenuSelection() {
        // 指定鼠标点击范围
        Vector2 mousePos = inputManager.getMousePosition();
        if (inputManager.isMouseButtonPressed(1)) {
            float centerX = renderer.getWidth() / 2.0f;
            float buttonY1 = centerX - 300;
            float buttonY2 = centerX + 200;

            if (mousePos.x >= buttonY1 && mousePos.x <= buttonY1 + 200) {
                System.out.printf("start\n");
                selectedIndex = 0;
                selectionMade = true;
                selectedOption = MenuOption.START_GAME;
            } else if (mousePos.x >= buttonY2 - 100 && mousePos.x <= buttonY2 + 100) {
                // 转换到回放
                System.out.printf("Replay\n");
                selectedIndex = 1;
                selectedOption = MenuOption.REPLAY;
                engine.disableRecording();
                Scene replay = new ReplayScene(engine, null);
                engine.setScene(replay);
            }
        }
    }

    @Override
    public void render() {
        if (renderer == null)
            return;

        int width = renderer.getWidth();
        int height = renderer.getHeight();
        if (debugFrames < 5) {

            debugFrames++;
        }

        renderer.drawRect(0, 0, width, height, 0.25f, 0.25f, 0.35f, 1.0f);

        super.render();

        renderMainMenu();
    }

    private void renderMainMenu() {
        if (renderer == null)
            return;

        int width = renderer.getWidth();
        int height = renderer.getHeight();

        // 只做两个键，开始游玩或者replay
        // 左边一个开始游玩的播放三角形 右边一个回放的原型箭头
        renderer.drawTriangle(width / 2 - 300, height / 2, 200, 0.6f, 0.5f, 0.2f, 1.0f);
        renderer.drawCircle(width / 2 + 200, height / 2, 100, 1, 0.6f, 0.5f, 0.2f, 1.0f);
    }

}
