import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URL;

class board extends JPanel implements ActionListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Image apple;
	private Image red;
	private Image green;
	private int dots;
	private final int D_SIZE = 10;
	private final int x[] = new int[300];
	private final int y[] = new int[300];
	private final int RAN = 29;
	private int ax;
	private int ay;
	private Timer t;
	private boolean L_D = false;
	private boolean R_D = true;
	private boolean U_D = false;
	private boolean D_D = false;
	private boolean ig = true;

	board() throws MalformedURLException {
		addKeyListener(new TAdapter());
		setPreferredSize(new Dimension(300, 300));
		setBackground(Color.BLACK);
		setFocusable(true);
		loadimage();
		ini();
	}

	public void loadimage() throws MalformedURLException {
		ImageIcon il = new ImageIcon(new URL("https://imgur.com/86EywJC.png"));
		apple = il.getImage();
		ImageIcon i2 = new ImageIcon(new URL("https://imgur.com/86EywJC.png"));
		red = i2.getImage();
		ImageIcon i3 = new ImageIcon(new URL("https://imgur.com/86EywJC.png"));
		green = i3.getImage();
	}
	public void ini(){
		dots = 5;
		for(int i=0 ;i<dots;i++){
			x[i]=50-i*D_SIZE;
			y[i]=50;
		}
		app();
		t = new Timer(200,this);
		t.start();
	}
	public void app(){
		int r = (int)(Math.random()*RAN);
		ax = (r*D_SIZE);	
		r = (int)(Math.random()*RAN);	
		ay = (r*D_SIZE);
	}
	public void capp(){
		if((x[0]==ax)&&(y[0]==ay)){
			dots++;
			app();
		}
	}
	public void cc(){
		if(y[0]>=300){
			ig= false;
		}	
		if(x[0]>=300){
			ig= false;
		}
		if(y[0]<0){
			ig= false;
		}	
		if(x[0]<0){
			ig= false;
		}
		for(int i =dots;i>0;i--){
			if((i>4)&&(x[0]==x[i])&&(y[0]==y[i])){
				ig=false;
			}
		}
		if(!ig){
			t.stop();
		}
	}
	public void move(){
		if(L_D){
			x[0]=x[0]-D_SIZE; 
		}
		if(R_D){
			x[0]+=D_SIZE; 
		}
		if(U_D){
			y[0]=y[0]-D_SIZE; 
		}
		if(D_D){
			y[0]+=D_SIZE; 
		}
		for(int i=dots;i>0;i--){
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
	}
	public void actionPerformed(ActionEvent ae){
			if(ig){
				capp();
				cc();
				move();
			}
			repaint();
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g){
		if(ig){
			g.drawImage(apple,ax,ay,this);
			for(int i=0;i<dots;i++){
				if(i==0){
					g.drawImage(red,x[i],y[i],this);
				}
				else{
					g.drawImage(green,x[i],y[i],this);
				}
			}
			Toolkit.getDefaultToolkit().sync();
		}
		else{
			go(g);
		}
	}
	public void go(Graphics g){
		String m="game over";
		Font f=new Font("SAN_SERIF",Font.BOLD,14);
		FontMetrics metrices= getFontMetrics(f);
		g.setColor(Color.WHITE);
		g.setFont(f);
		g.drawString(m,(300-metrices.stringWidth(m))/2,150);		
	}
	private class TAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e){
			int key = e.getKeyCode();
			if((key == 87)&&(!U_D)){
				L_D = false; 
				R_D = false;
				U_D = true;
				D_D = false;
			}		
			if((key == 65)&&(!L_D)){
				L_D = true; 
				R_D = false;
				U_D = false;
				D_D = false;
			}		
			if((key == 68)&&(!R_D)){
				L_D = false; 
				R_D = true;
				U_D = false;
				D_D = false;
			}		
			if((key == 83)&&(!D_D)){
				L_D = false; 
				R_D = false;
				U_D = false;
				D_D = true;
			}		
		}
	}
}
public class Snake extends JFrame{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	Snake() throws MalformedURLException {
		add(new board());
		pack();
		setLocationRelativeTo(null);
		setTitle("Snake");
		setResizable(false);
	}
	public static void main(String args[]) throws MalformedURLException {
		new Snake().setVisible(true);
	}
}