/*---------------------------------------------------------------------------*/
/*                     Copyright (C) 2003  Viacheslav Pozdniakov             */
/*                                   TriGraph 2.5                            */
/*            This program draws graphs based on Afunction(Bx+C)+D           */
/*                            quation where function is                      */
/*                    'sin','cos','tg'('tan') or 'ctg'('cotan')              */
/*                Please read COPYRIGHT file in program's directory          */
/*									     */
/*This program is free software; you can redistribute it and/or modify	     */
/*it under the terms of the GNU General Public License as published by       */
/*the Free Software Foundation; either version 2 of the License, or	     */	
/*(at your option) any later version.                                        */  
/*									     */
/*This program is distributed in the hope that it will be useful,            */
/*but WITHOUT ANY WARRANTY; without even the implied warranty of	     */
/*MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the		     */
/*GNU General Public License for more details.				     */
/*									     */
/*You should have received a copy of the GNU General Public License	     */
/*along with this program; if not, write to the Free Software		     */
/*Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA  */
/*									     */
/*It also important to know that only this file and Java classes are         */
/*under the terms of the GNU GPL. Binaries of JRE are distributed under terms*/
/*of license which you can get in Java directory.			     */
/*---------------------------------------------------------------------------*/

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.net.*;
import java.net.*;
import java.io.*;
import javax.swing.text.*;
import javax.swing.event.*;
import java.net.MalformedURLException;
import javax.swing.colorchooser.*; 

