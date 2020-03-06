package animatedscene;

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

public class AnimatedScene extends JFrame implements Runnable {
	static final int WINDOW_WIDTH = 1100;
	static final int WINDOW_HEIGHT = 700;
	final int XBORDER = 0;
	final int YBORDER = 0;
	final int YTITLE = 25;
	boolean animateFirstTime = true;
	int xsize = -1;
	int ysize = -1;
	Image image;
	Graphics2D g;

	int personYPos;
	int rotateStar;
	int timeCount;
	double starGrow;
	boolean personVisible;
	int ufoMoveX;
	int ufoMoveY;
	int ufoRotate;
	double ufoScale;
	double beamShrink;
	boolean beamBool;
	int beamX;
	int personRotate;
	boolean alienVisible;
	int alienFade;
	int alienY;
	double alienScale;
	double scaleVal;
	boolean textVisible;
	int textX;
	int textFade;
	int rectX;
	boolean callEnd;
	int rectY;
	int rectScale;
	boolean endVisible;
	int signY;
	boolean text3Visible;
	static AnimatedScene frame;
	public static void main(String[] args) {
		frame = new AnimatedScene();
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public AnimatedScene() {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.BUTTON1 == e.getButton()) {
					//left button

					// location of the cursor.
					int xpos = e.getX();
					int ypos = e.getY();

				}
				if (e.BUTTON3 == e.getButton()) {
					//right button
					reset();
				}
				repaint();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				repaint();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {

				repaint();
			}
		});

		addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (e.VK_UP == e.getKeyCode()) {} else if (e.VK_DOWN == e.getKeyCode()) {} else if (e.VK_LEFT == e.getKeyCode()) {} else if (e.VK_RIGHT == e.getKeyCode()) {}
				repaint();
			}
		});
		init();
		start();
	}
	Thread relaxer;
	////////////////////////////////////////////////////////////////////////////
	public void init() {
		requestFocus();
	}
	////////////////////////////////////////////////////////////////////////////
	public void destroy() {}

	////////////////////////////////////////////////////////////////////////////
	public void paint(Graphics gOld) {
		if (image == null || xsize != getSize().width || ysize != getSize().height) {
			xsize = getSize().width;
			ysize = getSize().height;
			image = createImage(xsize, ysize);
			g = (Graphics2D) image.getGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		}
		//fill background
		g.setColor(Color.cyan);
		g.fillRect(0, 0, xsize, ysize);

		int x[] = {
			getX(0), getX(getWidth2()), getX(getWidth2()), getX(0), getX(0)
		};
		int y[] = {
			getY(0), getY(0), getY(getHeight2()), getY(getHeight2()), getY(0)
		};
		//fill border
		g.setColor(Color.white);
		g.fillPolygon(x, y, 4);
		// draw border
		g.setColor(Color.red);
		g.drawPolyline(x, y, 5);

		if (animateFirstTime) {
			gOld.drawImage(image, 0, 0, null);
			return;
		}

		drawSky();
		drawGround();
		if (personVisible) {

			g.setColor(Color.BLACK);
			drawPerson(210, personYPos, personRotate, 1, 1);
		}
		drawUFO2(ufoMoveX, ufoMoveY, 0, ufoScale, ufoScale);

		drawSign();
		if (textVisible) {
			drawText(textX, 400, 0, 1, 1);
		}
		if (callEnd) {
			drawEnd(rectX, rectY, 0, rectScale, rectScale);
		}
		//        drawAlien(500,500, 0, 1, 1);
		if (endVisible) {
			drawText2(textX, 400, 0, 1, 1);
		}

		gOld.drawImage(image, 0, 0, null);

	}

	////////////////////////////////////////////////////////////////////////////
	public void drawSky() {
		// x1: x coordinate of the first specified point in user space
		//y1: y coordinate of the first specified point in user space
		//color1: color at the first specified point
		//x2: x coordinate of the second specified point in user space
		//y2: y coordinate of the second specified point in user space
		//color2: color at the second specified point
		Color sky1 = new Color(34, 38, 110);
		Color sky2 = new Color(9, 7, 69);
		GradientPaint blueToBlack = new GradientPaint(300, 0, sky1, 400, 400, sky2);

		g.setPaint(blueToBlack);
		g.fillRect(0, 0, 4000, 5000);
		Color starYellow = new Color(235, 207, 70);
		g.setColor(starYellow);

		drawStar2(600, 200, rotateStar, starGrow, starGrow);
		drawStar(300, 100, 0, 0.1, .1);
		drawStar2(200, 150, 0, .3, .3);
		drawStar(750, 300, 100 - rotateStar, starGrow, starGrow);
		drawStar2(500, 400, rotateStar, starGrow, starGrow);
		drawStar(650, 275, rotateStar, .5, .5);
		drawStar2(800, 200, 0, 0.5, 0.5);
		drawStar(400, 300, 0, .1, .1);
		drawStar2(150, 50, 50 - rotateStar, starGrow, starGrow);
		drawStar(850, 350, 0, .2, .2);
		drawStar2(675, 200, 0, .1, .1);
		drawStar(800, 105, 0, starGrow, starGrow);
		drawStar2(500, 50, 0, .3, .3);
		drawStar(850, 350, rotateStar, starGrow, starGrow);
		drawStar2(75, 300, 0, .1, .1);
		drawStar(900, 105, 0, .2, .2);

		Color moonColor = new Color(209, 221, 222);
		g.setColor(moonColor);
		g.fillOval(950, 75, 70, 70);

	}

	public void drawStar(int xpos, int ypos, double rot, double xscale, double yscale) {
		g.translate(xpos, ypos);
		g.rotate(rot * Math.PI / 180.0);
		g.scale(xscale, yscale);

		int xval[] = { 0, 10, 40, 10, 0, -10, -40, -10, 0 };
		int yval[] = {-40, -10, 0, 10, 40, 10, 0, -10, -40
		};
		g.fillPolygon(xval, yval, xval.length);

		g.scale(1.0 / xscale, 1.0 / yscale);
		g.rotate(-rot * Math.PI / 180.0);
		g.translate(-xpos, -ypos);
	}
	public void drawStar2(int xpos, int ypos, double rot, double xscale, double yscale) {
		g.translate(xpos, ypos);
		g.rotate(rot * Math.PI / 180.0);
		g.scale(xscale, yscale);

		int xval[] = { 0, 7, 25, 15, 40, 15, 25, 7, 0, -7, -25, -15, -40, -15, -25, -7, 0 };
		int yval[] = {-40, -15, -25, -7, 0, 7, 25, 15, 40, 15, 25, 7, 0, -7, -25, -15, -40
		};
		g.fillPolygon(xval, yval, xval.length);

		g.scale(1.0 / xscale, 1.0 / yscale);
		g.rotate(-rot * Math.PI / 180.0);
		g.translate(-xpos, -ypos);
	}
	public void drawGround() {
		Color scaryGreen = new Color(32, 48, 33);
		g.setColor(scaryGreen);
		g.fillRect(0, 450, 4000, 5000);

		g.setColor(Color.BLACK);
		Color signFont = new Color(13, 24, 24);
		g.setColor(signFont);
		drawRoad(620, 448, 1, 5, 5);
	}
	public void drawUFO2(int xpos, int ypos, double rot, double xscale, double yscale) {
		g.translate(xpos, ypos);
		g.rotate(rot * Math.PI / 180.0);
		g.scale(xscale, yscale);

		if (beamBool) {
			drawBeam(210, beamX, 0, beamShrink, beamShrink);
		}
		//       if(personVisible){
		//       Color signFont = new Color (43, 34, 34);
		//       g.setColor(signFont);
		//       drawPerson(200, personYPos, personRotate, 1, 1);
		//       }

		Color ufoGray = new Color(170, 180, 181);
		g.setColor(ufoGray);
		g.fillArc(110, 150, 200, 260, 180, -180);

		Color ufoGray2 = new Color(103, 109, 110);
		g.setColor(ufoGray2);
		g.fillOval(15, 255, 400, 50);

		Color ufoWindow = new Color(206, 217, 219);
		g.setColor(ufoWindow);
		g.fillOval(169, 175, 75, 75);
		drawMirror(125, 175, 20, 1, 1);
		drawMirror(265, 185, -20, 1, 1);
		if (alienVisible) {
			drawAlien(163, alienY, 0, alienScale, alienScale);
		}

		g.scale(1.0 / xscale, 1.0 / yscale);
		g.rotate(-rot * Math.PI / 180.0);
		g.translate(-xpos, -ypos);
	}
	//    public void drawUFO()
	//    {
	//       if(beamShrink){
	//      drawBeam(210, 350, 0,1,1 );
	//       }
	//      Color signFont = new Color (43, 34, 34);
	//       g.setColor(signFont );
	//       drawPerson(200, personYPos, 30, 1, 1);
	//   
	//       Color ufoGray = new Color (170, 180, 181);
	//       g.setColor(ufoGray);
	//       g.fillArc(110, 150, 200, 260, 180, -180);
	//       
	//       Color ufoGray2 = new Color (103, 109, 110);
	//       g.setColor(ufoGray2);
	//       g.fillOval(15, 255, 400, 50);
	//       
	//       Color ufoWindow = new Color (206, 217, 219);
	//       g.setColor(ufoWindow);
	//       g.fillOval(169, 175, 75, 75);
	//       drawMirror(125, 175, 20, 1, 1);
	//       drawMirror(265, 185, -20, 1, 1);
	//       
	//       
	//       
	//    }
	public void drawMirror(int xpos, int ypos, double rot, double xscale, double yscale) {
		g.translate(xpos, ypos);
		g.rotate(rot * Math.PI / 180.0);
		g.scale(xscale, yscale);

		g.fillOval(0, 0, 30, 75);

		g.scale(1.0 / xscale, 1.0 / yscale);
		g.rotate(-rot * Math.PI / 180.0);
		g.translate(-xpos, -ypos);
	}
	public void drawBeam(int xpos, int ypos, double rot, double xscale, double yscale) {
		g.translate(xpos, ypos);
		g.rotate(rot * Math.PI / 180.0);
		g.scale(xscale, yscale);

		Color beamYellow = new Color(250, 252, 179, 100);
		g.setColor(beamYellow);
		int xval[] = { 0, 30, -30, 0 };
		int yval[] = {-40, 50, 50, -40
		};
		g.fillPolygon(xval, yval, xval.length);

		g.scale(1.0 / xscale, 1.0 / yscale);
		g.rotate(-rot * Math.PI / 180.0);
		g.translate(-xpos, -ypos);
	}
	public void drawSign() {
		Color signBack = new Color(132, 68, 83);
		g.setColor(signBack);
		g.fillRect(700, 400, 350, 250);

		Color signFont = new Color(43, 34, 34);
		g.setColor(signFont);

		g.setFont(new Font("Century Gothic", Font.PLAIN, 50));
		g.drawString("Welcome", 770, 500);
		g.drawString("to", 850, 550);
		g.setFont(new Font("Freestyle Script", Font.PLAIN, 80));
		g.drawString("Farmington!", 750, signY);
		g.fillRect(770, 650, 30, 60);
		g.fillRect(960, 650, 30, 60);
	}
	public void drawPerson(int xpos, int ypos, double rot, double xscale, double yscale) {
		g.translate(xpos, ypos);
		g.rotate(rot * Math.PI / 180.0);
		g.scale(xscale, yscale);

		g.fillOval(-10, -38, 20, 20);

		int xval[] = { 10, 20, 25, 10, 10, 15, 10, 0, -10, -15, -10, -10, -25, -20, -10, -10 };
		int yval[] = {-20, -30, -25, -10, 20, 35, 40, 20, 40, 35, 20, -10, -25, -30, -20, -20
		};
		g.fillPolygon(xval, yval, xval.length);

		g.scale(1.0 / xscale, 1.0 / yscale);
		g.rotate(-rot * Math.PI / 180.0);
		g.translate(-xpos, -ypos);
	}
	public void drawRoad(int xpos, int ypos, double rot, double xscale, double yscale) {
		g.translate(xpos, ypos);
		g.rotate(rot * Math.PI / 180.0);
		g.scale(xscale, yscale);

		int xval[] = { 11, 15, 5, -40, 10 };
		int yval[] = { 0, 0, 55, 55, 0 };
		g.fillPolygon(xval, yval, xval.length);

		g.scale(1.0 / xscale, 1.0 / yscale);
		g.rotate(-rot * Math.PI / 180.0);
		g.translate(-xpos, -ypos);
	}
	public void drawAlien(int xpos, int ypos, double rot, double xscale, double yscale) {
		g.translate(xpos, ypos);
		g.rotate(rot * Math.PI / 180.0);
		g.scale(xscale, yscale);

		Color aliengreen = new Color(40, 128, 19, alienFade);
		g.setColor(aliengreen);
		g.fillOval(10, 10, 50, 30);
		Color alienwhite = new Color(235, 239, 240, alienFade);
		g.setColor(alienwhite);
		g.fillOval(8, 8, 13, 13);
		g.fillOval(48, 8, 13, 13);
		Color alienblack = new Color(21, 25, 26, alienFade);
		g.setColor(alienblack);
		g.drawArc(30, 20, 10, 10, -180, 180);
		g.fillOval(10, 10, 8, 8);
		g.fillOval(51, 10, 8, 8);
		g.setColor(alienwhite);
		g.fillOval(9, 10, 5, 5);
		g.fillOval(50, 10, 5, 5);

		g.scale(1.0 / xscale, 1.0 / yscale);
		g.rotate(-rot * Math.PI / 180.0);
		g.translate(-xpos, -ypos);
	}
	public void drawText(int xpos, int ypos, double rot, double xscale, double yscale) {
		g.translate(xpos, ypos);
		g.rotate(rot * Math.PI / 180.0);
		g.scale(xscale, yscale);
		Color alienblack = new Color(0, 0, 0, textFade);
		g.setColor(alienblack);
		g.setFont(new Font("Century Gothic", Font.PLAIN, 50));
		g.drawString("AHHHHHHHHH", 0, 0);

		g.scale(1.0 / xscale, 1.0 / yscale);
		g.rotate(-rot * Math.PI / 180.0);
		g.translate(-xpos, -ypos);
	}
	public void drawEnd(int xpos, int ypos, double rot, double xscale, double yscale) {
		g.translate(xpos, ypos);
		g.rotate(rot * Math.PI / 180.0);
		g.scale(xscale, yscale);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 300, 300);

		g.scale(1.0 / xscale, 1.0 / yscale);
		g.rotate(-rot * Math.PI / 180.0);
		g.translate(-xpos, -ypos);
	}
	public void drawText2(int xpos, int ypos, double rot, double xscale, double yscale) {
		g.translate(xpos, ypos);
		g.rotate(rot * Math.PI / 180.0);
		g.scale(xscale, yscale);

		Color alienblack = new Color(255, 255, 255);
		g.setColor(alienblack);
		g.setFont(new Font("Century Gothic", Font.PLAIN, 50));
		g.drawString("THE END", 0, 0);

		g.scale(1.0 / xscale, 1.0 / yscale);
		g.rotate(-rot * Math.PI / 180.0);
		g.translate(-xpos, -ypos);
	}
	public void drawtext3(int xpos, int ypos, double rot, double xscale, double yscale) {
		g.translate(xpos, ypos);
		g.rotate(rot * Math.PI / 180.0);
		g.scale(xscale, yscale);

		Color alienblack = new Color(255, 255, 255);
		g.setColor(alienblack);
		g.setFont(new Font("Century Gothic", Font.PLAIN, 50));
		g.drawString("frogs rule!", 0, 0);

		g.scale(1.0 / xscale, 1.0 / yscale);
		g.rotate(-rot * Math.PI / 180.0);
		g.translate(-xpos, -ypos);
	}

	////////////////////////////////////////////////////////////////////////////
	// needed for     implement runnable
	public void run() {
		while (true) {
			animate();
			repaint();
			double seconds = 0.02; //time that 1 frame takes.
			int miliseconds = (int)(1000.0 * seconds);
			try {
				Thread.sleep(miliseconds);
			} catch (InterruptedException e) {}
		}
	}
	/////////////////////////////////////////////////////////////////////////
	public void reset() {
		timeCount = 0;
		personYPos = 550;
		rotateStar = 30;
		starGrow = 0.1;
		personVisible = true;
		ufoMoveX = -300;
		ufoMoveY = -100;
		ufoRotate = 0;
		ufoScale = 1;
		beamShrink = 2;
		beamBool = false;
		beamX = 300;
		personRotate = 0;
		alienVisible = false;
		alienFade = 0;
		alienY = 195;
		alienScale = 1.25;
		scaleVal = 1;
		textVisible = false;
		textX = 0;
		textFade = 2;
		rectX = 3;
		callEnd = false;
		rectY = 0;
		rectScale = 1;
		endVisible = false;
		signY = 700;
	}
	/////////////////////////////////////////////////////////////////////////
	public void animate() {
		if (animateFirstTime) {
			animateFirstTime = false;
			if (xsize != getSize().width || ysize != getSize().height) {
				xsize = getSize().width;
				ysize = getSize().height;
			}

			reset();
		}

		//ufo branch
		if (timeCount<150) {
			ufoMoveX += 2;
			ufoMoveY++;

		} else if (timeCount > 520) {
			ufoMoveX += 1;
			ufoMoveY -= 0.5;
			ufoScale += -0.01;
		}
		//beam branch
		if (timeCount > 150 && timeCount<175) {
			beamBool = true;
			beamShrink += 0.09;
			beamX += 2;

		} else if (timeCount > 300 && timeCount<350) {
			beamX -= 2;
			beamShrink += -.09;
		} else if (timeCount > 350) {
			beamBool = false;
		}
		//person branch
		if (timeCount > 175 && timeCount<300) {

			personYPos -= 2;
			personRotate += 3;

		} else if (timeCount > 300) {
			personVisible = false;
		}
		//star branch
		scaleVal += starGrow;
		if (scaleVal > 4) {
			starGrow = -0.1;
		}
		if (scaleVal<.7) {
			starGrow = 0.1;
		}

		scaleVal += starGrow;
		if (scaleVal > 3.0) {
			starGrow = -0.2;
		} else if (scaleVal<.6) {
			starGrow += 0.2;
		}

		//alien branch
		if (timeCount > 350 && timeCount<430) {
			alienVisible = true;
			alienFade += 3;
		} else if (timeCount > 440 && timeCount<520) {
			//alienY+=3;
			alienFade -= 3;
		} else if (timeCount > 520) {
			alienVisible = false;
		}

		//text branch
		if (timeCount > 175 && timeCount<250) {
			textFade += 5;
			textVisible = true;
			textX += 3;
		}
		if (timeCount > 200 && timeCount<275) {
			textFade -= 5;
		} else if (timeCount > 275) {
			textVisible = false;
		}
		if (timeCount > 650) {
			callEnd = true;
			rectScale += 1;
		}
		if (timeCount > 660) {
			endVisible = true;
		}

		if (timeCount > 0 && timeCount<30) {
			signY -= 3;
		}
		timeCount++;
	}

	////////////////////////////////////////////////////////////////////////////
	public void start() {
		if (relaxer == null) {
			relaxer = new Thread(this);
			relaxer.start();
		}
	}
	////////////////////////////////////////////////////////////////////////////
	public void stop() {
		if (relaxer.isAlive()) {
			relaxer.stop();
		}
		relaxer = null;
	}
	/////////////////////////////////////////////////////////////////////////
	public int getX(int x) {
		return (x + XBORDER);
	}

	public int getY(int y) {
		return (y + YBORDER + YTITLE);
	}

	public int getYNormal(int y) {
		return (-y + YBORDER + YTITLE + getHeight2());
	}

	public int getWidth2() {
		return (xsize - getX(0) - XBORDER);
	}

	public int getHeight2() {
		return (ysize - getY(0) - YBORDER);
	}
}