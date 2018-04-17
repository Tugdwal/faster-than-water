package ftw.game.graphics.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;

import ftw.game.FTWGame;
import ftw.game.Player;
import ftw.game.assets.Assets;
import ftw.game.graphics.base.GameScreen;
import ftw.game.ship.CrewMember;
import ftw.game.ship.Ship;

public class BattleScreen extends GameScreen
{
    private ArrayList<ActionButton> m_HUDButtons;
    private ArrayList<ShipImage> m_EnemyShips;
    private ArrayList<ShipLifeLabel> m_ShipLifeLabels;

    private BattleManager m_BattleManager;
    private ShipImage m_ship;
    private Ship m_PlayerShip;

    private Label m_LifeAttributs;
    private Label m_FirePointsAttributs;
    private Label m_SpeedAttributs;
    private Label m_MovePointsAttributs;

    {
        m_HUDButtons = new ArrayList<>();
        m_EnemyShips = new ArrayList<>();
        m_ShipLifeLabels = new ArrayList<>();
    }

    public BattleScreen(FTWGame game, Batch batch, Viewport hud_viewport, Viewport game_viewport)
    {
        super(game, batch, hud_viewport, game_viewport);

        m_PlayerShip = game().player().ship();

        Skin skin = game.assets().get(Assets.SKIN_DEFAULT);
        setColor(skin.getColor("background-reversed"));
        TextButtonStyle style = skin.get("default-reversed", TextButtonStyle.class);

        Table table = new Table();
        table.setFillParent(true);
        table.center().bottom();
        hud().addActor(table);

        HUDInit(table, skin, style);

    }