public class TriGraph extends JPanel implements MouseListener,
						MouseMotionListener,
						ChangeListener,
						ActionListener,
						HyperlinkListener{
 
    	TriCanvas canvas; 
	JButton drawbut, clearbut, enterbut, colbut;
	JPopupMenu popup;
	JTextField fu;
	JSlider whichx;
	JLabel mastab;
	JMenuItem open,save,all,last,test,exit,hcont,about;
	JTextArea grafiki;
	JTextPane helparea;
	URL helpurl;
	Color col=Color.red;
	JFrame fmain,ftest;
	JMenuItem color,backcolor,axes;
	String first="";
	String second="";
	String third="";
	String fourth="";
	JRadioButton var1,var2,var3,var4;
	JProgressBar time;
	javax.swing.Timer timer;
	int mark=0,loop=0;
	int temp,trueansw,x1,x2,y1,y2;	
	ButtonGroup group;
	Color backcol;
	JLabel ija;
        JMenuBar mbar;


    public void init() {
	
	canvas = new TriCanvas();
	canvas.setBackground(Color.blue.darker().darker().darker().darker());
	canvas.addMouseListener(this);
	canvas.addMouseMotionListener(this);
	

    }

	

	private JMenuBar  createMenu(){
	JMenuBar mbar = new JMenuBar();
	JMenu file = new JMenu("Rinkmena");
	test=new JMenuItem("Prad\u0117ti test\u0105");
	test.addActionListener(this);

	open=new JMenuItem("Atidaryti sesij\u0105");
	
	open.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_O, ActionEvent.CTRL_MASK));

	open.addActionListener(this);

	save=new JMenuItem("I\u0161saugoti sesij\u0105");
	save.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_S, ActionEvent.CTRL_MASK));
	
	save.addActionListener(this);
	file.add(open);
	file.add(save);
	file.addSeparator();
	file.add(test);
	exit =new JMenuItem("I\u0161\u0117jimas");
	exit.addActionListener(this);
	file.addSeparator();
	file.add(exit);

	JMenu rule = new JMenu("Redaguoti");
	all=new JMenuItem("I\u0161trinti visk\u0105");
	all.addActionListener(this);
	rule.add(all);	
	last=new JMenuItem("I\u0161trinti paskutin\u012f");
	last.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_T, ActionEvent.ALT_MASK));
	last.addActionListener(this);
	rule.add(last);	

	JMenu settings = new JMenu("Nuostatos");
	color =new JMenuItem("Grafiko spalva");
	color.addActionListener(this);
	settings.add(color);
	backcolor  =new JMenuItem("Fono spalva");
	backcolor.addActionListener(this);
	settings.add(backcolor);

	axes  =new JMenuItem("A\u0161i\u0173 spalva");
	axes.addActionListener(this);
	settings.add(axes);

	JMenu hel = new JMenu("Pagalba");
	hcont =new JMenuItem("Pagalba");
	
	hcont.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_H, ActionEvent.ALT_MASK));
	
	hcont.addActionListener(this);
	about =new JMenuItem("Apie...");
	about.addActionListener(this);
	hel.add(hcont);
	hel.addSeparator();
	hel.add(about);
	mbar.add(file);
	mbar.add(rule);
	mbar.add(settings);
	mbar.add(Box.createHorizontalGlue());
	mbar.add(hel);
	return mbar;
	}


	private JToolBar createTools(){
	JToolBar tools = new JToolBar();
	drawbut =new JButton("Brai\u017Eyti");
	drawbut.setMnemonic(KeyEvent.VK_B);
	drawbut.addActionListener(this);
	colbut =new JButton("Spalva");
	colbut.setMnemonic(KeyEvent.VK_P);
	colbut.addActionListener(this);
	clearbut =new JButton("I\u0161trinti");
	clearbut.setMnemonic(KeyEvent.VK_I);
	clearbut.addActionListener(this);
	fu =new JTextField("2sin(x+35)+1",15);
	fu.addActionListener(this);
	whichx = new JSlider(1,5,1);
	whichx.addChangeListener(this);
	tools.add(drawbut);
	tools.add(colbut);
	tools.add(clearbut);
	ija = new JLabel(" Funkcija: y=");
	tools.add(ija);
	tools.add(fu);
	mastab = new JLabel(" M 1:1 ");
	tools.add(mastab);
	tools.add(whichx);

	return tools;
	}
	
	private JTextPane createHelp(){
	helparea=new JTextPane();
	helparea.setEditable(false);
	helparea.addHyperlinkListener(this);
	try{
	File f = new File ("Hf/index.html");
	    String s = f.getAbsolutePath();
	    s = "file:"+s;
	helpurl = new URL(s);
	helparea.setPage(helpurl);} catch (MalformedURLException e){}catch (IOException e) {};
	return helparea;
	}

	
	public void createTest(){
	ftest = new JFrame("TriGraph - Testas");
	ftest.addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e) { canvas.testenabled=false;
							canvas.repaint();mark=0;loop=0;
							drawbut.setEnabled(true);
							test.setEnabled(true);}});
	ftest.setIconImage((new ImageIcon(getClass().getResource("Hf/images/icon.gif"))).getImage());
	ftest.setSize(450,100);
	ftest.setResizable(true);
	group = new ButtonGroup();
	var1 = new JRadioButton();
	var2 = new JRadioButton();
	var3 = new JRadioButton();
	var4 = new JRadioButton();
	group.add(var1);
	group.add(var2);
	group.add(var3);
	group.add(var4);
	var1.addActionListener(this);
	var2.addActionListener(this);
	var3.addActionListener(this);
	var4.addActionListener(this);
	enterbut = new JButton("Prad\u0117ti");
	enterbut.addActionListener(this);
	time = new JProgressBar(1,600);
	JToolBar variants = new JToolBar();
	variants.add(var1);
	variants.add(var2);
	variants.add(var3);
	variants.add(var4);
	ftest.getContentPane().add("North",variants);
	ftest.getContentPane().add("Center",time);
	ftest.getContentPane().add("South",enterbut);
	timer = new javax.swing.Timer(1000,this);
	ftest.setVisible(true);
	
		
	
	}	
	
	public void testengine(){
	int a=10;
	int b=10;
	int c=10;
	int d=10;
	int fnum=10;
	int truevar=10;
	String bstr="",fstr="";
	String cstr="";
	String dstr="";
	String astr="";
	
	while(fnum>3){
	fnum=(int)(10*Math.random());}
	if(fnum==0)fstr="sin";
	if(fnum==1)fstr="cos";
	if(fnum==2)fstr="tg";
	if(fnum==3)fstr="ctg";
	

	while(a>3){
	a=1+(int)(10*Math.random());}
	if((fnum==2)|(fnum==3))a=1;
	if((Math.random())>0.5)a=-1*a;
	if(a==-3)astr="-3";
	if(a==-2)astr="-2";
	if(a==-1)astr="-";
	if(a==1)astr="";
	if(a==2)astr="2";
	if(a==3)astr="3";

	
	while(b>3){
	b=1+(int)(10*Math.random());}
	if(b==1)bstr="";
	if(b==2)bstr="2";
	if(b==3)bstr="3";
	

	while(c>1){
	c=(int)(10*Math.random());}
	if((Math.random())>0.5)c=-1*c;	
	if(c==0){c=0;cstr="";};
	if(c==1){c=30;cstr="+30";};
	if(c==-1){c=-30;cstr="-30";};
		

	while(d>3){
	d=(int)(10*Math.random());}
	if((Math.random())>0.5)d=-1*d;
	if(d==0)dstr="";
	if(d==-3)dstr="-3";
	if(d==-2)dstr="-2";
	if(d==-1)dstr="-1";
	if(d==1)dstr="+1";
	if(d==2)dstr="+2";
	if(d==3)dstr="+3";
	

	while(truevar>4){
	truevar=1+(int)(10*Math.random());}
	if (truevar==1)
		{first=astr+fstr+'('+bstr+'x'+cstr+')'+dstr;
		var1.setText(first);

		a--;
		if(a==0)astr="0.5";
		if(a==-4)astr="-4";
		if(a==-3)astr="-3";
		if(a==-2)astr="-2";
		if(a==-1)astr="-";
		if(a==1)astr="";
		if(a==2)astr="2";
		a++;
		
		b--;
		if(b==0)bstr="0.25";
		if(b==1)bstr="0.5";
		if(b==2)bstr="3";
		b++;

		second=astr+fstr+'('+bstr+'x'+cstr+')'+dstr;
		var2.setText(second);

		b--;
		if(b==0)bstr="0.5";
		if(b==1)bstr="3";
		if(b==2)bstr="2";
		b++;

		if(cstr=="+30")cstr="-45";
		if(cstr=="-30")cstr="+45";
		if(cstr=="")cstr="+30";
		
		third=astr+fstr+'('+bstr+'x'+cstr+')'+dstr;
		var3.setText(third);

		if(cstr=="+45")cstr="+30";
		if(cstr=="-45")cstr="-30";
		if(cstr=="")cstr="-30";

		d--;
		if(d==0)dstr="+4";
		if(d==-3)dstr="-3";
		if(d==-2)dstr="-2";
		if(d==-1)dstr="-1";
		if(d==1)dstr="+1";
		if(d==2)dstr="+2";
		if(d==-4)dstr="-4";
		d++;
		fourth=astr+fstr+'('+bstr+'x'+cstr+')'+dstr;
		var4.setText(fourth);
		
	canvas.draw(first,Color.white);

	canvas.testgraph=first;
	trueansw=1;
	
		}
	if (truevar==2)
		{second=astr+fstr+'('+bstr+'x'+cstr+')'+dstr;
		var2.setText(second);

		a++;
		if(a==0)astr="-0.25";
		if(a==-2)astr="-2";
		if(a==-1)astr="-";
		if(a==1)astr="";
		if(a==2)astr="2";
		if(a==3)astr="3";
		if(a==4)astr="4";
		a--;
		b--;
		if(b==0)bstr="0.5";
		if(b==1)bstr="0.25";
		if(b==2)bstr="2";
		b++;
		first=astr+fstr+'('+bstr+'x'+cstr+')'+dstr;
		var1.setText(first);
		
		b--;
		if(b==0)bstr="0.25";
		if(b==1)bstr="0.5";
		if(b==2)bstr="3";
		b++;

		d++;
		if(d==0)dstr="-4";
		if(d==4)dstr="+4";
		if(d==-2)dstr="-2";
		if(d==-1)dstr="-1";
		if(d==1)dstr="+1";
		if(d==2)dstr="+2";
		if(d==3)dstr="+3";
		d--;
		third=astr+fstr+'('+bstr+'x'+cstr+')'+dstr;
		var3.setText(third);


		d--;
		if(d==0)dstr="5";
		if(d==-3)dstr="-3";
		if(d==-2)dstr="-2";
		if(d==-1)dstr="-1";
		if(d==1)dstr="+1";
		if(d==2)dstr="+2";
		if(d==-4)dstr="-4";
		d++;
		if(cstr=="+30")cstr="-30";
		if(cstr=="-30")cstr="+30";
		if(cstr=="")cstr="+30";
		fourth=astr+fstr+'('+bstr+'x'+cstr+')'+dstr;
		var4.setText(fourth);
		
	canvas.draw(second,Color.white);
	canvas.testgraph=second;
	trueansw=2;
	
		}
	if (truevar==3)
		{third=astr+fstr+'('+bstr+'x'+cstr+')'+dstr;
		var3.setText(third);

		a--;
		if(a==0)astr="0.5";
		if(a==-4)astr="-4";
		if(a==-3)astr="-3";
		if(a==-2)astr="-2";
		if(a==-1)astr="-";
		if(a==1)astr="";
		if(a==2)astr="2";
		a++;		


		b--;
		if(b==0)bstr="0.5";
		if(b==1)bstr="0.125";
		if(b==2)bstr="2";
		b++;	
		first=astr+fstr+'('+bstr+'x'+cstr+')'+dstr;
		var1.setText(first);

		if(cstr=="+30")cstr="-30";
		if(cstr=="-30")cstr="+30";
		if(cstr=="")cstr="+30";
		d++;
		if(d==0)dstr="";
		if(d==4)dstr="+4";
		if(d==-2)dstr="-2";
		if(d==-1)dstr="-1";
		if(d==1)dstr="+1";
		if(d==2)dstr="+2";
		if(d==3)dstr="+3";
		d--;
		second=astr+fstr+'('+bstr+'x'+cstr+')'+dstr;		
		var2.setText(second);
		
		b++;
		if(b==0)bstr="0.25";
		if(b==1)bstr="0.5";
		if(b==2)bstr="3";
		b--;
		d--;
		if(d==0)dstr="-5";
		if(d==-3)dstr="-3";
		if(d==-2)dstr="-2";
		if(d==-1)dstr="-1";
		if(d==1)dstr="+1";
		if(d==2)dstr="+2";
		if(d==-4)dstr="-4";
		d++;
		fourth=astr+fstr+'('+bstr+'x'+cstr+')'+dstr;
		var4.setText(fourth);
		
	canvas.draw(third,Color.white);
	canvas.testgraph=third;
	trueansw=3;
	
		}
	if (truevar==4)
		{fourth=astr+fstr+'('+bstr+'x'+cstr+')'+dstr;
		var4.setText(fourth);

		a++;
		if(a==0)astr="-0.5";
		if(a==-2)astr="-2";
		if(a==-1)astr="-";
		if(a==1)astr="";
		if(a==2)astr="2";
		if(a==3)astr="3";
		if(a==4)astr="4";
		a--;
		b--;
		if(b==0)bstr="0.5";
		if(b==1)bstr="0.25";
		if(b==2)bstr="2";
		b++;
		first=astr+fstr+'('+bstr+'x'+cstr+')'+dstr;	
		var1.setText(first);

		a--;
		if(a==0)astr="0.25";
		if(a==-4)astr="-4";
		if(a==-3)astr="-3";
		if(a==-2)astr="-2";
		if(a==-1)astr="-";
		if(a==1)astr="";
		if(a==2)astr="2";
		a++;
		if(cstr=="+30")cstr="-45";
		if(cstr=="-30")cstr="+45";
		if(cstr=="")cstr="-45";		
		second=astr+fstr+'('+bstr+'x'+cstr+')'+dstr;
		var2.setText(second);

		if(cstr=="+30")cstr="-30";
		if(cstr=="-30")cstr="+30";
		if(cstr=="")cstr="+30";

		d--;
		if(d==0)dstr="-5";
		if(d==-3)dstr="-3";
		if(d==-2)dstr="-2";
		if(d==-1)dstr="-1";
		if(d==1)dstr="+1";
		if(d==2)dstr="+2";
		if(d==-4)dstr="-4";
		d++;
		third=astr+fstr+'('+bstr+'x'+cstr+')'+dstr;
		var3.setText(third);
		
		
	canvas.draw(fourth,Color.white);
	canvas.testgraph=fourth;
	trueansw=4;
	
		}
	loop=loop+1;
	}
		
	public static void main(String args[]){
	ImageIcon splashicon = new ImageIcon("Hf/images/Splash.gif");
	JLabel splashLabel = new JLabel(splashicon);
	JWindow splashScreen = new JWindow();
	splashScreen.getContentPane().add(splashLabel);
	splashScreen.pack();
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	splashScreen.setLocation(screenSize.width/2 - splashScreen.getSize().width/2,
	screenSize.height/2 - splashScreen.getSize().height/2);
	splashScreen.show();

	TriGraph trigraph = new TriGraph();
	TriCanvas canvas = new TriCanvas();
	try {
       UIManager.setLookAndFeel(
            "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

	    } catch (Exception e) {System.out.println("OS is not Windows! Wait for bugs in menubar! Sorry :(");
					}
	
	
	
	trigraph.init();
		

	trigraph.createFmain();
	
	splashScreen.setVisible(false);
	splashScreen = null;
	splashLabel = null; 

	}   
	
	
	public void createFmain(){
	
	this.createMenu();	
	mbar = this.createMenu();
	this.createTools();
	JToolBar tools = this.createTools();
	fmain = new JFrame("TriGraph 2.5");
	fmain.addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {System.exit(0);}});

	fmain.getContentPane().setLayout(new BorderLayout()); 


	fmain.setIconImage((new ImageIcon(getClass().getResource("Hf/images/icon.gif"))).getImage());

	JToolBar formenu = new JToolBar();
	formenu.add(mbar);
	fmain.getContentPane().add("North",formenu);
	
	
	fmain.getContentPane().add("South",tools);

	fmain.getContentPane().add("Center",canvas);
	

	fmain.setSize(500, 300);
	
	fmain.setResizable(true);
	fmain.setLocation(0,0);
	fmain.setVisible(true);
	}

	public void getCol(){
	Color temp = col;
	col=JColorChooser.showDialog(this,"Grafiko spalva",Color.blue);
	if (col == null) col=temp;
	};

	public String nospaces(String withspaces){
	String wospaces="";
	for (int i=0;i<withspaces.length();i++)
	{if (withspaces.charAt(i)!=' ') wospaces=wospaces+withspaces.charAt(i);
	}
	return (wospaces);
	}

	public void stateChanged(ChangeEvent ev){
	if(ev.getSource()==whichx){
	
	if((whichx.getValue())>0){
	mastab.setText(" M 1:"+(whichx.getValue()) );
	float koeff=(whichx.getValue());
	canvas.delimit(koeff);
				}
	}	
	}

	public void mouseClicked(MouseEvent e){
	
	canvas.movex=0;
	canvas.movey=0;
	canvas.movedx=0;
	canvas.movedy=0;
	canvas.repaint();}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){
	x1=e.getX();
	y1=e.getY();
	}
	public void mouseReleased(MouseEvent e){
	x2=e.getX();
	y2=e.getY();
	canvas.movex=canvas.movedx+(x2-x1);
	canvas.movey=canvas.movedy+(y2-y1);
	canvas.movedx=canvas.movedx+x2-x1;
	canvas.movedy=canvas.movedy+y2-y1;
	
	canvas.repaint();}

	public void mouseMoved(MouseEvent e){
	
	}
	public void mouseDragged(MouseEvent e){
	}

	public void calldraw(){

	String funk = fu.getText().trim();
	funk=funk.toLowerCase();
	funk=nospaces(funk);
		if(
		((funk.indexOf("sin")>=0)||
		(funk.indexOf("cos")>=0)||
		(funk.indexOf("tg")>=0)||
		(funk.indexOf("ctg")>=0))&&
	   	(funk.indexOf('x')>=0)&&
	   	(funk.indexOf('(')>=0)&&
	   	(funk.indexOf(')')>=0)){
	canvas.puttolist(funk,col);
	canvas.repaint();
	}else {JOptionPane.showMessageDialog(this," Tr\u016Bksta funkcijos argumento arba skliausteli\u0173!\n Funkcijos trafaretas yra \n Afunkcija(Bx+C)+D","Klaida!",JOptionPane.ERROR_MESSAGE);
		fu.setText("2sin(x+35)+1");
		}
	}
	
 	public void actionPerformed(ActionEvent ev) {
	
	

	if(ev.getSource()==save){
	JFileChooser fc = new JFileChooser();
	fc.addChoosableFileFilter(new TriFilter());
	fc.showSaveDialog(this);
	 
	File file = fc.getSelectedFile();

	try{
	DataOutputStream savesession = new DataOutputStream(new FileOutputStream(file));
	
	if((!(canvas.Graphslist.isEmpty())))
	{
	savesession.writeInt(canvas.n);
	for(int i=0;i<canvas.n;i++){
	canvas.recognize((canvas.Graphslist.get(new Integer(i))).toString());
	if (canvas.fuk=="sin")savesession.writeChar('i');
	if (canvas.fuk=="cos")savesession.writeChar('o');
	if (canvas.fuk=="tg")savesession.writeChar('g');
	if (canvas.fuk=="ctg")savesession.writeChar('t');
	savesession.writeFloat(canvas.ak);
	savesession.writeFloat(canvas.bk);
	savesession.writeFloat(canvas.ck);
	savesession.writeFloat(canvas.dk);
	Color col = (Color)canvas.Colorlist.get(new Integer(i));
	savesession.writeInt(col.getRed());
	savesession.writeInt(col.getGreen());
	savesession.writeInt(col.getBlue());
	}
	savesession.flush();
	savesession.close();}
	
	}catch(Exception e){}

	}	

	if(ev.getSource()==open){

	JFileChooser fc = new JFileChooser();
	fc.addChoosableFileFilter(new TriFilter());
	fc.showOpenDialog(this);
	 
	File file = fc.getSelectedFile();

	try{
	DataInputStream opensession = new DataInputStream(new FileInputStream(file));	
	canvas.dellist();
	int temp=opensession.readInt();
	for(int i=0;i<temp;i++){
	String str="",as,bs,cs,ds;
	float a=1,b=1,c,d;
	char f = opensession.readChar();
	if (f=='i')str="sin";
	if (f=='o')str="cos";
	if (f=='g')str="tg";
	if (f=='t')str="ctg";
	a=(opensession.readFloat());
	b=(opensession.readFloat());
	c=(opensession.readFloat());
	d=(opensession.readFloat());
	as=new Float(a).toString();
	if (a==1) as="";
	if (a==-1) as="-";
	bs=new Float(b).toString();
	if (b==1) bs="";
	if (b==-1) bs="-";
	cs=new Float(c).toString();
	if (cs.charAt(0)!='-') cs="+"+cs;
	if (c==0) cs="";
	ds=new Float(d).toString();
	if (ds.charAt(0)!='-') ds="+"+ds;
	if (d==0) ds="";
	str=as+str+"("+bs+"x"+cs+")"+ds;
	int re= opensession.readInt();
	int gr= opensession.readInt();
	int bl= opensession.readInt();
	Color myColor = new Color(re,gr,bl);
	canvas.puttolist(str,myColor);
	}
	
	}catch(Exception e){}
	}

	if(ev.getSource()==drawbut){
	calldraw();
	}
	
	if(ev.getSource()==colbut){
	getCol();
	}	

	if(ev.getSource()==fu){
	if(canvas.testenabled!=true)
	calldraw();
	}

	if(ev.getSource()==all){
	canvas.dellist();}

	if(ev.getSource()==last){
	
	if((!(canvas.Graphslist.isEmpty())))
	{
	canvas.Colorlist.remove(new Integer(canvas.n));
	canvas.Graphslist.remove(new Integer(canvas.n));
	canvas.n--;
	canvas.repaint();

	}
	
	}
	
	if(ev.getSource()==clearbut){
	canvas.dellist();}

	if(ev.getSource()==exit){
	System.exit(0);}

	if(ev.getSource()==color){
	getCol();
	}

	if(ev.getSource()==timer){
	int temp=time.getValue();
	temp++;
	time.setValue(temp);
	}
	


	if(ev.getSource()==hcont){
	this.createHelp();
	JTextPane helparea = this.createHelp();
	JFrame helpf = new JFrame("TriGraph - Pagalba");
	JScrollPane scr = new JScrollPane(helparea);
	helpf.setIconImage((new ImageIcon(getClass().getResource("Hf/images/icon.gif"))).getImage());
	helpf.getContentPane().add(scr,BorderLayout.CENTER);
	helpf.setSize(300,400);
	helpf.setVisible(true);
	helpf.setResizable(true);
	}		

	if(ev.getSource()==axes){
	canvas.axcol=JColorChooser.showDialog(this,"A\u0161i\u0173 spalva",Color.green);
	if (canvas.axcol == null) canvas.axcol=Color.green;
	canvas.repaint();	
	}

	if(ev.getSource()==backcolor){
	Color temp1 = canvas.getBackground();
	Color temp2=JColorChooser.showDialog(this,"Fono spalva",Color.blue.darker().darker().darker().darker());
	canvas.setBackground(temp2);
	if (temp2 == null) canvas.setBackground(temp1);
	canvas.repaint();
	}

	if(ev.getSource()==enterbut){

	if(canvas.testenabled==false){
	timer.start();
	time.setValue(1);
	enterbut.setText("\u012Evesti");
	canvas.testenabled=true;}
	
	if((canvas.testenabled==true)&&(loop<=10)){
	if((temp==trueansw)&&(loop!=0)&&(time.getValue()<600)){mark=mark+1;};
	canvas.repaint();
	testengine();
	
	}

	
	
	if(loop==11){
	timer.stop();
	canvas.testenabled=false;
	canvas.repaint();
	if(mark==0)mark=1;
	JOptionPane.showMessageDialog(this,"J\u016Bs\u0173 pa\u017Eymys: "+mark,"Rezultatas",JOptionPane.PLAIN_MESSAGE);
	loop=0;mark=0;
	enterbut.setText("Dar kart\u0105");
	}
	
	
	}
	
	if(ev.getSource()==test){
	this.createTest();
	drawbut.setEnabled(false);
	test.setEnabled(false);
	canvas.dellist();
	}

	if(ev.getSource()==about){
	ImageIcon icon = new ImageIcon("Hf/images/tg.gif");
	JOptionPane.showMessageDialog(this,"Suk\u016Br\u0117  Via\u010Deslav Pozdniakov. Ateities vid. mokykla 2002-2003 m.m.\n\u0160i programa platinama pagal GNU GPL. Yra apribojim\u0173 i\u0161 Sun Microsystems pus\u0117s.","Apie...",JOptionPane.INFORMATION_MESSAGE,icon);
	}

	if(ev.getSource()==var1){
	temp=1;
	
	}

	if(ev.getSource()==var2){
	temp=2;	
	
	}
	
	if(ev.getSource()==var3){
	temp=3;	
	
	}

	if(ev.getSource()==var4){
	temp=4;	
	
	}	
	}
	
	public void hyperlinkUpdate(HyperlinkEvent e){
	if (e.getEventType()==HyperlinkEvent.EventType.ACTIVATED){try
				{helparea.setPage(e.getURL());}
						catch(IOException ioe){};
							}
							}
}



