package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sun.audio.AudioPlayer;


public class Main extends Application {

    int saed = 0;
    int siz =2;
    Ball[] balls;
    double dx, dy, v1, v2, r1, r2, m1, m2;
    double angle, a1, a2;
    public static final double e =1;
    public static final double mui=0.2;
    public static final double g =9.87;

    public void updateValue() {
        for (int i = 0; i < balls.length; i++) {
            for (int j = i + 1; j < balls.length; j++) {

                dx = (balls[i].getX()) - (balls[j].getX()); // المسافة بين الكرتين على المحور x

                dy = (balls[i].getY()) - (balls[j].getY());// المسافة بين الكرتين على المحور y

                r1 = balls[i].radios;

                r2 = balls[j].radios;

                m1 = balls[i].getMass();

                m2 = balls[j].getMass();

                v1 = balls[i].getVelocity();

                v2 = balls[j].getVelocity();


                // شرط الصدم   " أن تكون المسافة بين المركزين أصغر من مجموع نصفي القطرين "
                if (Math.sqrt(dx * dx + dy * dy) <= r1 + r2) {

//                    String path2 = AudioPlayer.class.getResource("/BinaryContent/Coins.mp3").toString();
//                    Media media2 = new Media(path2);
//                    MediaPlayer mp2 = new MediaPlayer(media2);
//                    mp2.stop();
//                    mp2.play();
                    angle = (Math.atan2(dy, dx));
                    a1 = balls[i].getVelocityAngle() - angle;
                    a2 = balls[j].getVelocityAngle() - angle;

                    division(i,j,angle);//تفريق الكرات في حالة الالتحام


                    double vt1 = ((v1 * Math.cos(a1) * (m1 - e * m2)) + ((1 + e) * m2 * v2 * Math.cos(a2))) / (m1 + m2);
                    double vt2 = ((v2 * Math.cos(a2) * (m2 - e * m1)) + ((1 + e) * m1 * v1 * Math.cos(a1))) / (m1 + m2);
                    double vn1 = v1 * Math.sin(a1);
                    double vn2 = v2 * Math.sin(a2);
                    double newAngle = angle + (Math.PI / 2);

                    balls[i].setVx(vt1 * Math.cos(angle) + vn1 * Math.cos(newAngle));
                    balls[i].setVy(vt1 * Math.sin(angle) + vn1 * Math.sin(newAngle));

                    balls[j].setVx(vt2 * Math.cos(angle) + vn2 * Math.cos(newAngle));
                    balls[j].setVy(vt2 * Math.sin(angle) + vn2 * Math.sin(newAngle));


                }
            }
        }

//        يمر على كل الكرات ويقوم بتحديث الموقع والسرعة
        for (int i = 0; i <balls.length ; i++) {

            balls[i].updateCenter();
            balls[i].collision(); // دراسة الصدم مع الطاولة

/*            if (((balls[i].getCenterX() + balls[i].getRadius()) >= 946 && (balls[i].getCenterX() + balls[i].getRadius()) < 956) ||
                    ((balls[i].getCenterX() - balls[i].getRadius()) <= 252 && (balls[i].getCenterX() - (balls[i].getRadius()) > 242) ||
                    (((balls[i].getCenterY() + (balls[i].getRadius()) >= 528 && ((balls[i].getCenterY() + (balls[i].getRadius()) < 538) ||
                    ((balls[i].getCenterY() - balls[i].getRadius()) <= 169 && (balls[i].getCenterY() - balls[i].getRadius()) > 159))))))) {
                //System.out.println("saed"+"\n");
            }*/
        }


    }


    /**
     * @param mx mouse X
     * @param my mouse Y
     */
    // لاعطاء الكرة البيضاء سرعة بحسب البعد بينها وبين مؤشر الماوس
    public void VelocityPos(double mx, double my) {

        if (balls[saed].getVelocity() == 0) {
            double tx = (mx - balls[saed].getCenterX()) / 30;
            double ty = (my - balls[saed].getCenterY()) / 30;

            balls[saed].setVx(tx);
            balls[saed].setVy(ty);
        }
    }
    void  division (int i,int j,double angle){

        balls[i].setCenterX(balls[j].getCenterX() + balls[j].getRadius() * Math.cos(angle) + balls[i].getRadius() * Math.cos(angle));
        balls[i].setCenterY(balls[j].getCenterY() + balls[j].getRadius() * Math.sin(angle) + balls[i].getRadius() * Math.sin(angle));
        balls[j].setCenterX(balls[i].getCenterX() + balls[i].getRadius() * Math.cos(angle + Math.PI) + balls[j].getRadius() * Math.cos(angle + Math.PI));
        balls[j].setCenterY(balls[i].getCenterY() + balls[i].getRadius() * Math.sin(Math.PI + angle) + balls[j].getRadius() * Math.sin(Math.PI + angle));
    }