    private void HUDInit(Table table, Skin skin, TextButtonStyle style)
    {

        final ActionButton back_button = new ActionButton("Retreat !", style);
        back_button.getLabelCell().pad(10);
        back_button.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                pop();
            }
        });
        m_HUDButtons.add(back_button);
        table.add(back_button).padRight(20);

        /*
         * ActionButton button = new ActionButton ("Fire on\nPort Side", style);
         * button.addListener(new ChangeListener() {
         *
         * @Override public void changed(ChangeEvent event, Actor actor) {
         * System.out.println("FireLeft"); m_ship.portSideFire (); } });
         * hud().addActor(button); m_HUDButtons.add(button);
         * table.add(button).padRight(15);
         *
         * button = new ActionButton ("Fire on\nStarBoard Side", style);
         * button.addListener(new ChangeListener() {
         *
         * @Override public void changed(ChangeEvent event, Actor actor) {
         * System.out.println("FireRight"); m_ship.startboardSideFire(); } });
         * hud().addActor(button); m_HUDButtons.add(button);
         * table.add(button).padRight(150);
         */

        ActionButton button = new ActionButton("Fire !", style);
        button.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                System.out.println("Fire !");
                m_ship.shoot();
            }
        });
        hud().addActor(button);
        m_HUDButtons.add(button);
        table.add(button).padRight(150);

        Table tableAttributs = new Table();
        tableAttributs.setFillParent(true);
        tableAttributs.left().bottom();

        Label lifeLabel = new Label("Life: ", skin);
        m_LifeAttributs = new Label(String.valueOf(m_PlayerShip.health().value()), skin);
        tableAttributs.add(lifeLabel);
        tableAttributs.add(m_LifeAttributs);
        tableAttributs.row();
        Label speedLabel = new Label("Speed: ", skin);
        m_SpeedAttributs = new Label(String.valueOf(m_PlayerShip.speed().value()), skin);
        tableAttributs.add(speedLabel);
        tableAttributs.add(m_SpeedAttributs);
        tableAttributs.row();
        Label movePtLabel = new Label("Moves: ", skin);
        m_MovePointsAttributs = new Label("0", skin);
        tableAttributs.add(movePtLabel);
        tableAttributs.add(m_MovePointsAttributs);
        tableAttributs.row();
        Label firePtLabel = new Label("Shoots: ", skin);
        m_FirePointsAttributs = new Label("0", skin);
        tableAttributs.add(firePtLabel);
        tableAttributs.add(m_FirePointsAttributs);

        table.add(tableAttributs).padRight(150);

        button = new ActionButton("Left", style);
        button.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                System.out.println("Left");
                m_ship.leftRotate();
            }
        });
        hud().addActor(button);
        m_HUDButtons.add(button);
        table.add(button).padRight(20);

        Table tableMove = new Table();
        tableMove.setFillParent(true);
        tableMove.left().bottom();
        button = new ActionButton("Forward", style);
        button.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                System.out.println("Forward");
                m_ship.forwardMove();
            }
        });
        hud().addActor(button);
        m_HUDButtons.add(button);
        tableMove.add(button).padBottom(20).row();

        button = new ActionButton("Backward", style);
        button.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                System.out.println("Backward");
                m_ship.backwardMove();
            }
        });
        hud().addActor(button);
        m_HUDButtons.add(button);
        tableMove.add(button);

        table.add(tableMove).padRight(20);

        button = new ActionButton("Right", style);
        button.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                System.out.println("Right");
                m_ship.rightRotate();
            }
        });
        hud().addActor(button);
        m_HUDButtons.add(button);
        table.add(button).padRight(100);

        final ActionButton nextTurnButton = new ActionButton("End Turn", style);
        nextTurnButton.getLabelCell().pad(10);
        nextTurnButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                m_BattleManager.changeTurn();
            }
        });
        m_HUDButtons.add(nextTurnButton);
        table.add(nextTurnButton).padRight(20);
    }

    @Override
    public void init()
    {
        m_EnemyShips.clear();
        stage().clear();

        m_BattleManager = new BattleManager();

        float scale = 0.4f;

        m_ship = new ShipImage(game().assets().get(Assets.TEXTURE_SHIP_TOP_LARGE), game().player(), m_PlayerShip);
        m_ship.setSize(m_ship.getWidth() * scale, m_ship.getHeight() * scale);
        m_ship.setOrigin(m_ship.getWidth() / 2, m_ship.getHeight() / 2);
        stage().addActor(m_ship);

        int nbEnemies = MathUtils.random(1, 3);
        for (int i = 0; i < nbEnemies; i++) {
            Ship enemyShip = new Ship("enemy", new CrewMember("Roger"), 10)
                .health(MathUtils.random(100, 1000))
                .defense(MathUtils.random(100))
                .speed(MathUtils.random(100))
                .steering(MathUtils.random(100));

            Vector2 position = new Vector2(MathUtils.random(2000), MathUtils.random(1000));
            int direction = MathUtils.random(8);

            scale = 0.2f;

            ShipImage enemyShipImage = new ShipImage(game().assets().get(Assets.TEXTURE_SHIP_TOP_SMALL), null, enemyShip, position, direction);
            enemyShipImage.setSize(enemyShipImage.getWidth() * scale, enemyShipImage.getHeight() * scale);
            enemyShipImage.setOrigin(enemyShipImage.getWidth() / 2, enemyShipImage.getHeight() / 2);
            enemyShipImage.update();

            ShipLifeLabel lifeLbl = new ShipLifeLabel(enemyShipImage);

            m_EnemyShips.add(enemyShipImage);
            m_ShipLifeLabels.add(lifeLbl);
            stage().addActor(enemyShipImage);
            stage().addActor(lifeLbl);
        }

        m_ship.update();
        updateHUD();
    }

    private void updateHUD()
    {
        m_LifeAttributs.setText(String.valueOf(m_PlayerShip.health().value()));
        m_SpeedAttributs.setText(String.valueOf(m_PlayerShip.speed().value()));
        m_MovePointsAttributs.setText(String.valueOf(m_ship.movePoints()));
        m_FirePointsAttributs.setText(String.valueOf(m_ship.firePoints()));

        if (m_ShipLifeLabels.isEmpty())
            return;

        ArrayList<ShipLifeLabel> slis = null;
        for (ShipLifeLabel sli : m_ShipLifeLabels) {
            sli.update();
            if (sli.isDead()) {
                if (slis == null)
                    slis = new ArrayList<>();

                slis.add(sli);
            }
        }

        if (slis != null)
            for (ShipLifeLabel sli : slis)
                sli.destroy();
    }

    private void activateButtons()
    {
        for (ActionButton button : m_HUDButtons)
            button.setTouchable(Touchable.enabled);
    }

    private void deactivateButtons()
    {
        for (ActionButton button : m_HUDButtons)
            button.setTouchable(Touchable.disabled);
    }

    private class ActionButton extends TextButton
    {
        public ActionButton(String name, TextButtonStyle style)
        {
            super(name, style);
        }

        public ActionButton(String name, Skin skin)
        {
            super(name, skin);
        }
    }

    private class ShipImage extends Image
    {
        static final int rotationAngle = 45;

        private Player m_player;
        private Ship m_Ship;
        private Vector2 m_Position;
        private Vector2 m_Direction;

        private int m_MovePoints;
        private int m_FirePoints;

        {
            m_Direction = new Vector2(1f, 0f);
            m_MovePoints = 5;
            m_FirePoints = 1;
        }

        public ShipImage(Texture texture, Player player, Ship ship)
        {
            super(texture);
            m_player = player;
            m_Ship = ship;

            m_Position = new Vector2();
            m_Position.x = stage().getWidth() / 2;
            m_Position.y = stage().getHeight() / 2;
        }

        public ShipImage(Texture texture, Player player, Ship ship, Vector2 position, int direction)
        {
            super(texture);

            m_player = player;
            m_Ship = ship;

            m_Position = position;
            rotateBy(direction * rotationAngle);
            m_Direction.x = MathUtils.cosDeg(getRotation());
            m_Direction.y = MathUtils.sinDeg(getRotation());
        }

        public void refuelActionPoints()
        {
            m_MovePoints = 5;
            m_FirePoints = 1;
            updateHUD();
        }

        public void forwardMove()
        {
            if (!canMove())
                return;

            useMovePoint();
            m_Position.x += m_Direction.x * m_Ship.speed().value();
            m_Position.y += m_Direction.y * m_Ship.speed().value();
            update();
        }

        public void backwardMove()
        {
            if (!canMove())
                return;

            useMovePoint();
            m_Position.x -= m_Direction.x * m_Ship.speed().value();
            m_Position.y -= m_Direction.y * m_Ship.speed().value();
            update();
        }

        public void leftRotate()
        {
            if (!canMove())
                return;

            useMovePoint();
            rotateBy(rotationAngle);
            m_Direction.x = MathUtils.cosDeg(getRotation());
            m_Direction.y = MathUtils.sinDeg(getRotation());
            update();
        }

        public void rightRotate()
        {
            if (!canMove())
                return;

            useMovePoint();
            rotateBy(-rotationAngle);
            m_Direction.x = MathUtils.cosDeg(getRotation());
            m_Direction.y = MathUtils.sinDeg(getRotation());
            System.out.println(String.valueOf(m_Direction.x) + " | " + String.valueOf(m_Direction.y));
            update();
        }

        public void portSideFire()
        {
            shoot(45);
        }

        public void startboardSideFire()
        {
            shoot(-45);
        }

        public void shoot(int angle)
        {
            if (!canShoot())
                return;

            if (m_BattleManager.isPlayerTurn) {
                ShipImage toShoot = null;
                for (ShipImage ship : m_EnemyShips) {
                    float distance = (float) Math.sqrt((m_Position.x - ship.m_Position.x) * (m_Position.x - ship.m_Position.x) + (m_Position.y - ship.m_Position.y) * (m_Position.y - ship.m_Position.y));
                    if (distance < 100) {
                        float diffAngle = (float) Math.acos(m_ship.m_Direction.dot(ship.m_Position));
                        if (diffAngle > angle - 10 && diffAngle > angle + 10) {
                            toShoot = ship;
                        }
                    }
                }

                if (toShoot != null) {
                    useFirePoint();
                    toShoot.takeDamage();
                }
            } else {
                float distance = (float) Math.sqrt((m_Position.x - m_ship.m_Position.x) * (m_Position.x - m_ship.m_Position.x) + (m_Position.y - m_ship.m_Position.y) * (m_Position.y - m_ship.m_Position.y));
                if (distance < 100) {
                    float diffAngle = (float) Math.acos(m_ship.m_Direction.dot(m_ship.m_Position));
                    if (diffAngle > angle - 10 && diffAngle > angle + 10) {
                        useFirePoint();
                        m_ship.takeDamage();
                    }
                }
            }
        }

        /**
         * Use to shoot on enemy ship without taking care of the shooting angle
         *
         */
        public void shoot()
        {
            if (!canShoot())
                return;

            if (m_BattleManager.isPlayerTurn) {
                ShipImage toShoot = null;
                for (ShipImage ship : m_EnemyShips) {
                    float distance = (float) Math.sqrt((m_Position.x - ship.m_Position.x) * (m_Position.x - ship.m_Position.x) + (m_Position.y - ship.m_Position.y) * (m_Position.y - ship.m_Position.y));
                    if (distance < 200) {
                        toShoot = ship;
                    }
                }

                if (toShoot != null) {
                    useFirePoint();
                    toShoot.takeDamage();
                }
            } else {
                float distance = (float) Math.sqrt((m_Position.x - m_ship.m_Position.x) * (m_Position.x - m_ship.m_Position.x) + (m_Position.y - m_ship.m_Position.y) * (m_Position.y - m_ship.m_Position.y));
                if (distance < 150) {
                    useFirePoint();
                    m_ship.takeDamage();
                }
            }
        }

        public void takeDamage()
        {
            if (m_Ship.damage(300) < 0) {
                destroy();
            }

            update();
        }

        public void destroy()
        {
            if (m_player != null) {
                Gdx.app.exit();

            } else {
                if (m_EnemyShips.contains(this)) {
                    m_EnemyShips.remove(this);
                    this.remove();
                }

                m_BattleManager.checkEndBattle();
            }

        }

        public void update()
        {
            checkShipPosition();
            setPosition(m_Position.x - getWidth() / 2, m_Position.y - getHeight() / 2);
            updateHUD();
        }

        public void checkShipPosition()
        {
            float sizeShip = getWidth() * getScaleX() / 2;
            if (m_Position.x < 0 + sizeShip)
                m_Position.x = 0 + sizeShip;
            else if (m_Position.x > stage().getWidth() - sizeShip)
                m_Position.x = stage().getWidth() - sizeShip;

            if (m_Position.y < 0 + sizeShip)
                m_Position.y = 0 + sizeShip;
            else if (m_Position.y > stage().getHeight() - sizeShip)
                m_Position.y = stage().getHeight() - sizeShip;
        }

        public boolean canMove()
        {
            if (m_MovePoints > 0)
                return true;

            return false;
        }

        public void useMovePoint()
        {
            m_MovePoints--;
        }

        public boolean canShoot()
        {
            if (m_FirePoints > 0)
                return true;

            return false;
        }

        public void useFirePoint()
        {
            m_FirePoints--;
        }

        public int movePoints()
        {
            return m_MovePoints;
        }

        public int firePoints()
        {
            return m_FirePoints;
        }
    }

    private class ShipLifeLabel extends Label
    {
        private ShipImage m_ShipImage;
        private int m_MaxHealth;
        private boolean m_IsDead;

        {
            m_IsDead = false;
        }

        public ShipLifeLabel(ShipImage shipImage)
        {
            super("100 / 100", game().assets().get(Assets.SKIN_DEFAULT).get("default-small", LabelStyle.class));

            m_ShipImage = shipImage;
            m_MaxHealth = m_ShipImage.m_Ship.health().value();

            setColor(1, 0, 0, 1);

            update();
        }

        public void update()
        {
            int life = m_ShipImage.m_Ship.health().value();

            if (life <= 0)
                return;

            setText(life + " / " + m_MaxHealth);
            setPosition(m_ShipImage.m_Position.x - getWidth() * getScaleX() / 2, m_ShipImage.m_Position.y - getHeight() * getScaleY() - m_ShipImage.getHeight() / 2);
        }

        public void destroy()
        {
            if (m_ShipLifeLabels.contains(this)) {
                m_ShipLifeLabels.remove(this);
            }
            this.remove();
        }

        public boolean isDead()
        {
            return m_IsDead;
        }
    }

    private class BattleManager
    {
        private boolean isPlayerTurn;
        private EnemyPlayer enemyIA;
        {
            isPlayerTurn = true;
            enemyIA = new EnemyPlayer();
        }

        public void changeTurn()
        {
            isPlayerTurn = !isPlayerTurn;
            if (isPlayerTurn) {
                activateButtons();
                m_ship.refuelActionPoints();
            } else {
                deactivateButtons();
                enemyIA.refuelActionPoints();
                enemyIA.runTurn();
            }
        }

        public void checkEndBattle()
        {
            if (m_EnemyShips.isEmpty()) {
                pop();
            }
        }
    }

    private class EnemyPlayer
    {
        public void refuelActionPoints()
        {
            for (ShipImage ship : m_EnemyShips)
                ship.refuelActionPoints();
        }

        public void runTurn()
        {
            int k = 0;
            for (final ShipImage ship : m_EnemyShips) {
                Timer.schedule(new Timer.Task()
                {
                    @Override
                    public void run()
                    {
                        for (int i = 0; i < ship.m_MovePoints; i++) {
                            int ran = MathUtils.random(100);
                            if (ran < 25)
                                ship.forwardMove();
                            else if (ran < 45)
                                ship.backwardMove();
                            else if (ran < 65)
                                ship.leftRotate();
                            else if (ran < 85)
                                ship.rightRotate();

                            ship.shoot();
                        }
                    }
                }, k);

                k++;
            }
            Timer.schedule(new Timer.Task()
            {
                @Override
                public void run()
                {
                    m_BattleManager.changeTurn();
                }
            }, m_EnemyShips.size());
        }
    }

}