class TriCanvas extends Canvas {
	
    	Hashtable Graphslist = new Hashtable();
	Hashtable Colorlist = new Hashtable();
	int n=0;
	float k=1;
	Color hcol,axcol=Color.green;
	boolean testenabled=false;
	String fuk="",testgraph;
	int movex,movey,movedx,movedy;
	float ak=1, bk=1, ck=0, dk=0;


	
    public void delimit(float koeff ){
	k=koeff;
	repaint();
	}

    public void paint(Graphics g) {
	Rectangle r = getBounds();
	
	int tempx, tempy;
	int xmax=r.width;
	int ymax=r.height;
	int xmid=r.width/2+movex;
	int ymid=r.height/2+movey;

	tempx=-1000;tempy=ymid;	

	g.setColor(axcol);
	g.drawLine(xmid,0,xmid,ymax);
	g.drawLine(0,ymid,xmax,ymid);
	g.drawLine(xmid,0,xmid-3,7);
	g.drawLine(xmid,0,xmid+3,7);
	g.drawLine(xmax,ymid,xmax-7,ymid-3);
	g.drawLine(xmax,ymid,xmax-7,ymid+3);
	g.drawString("x",xmax-15,ymid+15);
	g.drawString("y",xmid+15,15);
	for(int i=xmid;i<xmax;i=90+i){
				g.drawLine(i,ymid-3,i,ymid+3);
				int koord=i-xmid;
				g.drawString(""+(koord/k)+"°",i+5,ymid+15);
					}
	for(int i=xmid;i>0;i=i-90){
				g.drawLine(i,ymid-3,i,ymid+3);
				int koord=i-xmid;
				g.drawString(""+(koord/k)+"°",i+5,ymid+15);
					}
	for(int i=ymid;i<ymax;i=60+i){
				g.drawLine(xmid-3,i,xmid+3,i);
				int koord=(int)(i-ymid)/60;
				if(koord!=0) g.drawString("-"+koord/k,xmid+15,i+5);
					}
	for(int i=ymid;i>0;i=i-60){
				g.drawLine(xmid-3,i,xmid+3,i);
				int koord=(int)(ymid-i)/60;
				if(koord!=0) g.drawString(""+koord/k,xmid+15,i+5);
					}	

	if(testenabled==true)draw(testgraph,Color.white);	

	if((!(this.Graphslist.isEmpty())))
	{
	for(int i=0;i<this.n;i++){
	hcol=(Color)(this.Colorlist.get(new Integer(i)));
	draw((this.Graphslist.get(new Integer(i))).toString(),hcol);
	g.setColor(hcol);
	g.drawString("y="+(this.Graphslist.get(new Integer(i))).toString(),1,(i+1)*10);
	}}

	}