    HBox box = new HBox();

    @Override
    public void start(Stage primaryStage) {

        VBox root = new VBox(10);
        Image image = new Image("/BinaryContent/frontpic.jpg", 1400, 700, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);

        Button button1 = new Button("New Game");
        Button button2 = new Button("Quit");

        button2.setOnAction(e -> {
            System.exit(0);
        });
        button1.setLayoutX(500);
        button1.setLayoutY(600);
        button2.setLayoutX(500);
        button2.setLayoutY(700);
        root.setAlignment(Pos.CENTER);

        root.getChildren().addAll(button1, button2);
        root.setBackground(background);

        Scene scene = new Scene(root, 1400, 700);
        ;
        primaryStage.setTitle("POOL GAME");

        primaryStage.setScene(scene);

        Group root2 = new Group();
        Scene scene2 = new Scene(root2, 1300, 700);

        Image im = new Image("/BinaryContent/table.png");
        ImageView iv = new ImageView(im);
        iv.setFitWidth(800);
        iv.setPreserveRatio(true);

        StackPane p = new StackPane();
        p.setPrefSize(700, 700);

        p.getChildren().addAll(iv);

        StackPane.setAlignment(iv, Pos.CENTER);
        StackPane.setMargin(iv, new Insets(50, 50, 50, 200));
        root2.getChildren().add(p);

        button1.setOnAction(e -> {

            primaryStage.setScene(scene2);
            new AnimationTimer() {
                @Override
                public void handle(long now) {
                    updateValue();
                }
            }.start();

//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    while (true)
//                    {
//                        try {
//                            Thread.sleep(10);
//                        } catch (InterruptedException interruptedException) {
//                            interruptedException.printStackTrace();
//                        }
//                        updateValue();
//                    }
//                }
//            }).start();
        });

        balls = new Ball[10];


        // balls[19] = new Ball(965, 158, root2, 35, 0,0, Color.YELLOW);

        balls[9] = new Ball(785, 348, root2, 10.5, 0, 0,2, Color.VIOLET);


        balls[1] = new Ball(785 + 18, 348 - 11, root2, 10.5, 0, 0,2, Color.DARKRED);

        balls[2] = new Ball(785 + 18, 348 + 11, root2, 10.5, 0, 0,2, Color.GREENYELLOW);


        balls[3] = new Ball(785 + 18 * 2, 348, root2, 10.5, 0, 0,2, Color.DARKGREEN);


        balls[4] = new Ball(785 + 18 * 2, 348 + 22, root2, 10.5, 0, 0,2, Color.YELLOW);


        balls[5] = new Ball(785 + 18 * 2, 348 - 22, root2, 10.5, 0, 0,2, Color.DARKORANGE);


        balls[6] = new Ball(785 + 18 * 3, 348 + 11, root2, 10.5, 0, 0,2, Color.DARKBLUE);


        balls[7] = new Ball(785 + 18 * 3, 348 - 11, root2, 10.5, 0, 0,2, Color.CYAN);


        balls[8] = new Ball(785 + 18 * 4, 348, root2, 10.5, 0, 0,2, Color.RED);


        balls[saed] = new Ball(300, 348, root2, 10.5, 0, 0,2, Color.WHITE);

        box.setRotate(50);

        root2.getChildren().addAll(box);
        primaryStage.show();

        EventHandler<MouseEvent> mouseHandler = mouseEvent -> {

            if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
                double mx = mouseEvent.getX();
                double my = mouseEvent.getY();
                VelocityPos(mx, my);
            }
        };

        scene2.setOnMouseClicked(mouseHandler);
        scene2.setOnMouseDragged(mouseHandler);
        scene2.setOnMouseEntered(mouseHandler);
        scene2.setOnMouseExited(mouseHandler);
        scene2.setOnMouseMoved(mouseHandler);
        scene2.setOnMousePressed(mouseHandler);
        scene2.setOnMouseReleased(mouseHandler);

    }

    public static void main(String[] args) {
        launch(args);
    }
}


// هذا الكود لابعاد مراكز الكرات عن بعضها في حالة السكون وبعد الصدم الخفيف


