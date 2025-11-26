package com.gameengine.input;

import com.gameengine.math.Vector2;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InputManager {
    private static InputManager instance;
    private Set<Integer> pressedKeys;
    private Set<Integer> justPressedKeys;
    private Set<Integer> lastFramePressedKeys; // 新增：保存上一帧的按键状态
    private Map<Integer, Boolean> keyStates;
    private Vector2 mousePosition;
    private boolean[] mouseButtons;
    private boolean[] mouseButtonsJustPressed;
    private boolean[] lastFrameMouseButtons; // 新增：保存上一帧的鼠标状态
    
    private InputManager() {
        pressedKeys = new HashSet<>();
        justPressedKeys = new HashSet<>();
        lastFramePressedKeys = new HashSet<>(); // 初始化
        keyStates = new HashMap<>();
        mousePosition = new Vector2();
        mouseButtons = new boolean[3];
        mouseButtonsJustPressed = new boolean[3];
        lastFrameMouseButtons = new boolean[3]; // 初始化
    }
    
    public static InputManager getInstance() {
        if (instance == null) {
            instance = new InputManager();
        }
        return instance;
    }
    
    public void update() {
        // 清空justPressed状态，准备计算新的justPressed
        justPressedKeys.clear();
        for (int i = 0; i < mouseButtonsJustPressed.length; i++) {
            mouseButtonsJustPressed[i] = false;
        }
        
        // 计算哪些按键是"刚刚按下"的
        for (Integer keyCode : pressedKeys) {
            if (!lastFramePressedKeys.contains(keyCode)) {
                justPressedKeys.add(keyCode);
            }
        }
        
        // 计算哪些鼠标按钮是"刚刚按下"的
        for (int i = 0; i < mouseButtons.length; i++) {
            if (mouseButtons[i] && !lastFrameMouseButtons[i]) {
                mouseButtonsJustPressed[i] = true;
            }
        }
        
        // 保存当前帧状态，供下一帧比较使用
        lastFramePressedKeys.clear();
        lastFramePressedKeys.addAll(pressedKeys);
        
        System.arraycopy(mouseButtons, 0, lastFrameMouseButtons, 0, mouseButtons.length);
    }
    
    public void onKeyPressed(int keyCode) {
        pressedKeys.add(keyCode);
        keyStates.put(keyCode, true);
    }
    
    public void onKeyReleased(int keyCode) {
        pressedKeys.remove(keyCode);
        keyStates.put(keyCode, false);
    }
    
    public void onMouseMoved(float x, float y) {
        mousePosition.x = x;
        mousePosition.y = y;
    }
    
    public void onMousePressed(int button) {
        if (button >= 0 && button < mouseButtons.length) {
            mouseButtons[button] = true;
        }
    }
    
    public void onMouseReleased(int button) {
        if (button >= 0 && button < mouseButtons.length) {
            mouseButtons[button] = false;
        }
    }
    
    public boolean isKeyPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }
    
    public boolean isKeyJustPressed(int keyCode) {
        return justPressedKeys.contains(keyCode);
    }
    
    public boolean isMouseButtonPressed(int button) {
        if (button >= 0 && button < mouseButtons.length) {
            return mouseButtons[button];
        }
        return false;
    }
    
    public boolean isMouseButtonJustPressed(int button) {
        if (button >= 0 && button < mouseButtons.length) {
            return mouseButtonsJustPressed[button];
        }
        return false;
    }
    
    public boolean isAnyKeyJustPressed() {
        return !justPressedKeys.isEmpty();
    }
    
    public boolean isAnyKeyPressed() {
        return !pressedKeys.isEmpty();
    }

    public java.util.Set<Integer> getJustPressedKeysSnapshot() {
        return new java.util.HashSet<>(justPressedKeys);
    }
    
    public Vector2 getMousePosition() {
        return new Vector2(mousePosition);
    }
    
    public float getMouseX() {
        return mousePosition.x;
    }
    
    public float getMouseY() {
        return mousePosition.y;
    }
}