    public void puttolist(String function,Color gcol){
	this.Colorlist.put(new Integer(this.n),gcol);
	this.Graphslist.put(new Integer(this.n),function);
	this.n++;
	}	

    public void dellist(){
	this.Graphslist.clear();
	this.Colorlist.clear();
	this.n=0;
	repaint();
	}
	
    public void recognize(String function)
	{
	ak=1;bk=1;ck=0;dk=0;
	if((function.indexOf('s')<function.indexOf('i'))&&(function.indexOf('i')<function.indexOf('n')))
	{
	fuk="sin";
	try{ak=new Float(function.substring(0,function.indexOf('s'))).floatValue();}catch(java.lang.NumberFormatException ex){};
	try{if((function.charAt(function.indexOf('s')-1))=='-')ak=-1;}catch(java.lang.StringIndexOutOfBoundsException ex){};
	try{bk=new Float(function.substring(function.indexOf('(')+1,function.indexOf('x'))).floatValue();}catch(java.lang.NumberFormatException ex){};
	try{if((function.charAt(function.indexOf('x')-1))=='-')bk=-1;}catch(java.lang.StringIndexOutOfBoundsException ex){};
	try{ck=new Float(function.substring(function.indexOf('x')+1,function.indexOf(')'))).floatValue();}catch(java.lang.NumberFormatException ex){};
	try{dk=new Float(function.substring(function.indexOf(')')+1,function.length())).floatValue();}catch(java.lang.NumberFormatException ex){};
	};
	if((function.indexOf('c')<function.indexOf('o'))&&(function.indexOf('o')<function.indexOf('s')))
	{
	fuk="cos";
	try{ak=new Float(function.substring(0,function.indexOf('c'))).floatValue();}catch(java.lang.NumberFormatException ex){};
	try{if((function.charAt(function.indexOf('c')-1))=='-')ak=-1;}catch(java.lang.StringIndexOutOfBoundsException ex){};
	try{bk=new Float(function.substring(function.indexOf('(')+1,function.indexOf('x'))).floatValue();}catch(java.lang.NumberFormatException ex){};
	try{if((function.charAt(function.indexOf('x')-1))=='-')bk=-1;}catch(java.lang.StringIndexOutOfBoundsException ex){};
	try{ck=new Float(function.substring(function.indexOf('x')+1,function.indexOf(')'))).floatValue();}catch(java.lang.NumberFormatException ex){};
	try{dk=new Float(function.substring(function.indexOf(')')+1,function.length())).floatValue();}catch(java.lang.NumberFormatException ex){};
	};
	
	if(function.indexOf('t')<function.indexOf('g')){
	if(function.indexOf('c')==-1)
	{
	fuk="tg";
	try{ak=new Float(function.substring(0,function.indexOf('t'))).floatValue();}catch(java.lang.NumberFormatException ex){};
	try{if((function.charAt(function.indexOf('t')-1))=='-')ak=-1;}catch(java.lang.StringIndexOutOfBoundsException ex){};
	try{bk=new Float(function.substring(function.indexOf('(')+1,function.indexOf('x'))).floatValue();}catch(java.lang.NumberFormatException ex){};
	try{if((function.charAt(function.indexOf('x')-1))=='-')bk=-1;}catch(java.lang.StringIndexOutOfBoundsException ex){};
	try{ck=new Float(function.substring(function.indexOf('x')+1,function.indexOf(')'))).floatValue();}catch(java.lang.NumberFormatException ex){};
	try{dk=new Float(function.substring(function.indexOf(')')+1,function.length())).floatValue();}catch(java.lang.NumberFormatException ex){};
	}else
	if(function.indexOf('c')!=-1)
	{
	fuk="ctg";
	try{ak=new Float(function.substring(0,function.indexOf('c'))).floatValue();}catch(java.lang.NumberFormatException ex){};
	try{if((function.charAt(function.indexOf('c')-1))=='-')ak=-1;}catch(java.lang.StringIndexOutOfBoundsException ex){};
	try{bk=new Float(function.substring(function.indexOf('(')+1,function.indexOf('x'))).floatValue();}catch(java.lang.NumberFormatException ex){};
	try{if((function.charAt(function.indexOf('x')-1))=='-')bk=-1;}catch(java.lang.StringIndexOutOfBoundsException ex){};
	try{ck=new Float(function.substring(function.indexOf('x')+1,function.indexOf(')'))).floatValue();}catch(java.lang.NumberFormatException ex){};
	try{dk=new Float(function.substring(function.indexOf(')')+1,function.length())).floatValue();}catch(java.lang.NumberFormatException ex){};
	};};
	}
	

