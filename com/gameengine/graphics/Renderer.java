//渲染 绘制图形

package com.gameengine.graphics;

import com.gameengine.input.InputManager;
import com.gameengine.math.Vector2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * 渲染器
 */
public class Renderer extends JFrame {
    private int width;
    private int height;
    private String title;
    private GamePanel gamePanel;
    private InputManager inputManager;

    public Renderer(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.inputManager = InputManager.getInstance();

        initialize();
    }

    private void initialize() {
        setTitle(title);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        gamePanel = new GamePanel();
        add(gamePanel);

        setupInput();

        setVisible(true);
    }

    private void setupInput() {
        // 键盘输入
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                inputManager.onKeyPressed(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                inputManager.onKeyReleased(e.getKeyCode());
            }
        });

        // 鼠标输入
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                inputManager.onMousePressed(e.getButton());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                inputManager.onMouseReleased(e.getButton());
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                inputManager.onMouseMoved(e.getX(), e.getY());
            }
        });

        setFocusable(true);
        requestFocus();
    }

    /**
     * 开始渲染帧
     */
    public void beginFrame() {
        gamePanel.clear();
    }

    /**
     * 结束渲染帧
     */
    public void endFrame() {
        gamePanel.repaint();
    }

    /**
     * 绘制矩形
     */
    public void drawRect(float x, float y, float width, float height, float r, float g, float b, float a) {
        gamePanel.addDrawable(new RectDrawable(x, y, width, height, r, g, b, a));
    }

    /**
     * 绘制圆形
     */
    public void drawCircle(float x, float y, float radius, int segments, float r, float g, float b, float a) {
        gamePanel.addDrawable(new CircleDrawable(x, y, radius, r, g, b, a));
    }

    /**
     * 绘制线条
     */
    public void drawLine(float x1, float y1, float x2, float y2, float r, float g, float b, float a) {
        gamePanel.addDrawable(new LineDrawable(x1, y1, x2, y2, r, g, b, a));
    }

    public void drawTriangle(float x, float y, float size, float r, float g, float b, float a)
    {
        gamePanel.addDrawable(new TriangleDrawable(x, y, size, r, g, b, a));
    }

    public void drawText(float x, float y, String text, float r, float g, float b, float a)
    {
        
    }
    /**
     * 检查窗口是否应该关闭
     */
    public boolean shouldClose() {
        return !isVisible();
    }

    /**
     * 处理事件
     */
    public void pollEvents() {
        // Swing自动处理事件
    }

    /**
     * 清理资源
     */
    public void cleanup() {
        dispose();
    }

    // Getters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    /**
     * 游戏面板类
     */
    private class GamePanel extends JPanel {
        private List<Drawable> drawables = new ArrayList<>();

        public GamePanel() {
            setPreferredSize(new Dimension(width, height));
            setBackground(Color.BLACK);
        }

        public void clear() {
            drawables.clear();
        }

        public void addDrawable(Drawable drawable) {
            drawables.add(drawable);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            for (Drawable drawable : drawables) {
                drawable.draw(g2d);
            }
        }
    }

    /**
     * 可绘制对象接口
     */
    private interface Drawable {
        void draw(Graphics2D g);
    }

    /**
     * 矩形绘制类
     */
    private static class RectDrawable implements Drawable {
        private float x, y, width, height;
        private Color color;

        public RectDrawable(float x, float y, float width, float height, float r, float g, float b, float a) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = new Color(r, g, b, a);
        }

        @Override
        public void draw(Graphics2D g) {
            g.setColor(color);
            g.fillRect((int) x, (int) y, (int) width, (int) height);
        }
    }

    /**
     * 圆形绘制类
     */
    private static class CircleDrawable implements Drawable {
        private float x, y, radius;
        private Color color;

        public CircleDrawable(float x, float y, float radius, float r, float g, float b, float a) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.color = new Color(r, g, b, a);
        }

        @Override
        public void draw(Graphics2D g) {
            g.setColor(color);
            g.fillOval((int) (x - radius), (int) (y - radius), (int) (radius * 2), (int) (radius * 2));
        }
    }

    /**
     * 线条绘制类
     */
    private static class LineDrawable implements Drawable {
        private float x1, y1, x2, y2;
        private Color color;

        public LineDrawable(float x1, float y1, float x2, float y2, float r, float g, float b, float a) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = new Color(r, g, b, a);
        }

        @Override
        public void draw(Graphics2D g) {
            g.setColor(color);
            g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        }
    }

    /**
    * 三角形绘制类（播放按钮样式）
    */
    private static class TriangleDrawable implements Drawable 
    {
        private float x, y, size;
        private Color color;
        private boolean pointingRight; // 控制三角形朝向：true向右，false向左

        /**
         * 构造函数 - 向右的播放按钮三角形
         */
        public TriangleDrawable(float x, float y, float size, float r, float g, float b, float a) {
            this(x, y, size, r, g, b, a, true);
        }

        /**
         * 构造函数 - 可指定朝向的三角形
         */
        public TriangleDrawable(float x, float y, float size, float r, float g, float b, float a, boolean pointingRight) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.color = new Color(r, g, b, a);
            this.pointingRight = pointingRight;
        }

        @Override
        public void draw(Graphics2D g) {
            g.setColor(color);
            
            // 计算三角形的三个顶点
            int[] xPoints, yPoints;
            
            if (pointingRight) {
                // 向右的三角形（播放按钮）
                xPoints = new int[] {
                    (int) x,                    // 左顶点
                    (int) x,                    // 左顶点（与上一点相同，构成等腰三角形）
                    (int) (x + size)            // 右顶点（尖端）
                };
                yPoints = new int[] {
                    (int) (y - size / 2),       // 上顶点
                    (int) (y + size / 2),       // 下顶点
                    (int) y                     // 中间顶点（尖端）
                };
            } else {
                // 向左的三角形
                xPoints = new int[] {
                    (int) (x + size),           // 右顶点
                    (int) (x + size),           // 右顶点（与上一点相同）
                    (int) x                     // 左顶点（尖端）
                };
                yPoints = new int[] {
                    (int) (y - size / 2),       // 上顶点
                    (int) (y + size / 2),       // 下顶点
                    (int) y                     // 中间顶点（尖端）
                };
            }
            
            // 绘制填充三角形
            g.fillPolygon(xPoints, yPoints, 3);
        }
    }
    
    public void drawLetterR(float x, float y, float size, float r, float g, float b, float a) 
    {
        float width = size * 0.6f;
        
        // 旋转180°后的左侧竖线（原右侧竖线）
        drawLine(x + width, y, x + width, y + size, r, g, b, a);
        
        // 旋转180°后的顶部横线（原底部横线）
        drawLine(x + width, y, x + width * 0.2f, y, r, g, b, a);
        
        // 旋转180°后的右侧上半部（原左侧下半部）
        drawLine(x + width * 0.2f, y, x, y + size * 0.2f, r, g, b, a);
        drawLine(x, y + size * 0.2f, x, y + size * 0.5f, r, g, b, a);
        
        // 旋转180°后的中间横线（原中间横线位置不变）
        drawLine(x + width, y + size * 0.5f, x, y + size * 0.5f, r, g, b, a);
        
        // 旋转180°后的右下斜线（原左上斜线）
        drawLine(x, y + size * 0.5f, x - width * 0.3f, y + size, r, g, b, a);
    }
    public void drawLetterRDetailedMirrored(float x, float y, float size, float r, float g, float b, float a) 
    {
        float width = size * 0.6f;

        // 镜像后的左侧竖线（原右侧竖线）
        drawLine(x + width, y, x + width, y + size, r, g, b, a);

        // 镜像后的顶部横线
        drawLine(x + width, y + size, x + width * 0.2f, y + size, r, g, b, a);

        // 镜像后的右侧上半部
        drawLine(x + width * 0.2f, y + size, x, y + size * 0.8f, r, g, b, a);
        drawLine(x, y + size * 0.8f, x, y + size * 0.5f, r, g, b, a);

        // 镜像后的中间横线
        drawLine(x + width, y + size * 0.5f, x, y + size * 0.5f, r, g, b, a);

        // 镜像后的右下斜线（原左下斜线）
        drawLine(x, y + size * 0.5f, x - width * 0.3f, y, r, g, b, a);
    }
}