    public void draw (String function, Color gcol) {

	ak=1; bk=1; ck=0; dk=0;
	Rectangle r = getBounds();
	
	
	int tempx, tempy;
	int xmax=r.width;
	int ymax=r.height;
	int xmid=r.width/2+movex;
	int ymid=r.height/2+movey;
	
	tempx=-1000;tempy=ymid;	
	Graphics g = getGraphics();
	
	this.recognize(function);
	
	g.setColor(gcol);
	
	if(fuk.equals("sin")){
	for(int x=-5*xmax;x<=5*xmax;x++)
			{
			g.drawLine(tempx,tempy,x+xmid,(int)Math.round(ymid-(ak*k*60*(Math.sin(Math.PI/180*bk*x/k+Math.PI*ck/180)))-60*dk*k));
				   tempx=x+xmid;tempy=(int)Math.round(ymid-(ak*k*60*(Math.sin(Math.PI/180*bk*x/k+Math.PI*ck/180)))-60*dk*k);
			}}

	if (fuk.equals("cos")){
	for(int x=-5*xmax;x<=5*xmax;x++)
			{
			g.drawLine(tempx,tempy,x+xmid,(int)Math.round(ymid-(ak*k*60*(Math.cos(Math.PI/180*bk*x/k+Math.PI*ck/180)))-60*dk*k));
				   tempx=x+xmid;tempy=(int)Math.round(ymid-(ak*k*60*(Math.cos(Math.PI/180*bk*x/k+Math.PI*ck/180)))-60*dk*k);
			}}

	if (fuk.equals("tg")){
	for(int x=-5*xmax;x<=5*xmax;x++)
			{
			if((int)Math.round(ak*k*60*(Math.cos(Math.PI/180*bk*x/k+Math.PI*ck/180)))!=0){
			if((tempy<0)||(tempy>ymax)){
			tempx=x+xmid;tempy=(int)Math.round(ymid-(ak*k*60*(Math.tan(Math.PI/180*bk*x/k+Math.PI*ck/180)))-60*dk*k);
			continue;}
			g.drawLine(tempx,tempy,x+xmid,(int)Math.round(ymid-(ak*k*60*(Math.tan(Math.PI/180*bk*x/k+Math.PI*ck/180)))-60*dk*k));
				   tempx=x+xmid;tempy=(int)Math.round(ymid-(ak*k*60*(Math.tan(Math.PI/180*bk*x/k+Math.PI*ck/180)))-60*dk*k);
			
			
			}}}

	if (fuk.equals("ctg")){
	for(int x=-5*xmax;x<=5*xmax;x++)
			{
			if((int)Math.round(ak*k*60*(Math.sin(Math.PI/180*bk*x/k+Math.PI*ck/180)))!=0){
			if((tempy<0)||(tempy>ymax)){
			tempx=x+xmid;tempy=(int)Math.round(ymid-(ak*k*60*(1/(Math.tan(Math.PI/180*bk*x/k+Math.PI*ck/180))))-(60*dk*k));
			continue;}
			g.drawLine(tempx,tempy,x+xmid,(int)Math.round(ymid-(ak*k*60*(1/(Math.tan(Math.PI/180*bk*x/k+Math.PI*ck/180))))-(60*dk*k)));
				   tempx=x+xmid;tempy=(int)Math.round(ymid-(ak*k*60*(1/(Math.tan(Math.PI/180*bk*x/k+Math.PI*ck/180))))-(60*dk*k));
			
			}}}
	    }


}