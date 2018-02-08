import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.*;
import java.util.*;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.imageio.*;
import java.net.*;
import java.io.*;
//import javax.imageio;
import java.applet.*;
import javax.swing.*;

public class Main {


	public static void main(String[] args) {



		JFrame win=new JFrame(); // Crée la fenetre principale
		JPanel jp=(JPanel) win.getContentPane(); // Récupère le conteneur de la fenêtre


		final AffGrille jp2=new AffGrille(); // Crée une instance de la classe privée AffGrille

		Grid grid = new Grid();
    Hex hex = new Hex(0,0,0);
    grid.Lignes[0][0] = hex;
		for(int l=0;l<23;l=l+1){// Remarquer le "+2" car la grille est constituée de 2 sous grilles (les lignes impaires sont décallées)
			for(int c=0;c<23;c++){
				hex = new Hex(c, -c, l);
				grid.Lignes[l][c]= hex;
			}
		}



		grid.Lignes[12][12].ennemi();
		grid.Lignes[13][12].ennemi();
		grid.Lignes[12][13].ennemi();
		grid.Lignes[9][15].ennemi();
		grid.Lignes[11][15].ennemi();
		grid.Lignes[1][5].ennemi();

		jp2.grid = grid;

		JOptionPane.showMessageDialog(null,"Bienvenue dans OutPost Gamma!!");

		jp2.addMouseMotionListener(new MouseMotionListener(){
			Polygon p;
      Grid g;
			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseMoved(MouseEvent arg0) {

				p=jp2.pol;
        g=grid;
				if(!p.contains(arg0.getPoint())){
					jp2.repaint();
				}

			}


		});// Repeind jp2 lorsque la souris se déplace
		jp2.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				super.mouseClicked(arg0);
				if (jp2.mode==1 && jp2.selected.x != -1 ) {
					if (grid.Lignes[jp2.x][jp2.y].unite==false) {

						if ( jp2.selected.deplacement >= Math.abs((
						(Math.abs(jp2.selected.x-grid.Lignes[jp2.x][jp2.y].x))
						+ (Math.abs(jp2.selected.y-grid.Lignes[jp2.x][jp2.y].y))
						+ (Math.abs(jp2.selected.z-grid.Lignes[jp2.x][jp2.y].z))
						 + (Math.abs(jp2.selected.deep-grid.Lignes[jp2.x][jp2.y].deep)) ))/2) {
							 int cout = Math.abs((
	 						(Math.abs(jp2.selected.x-grid.Lignes[jp2.x][jp2.y].x))
	 						+ (Math.abs(jp2.selected.y-grid.Lignes[jp2.x][jp2.y].y))
	 						+ (Math.abs(jp2.selected.z-grid.Lignes[jp2.x][jp2.y].z))
	 						 + (Math.abs(jp2.selected.deep-grid.Lignes[jp2.x][jp2.y].deep)) ))/2;
							Object[] optionsDep = {"Oui",
	                    "Non"};
											int m = JOptionPane.showOptionDialog(null,"Ce deplacement va vous couter " +cout+ " points de mouvements, souhaitez vous l'effectuer? ","Deplacement Unite",
	    JOptionPane.YES_NO_CANCEL_OPTION,
	    JOptionPane.QUESTION_MESSAGE,
	    null,
	    optionsDep,
	    optionsDep[1]);
			if (m==0) {

				grid.Lignes[jp2.x][jp2.y].unite=jp2.selected.unite;
				grid.Lignes[jp2.x][jp2.y].hp=jp2.selected.hp;
				grid.Lignes[jp2.x][jp2.y].range=jp2.selected.range;
				grid.Lignes[jp2.x][jp2.y].capitaine=jp2.selected.capitaine;
				grid.Lignes[jp2.x][jp2.y].degatsmax=jp2.selected.degatsmax;
				grid.Lignes[jp2.x][jp2.y].legionnaire=jp2.selected.legionnaire;
				grid.Lignes[jp2.x][jp2.y].ennemi=jp2.selected.ennemi;
				grid.Lignes[jp2.x][jp2.y].deplacement=jp2.selected.deplacement-cout;


				jp2.selected.reset();

				jp2.selected = new Hex(-1,-1,-1);
				jp2.mode=0;
				jp2.repaint();
			}else{
				JOptionPane.showMessageDialog(null,"Deplacement annule");
 			 jp2.selected = new Hex(-1,-1,-1);
			 jp2.mode=0;
			}
		}else{
			JOptionPane.showMessageDialog(null,"Deplacement impossible, le cout en points dedéplacement est trop elevé");
			jp2.selected = new Hex(-1,-1,-1);
			jp2.mode=0;
		}
		}else{
			 JOptionPane.showMessageDialog(null,"Vous ne pouvez pas effectuer ce deplacement car cette case est occupee par un autre unite");
			 jp2.selected = new Hex(-1,-1,-1);
			 jp2.mode=0;
		}

	}else if (jp2.mode==2 && jp2.selected.x != -1 ) {
					if (grid.Lignes[jp2.x][jp2.y].unite==true) {



						if ( jp2.selected.range >= Math.abs((
						(Math.abs(jp2.selected.x-grid.Lignes[jp2.x][jp2.y].x))
						+ (Math.abs(jp2.selected.y-grid.Lignes[jp2.x][jp2.y].y))
						+ (Math.abs(jp2.selected.z-grid.Lignes[jp2.x][jp2.y].z))
						 + (Math.abs(jp2.selected.deep-grid.Lignes[jp2.x][jp2.y].deep)) ))/2) {
							 int cout = Math.abs((
	 						(Math.abs(jp2.selected.x-grid.Lignes[jp2.x][jp2.y].x))
	 						+ (Math.abs(jp2.selected.y-grid.Lignes[jp2.x][jp2.y].y))
	 						+ (Math.abs(jp2.selected.z-grid.Lignes[jp2.x][jp2.y].z))
	 						 + (Math.abs(jp2.selected.deep-grid.Lignes[jp2.x][jp2.y].deep)) ))/2;
							Object[] optionsDep = {"Oui",
	                    "Non"};
											int m = JOptionPane.showOptionDialog(null,"La cible est a portee de tir, souhaitez vous l'attaquer? ","Attaque Unite",
	    JOptionPane.YES_NO_CANCEL_OPTION,
	    JOptionPane.QUESTION_MESSAGE,
	    null,
	    optionsDep,
	    optionsDep[1]);
			if (m==0) {


				Random rand = new Random();
				 int degats = rand.nextInt(jp2.selected.degatsmax - 0 + 1) + 0;

				 if ((grid.Lignes[jp2.x][jp2.y].hp -degats) <= 0) {
					 JOptionPane.showMessageDialog(null,"Le coup a ete fatal a la cible");
				 		grid.Lignes[jp2.x][jp2.y].reset();
				 }else{
					 JOptionPane.showMessageDialog(null,"L'attaque a infligee"+ degats+" degats a la cible mais elle a survecue...");
					 grid.Lignes[jp2.x][jp2.y].hp=grid.Lignes[jp2.x][jp2.y].hp-degats;
				 }





				jp2.selected = new Hex(-1,-1,-1);
				jp2.mode=0;
				jp2.repaint();
			}else{
				JOptionPane.showMessageDialog(null,"Attaque annule");
 			 jp2.selected = new Hex(-1,-1,-1);
			 jp2.mode=0;
			}
		}else{
			JOptionPane.showMessageDialog(null,"Attaque impossible,La cible est trop loin");
			jp2.selected = new Hex(-1,-1,-1);
			jp2.mode=0;
		}
		}else{
			 JOptionPane.showMessageDialog(null,"Vous ne pouvez pas effectuer cette attaque car cette case n'est occupee par aucune unite");
			 jp2.selected = new Hex(-1,-1,-1);
			 jp2.mode=0;
		}

				}else
				{


        // Hex[] tab = jp2.grid.GetNeighbours(jp2.x, jp2.y);
				// JOptionPane.showMessageDialog(null,"Hexagone n:"+jp2.numero);
        // JOptionPane.showMessageDialog(null,"Hexagone n:"+jp2.numero +"/ x: "+grid.Lignes[jp2.x][jp2.y].x +" y: "+grid.Lignes[jp2.x][jp2.y].y +" z: "+grid.Lignes[jp2.x][jp2.y].z +" deepl: "+grid.Lignes[jp2.x][jp2.y].deep + " selected" +grid.Lignes[jp2.x][jp2.y].selected);
				if (grid.Lignes[jp2.x][jp2.y].unite == true) {

					Object[] options = {"Deplacement",
                    "Attaque",
                    "Destruction", "Ne rien faire"};
										int n = JOptionPane.showOptionDialog(null,"Voulez vous vous deplacer, attaquer, ou detruire cette unite?","Action Unite ( HP : "+grid.Lignes[jp2.x][jp2.y].hp+" / Portee : " +grid.Lignes[jp2.x][jp2.y].range+" / Deplacement :"+grid.Lignes[jp2.x][jp2.y].deplacement+" / Degats Max : "+grid.Lignes[jp2.x][jp2.y].degatsmax+ " )",
    JOptionPane.YES_NO_CANCEL_OPTION,
    JOptionPane.QUESTION_MESSAGE,
    null,
    options,
    options[3]);

				if (n==0) {
 					JOptionPane.showMessageDialog(null,"Vous avez selectionne le deplacement, veuillez clicquer sur la case vers laquelle vous voulez vous deplacer. ");
					jp2.mode = 1;
					jp2.selected = grid.Lignes[jp2.x][jp2.y];

				}else if(n==1){
					JOptionPane.showMessageDialog(null,"Vous avez selectionne l'attaque, veuillez clicquer sur l'unite que vous souhaitez attaquer. ");
					jp2.mode = 2;
					jp2.selected = grid.Lignes[jp2.x][jp2.y];
				}else if(n==2){
					JOptionPane.showMessageDialog(null,"Vous avez selectionne la destruction,Cette unite va etre supprimee du plateau. ");
					grid.Lignes[jp2.x][jp2.y].reset();

				}



				}else{
					Object[] optionsnc = {"Legionnaire",
                    "Capitaine"};
										int nc = JOptionPane.showOptionDialog(null,"Vous avez selectionee une case vide, souhaitez vous creer un legionnaire ou un capitaine?","Creation Unite",
    JOptionPane.YES_NO_CANCEL_OPTION,
    JOptionPane.QUESTION_MESSAGE,
    null,
    optionsnc,
    optionsnc[1]);
						if (nc==0) {
							grid.Lignes[jp2.x][jp2.y].legionnaire();
						}else{
							grid.Lignes[jp2.x][jp2.y].capitaine();

						}
					jp2.repaint();
				}

			}
			}

		});// Evenement qui survient au clicque
		jp.add(jp2);// Ajoute le composant à la fenêtre
    win.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		// win.setSize(1920, 1080);// Redimensionne la fenetre
		win.setVisible(true);// Affiche la fenetre

		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Permet de quiter l'application à la fermeture de la fenêtre
	}

}

@SuppressWarnings("serial")
class AffGrille extends JPanel{ // Classe personnelle qui crée une grile hexagonale.

	final static int cote=44; // Ceci définit la taille du côté d'un polygone
	int numero=0; // Retien le n° du polygone sur lequel est la souris
  int x = 0;
  int y = 0;


	Polygon pol;
  Grid grid;
	int mode=0;
Hex selected = new Hex(-1,-1,-1);
	@Override
	public void paint(Graphics arg0) {




		Polygon p2=getPolygon(0, 0, cote); // Crée un hexagone
		Rectangle r=p2.getBounds(); // Récupère le plus petit rectangle // aux bord de la fenêtre dans lequel l'hexagone peut s'inscrire
		Point hovered=null;
		arg0.setColor(Color.black);
		super.paint(arg0);
		Graphics2D g2d;




		g2d=(Graphics2D) arg0;
		ImageIcon d= new ImageIcon(getClass().getResource("/assets/Board.png"));
		Image img = d.getImage();
		g2d.drawImage(img,6,34,1300,950,null);
		BasicStroke bs1=new BasicStroke(1);// Permet de fixer l'épaisseur du trait dans la suite
		BasicStroke bs3=new BasicStroke(3);// Idem

		g2d.setStroke(bs1);

    Hex hex = new Hex(0,0,0);


		for(int l=0;l<23;l=l+2){// Remarquer le "+2" car la grille est constituée de 2 sous grilles (les lignes impaires sont décallées)
			for(int c=0;c<23;c++){
				Point p;
				p=getMousePosition();

				// Polygon poly=getPolygon(c*r.width, (int)(l*cote*1.5),cote);

        hex = new Hex(c, -c, l);
        // grid.Lignes[l][c]= hex;

        Polygon hex0 = grid.Lignes[l][c].DrawHexa((int)(l*r.width*0.75),(int)(c*cote), cote);


				if(p!=null && hex0.contains(p)){
					hovered=new Point( (int)(l*r.width*0.75),(int)(c*cote));
					numero=l*10+c;
          x=l;
          y=c;

					pol=hex0;

				}
				if (grid.Lignes[l][c].unite==true) {
					if (grid.Lignes[l][c].legionnaire == true) {
						arg0.setColor(Color.green);
					}else if (grid.Lignes[l][c].capitaine == true) {
						arg0.setColor(Color.blue);

					}else if(grid.Lignes[l][c].ennemi == true){
						arg0.setColor(Color.red);

					}

					g2d.fill(hex0);
					arg0.setColor(Color.black);


				}else{
					g2d.draw(hex0);

				}
			}
		}
		for(int l=1;l<23;l=l+2){
			for(int c=0;c<23;c++){

				Point p;
				p=getMousePosition();
				// Polygon poly=getPolygon(  (int)(l*r.width*0.75),(int)(c*cote+0.5*cote),cote);
        hex = new Hex(c, -c, l);
        // grid.Lignes[l][c]= hex;

        Polygon hex1 = grid.Lignes[l][c].DrawHexa( (int)(l*r.width*0.75),(int)(c*cote+0.5*cote), cote);
				//arg0.setColor(Color.black);
				if(p!=null && hex1.contains(p)){

					hovered=new Point((int)(l*r.width*0.75),(int)(c*cote+0.5*cote));
					numero=l*10+c;
					x=l;
					y=c;
          pol=hex1;
				}
				if (grid.Lignes[l][c].unite==true) {
					if (grid.Lignes[l][c].legionnaire == true) {
						arg0.setColor(Color.green);
					}else if (grid.Lignes[l][c].capitaine == true) {
						arg0.setColor(Color.blue);

					}else if(grid.Lignes[l][c].ennemi == true){
						arg0.setColor(Color.red);

					}
					g2d.fill(hex1);
					arg0.setColor(Color.black);
				}else{


					g2d.draw(hex1);


				}
			}
		}
		if(hovered!=null){
      arg0.setColor(Color.yellow);
      arg0.setColor(Color.red);
			g2d.setStroke(bs3);
			Polygon p=getPolygon(hovered.x, hovered.y,cote);
			g2d.draw(p);
      g2d.setStroke(bs3);


       Hex[] Neighbours = grid.GetNeighbours(hovered.x/56,hovered.y/45);
       // if (Neighbours[5].x>=0) {
       //   JOptionPane.showMessageDialog(null, "x :" + Neighbours[5]);
       //
       // }

       if (Neighbours[0].x>=0) {
         arg0.setColor(Color.orange);

         if (Neighbours[0].z%2==0) {
            Polygon p0 = Neighbours[0].DrawHexa( (int)(Neighbours[0].z*57),(int)(Neighbours[0].x*44),cote);
            g2d.draw(p0);

         }else{
           Polygon p0 = Neighbours[0].DrawHexa(  (int)(Neighbours[0].z*57),(int)(Neighbours[0].x*44+0.5*cote),cote);
           g2d.draw(p0);

         }

       }
        if (Neighbours[1].x>=0) {
          arg0.setColor(Color.orange);
          if (Neighbours[1].z%2==0) {
             Polygon p0 = Neighbours[1].DrawHexa( (int)(Neighbours[0].z*57+57),(int)(Neighbours[0].x*44-22), cote);
             g2d.draw(p0);

          }else{
            Polygon p0 = Neighbours[1].DrawHexa( (int)(Neighbours[0].z*57+57),(int)(Neighbours[0].x*44+0.5*cote-22), cote);
            g2d.draw(p0);

          }

        }
        if (Neighbours[2].x>=0) {
          arg0.setColor(Color.orange);
          if (Neighbours[2].z%2==0) {
             Polygon p0 = Neighbours[2].DrawHexa(  (int)(Neighbours[0].z*57),(int)(Neighbours[0].x*44+44+22),cote);
             g2d.draw(p0);

          }else{
            Polygon p0 = Neighbours[2].DrawHexa(  (int)(Neighbours[0].z*57),(int)(Neighbours[0].x*44+44),cote);
            g2d.draw(p0);

          }

        }
        if (Neighbours[3].x>=0) {
          arg0.setColor(Color.orange);
          if (Neighbours[3].z%2==0) {
             Polygon p0 = Neighbours[3].DrawHexa( (int)(Neighbours[0].z*57+2*57),(int)(Neighbours[0].x*44),cote);
             g2d.draw(p0);

          }else{
            Polygon p0 = Neighbours[3].DrawHexa((int)(Neighbours[0].z*57+2*57),(int)(Neighbours[0].x*44+22), cote);
            g2d.draw(p0);

          }

        }
        if (Neighbours[4].x>=0) {
          arg0.setColor(Color.orange);
          if (Neighbours[4].z%2==0) {
             Polygon p0 = Neighbours[4].DrawHexa(  (int)(Neighbours[0].z*57+2*57),(int)(Neighbours[0].x*44+44),cote);
             g2d.draw(p0);

          }else{
            Polygon p0 = Neighbours[4].DrawHexa(  (int)(Neighbours[0].z*57+2*57),(int)(Neighbours[0].x*44+44+22), cote);
            g2d.draw(p0);

          }

        }
        if (Neighbours[5].x>=0) {
          arg0.setColor(Color.orange);
          if (Neighbours[5].z%2==0) {
             Polygon p0 = Neighbours[5].DrawHexa(  (int)(Neighbours[0].z*57+57),(int)(Neighbours[0].x*44+88),cote);
             g2d.draw(p0);

          }else{
            Polygon p0 = Neighbours[5].DrawHexa(  (int)(Neighbours[0].z*57+57),(int)(Neighbours[0].x*44+66),cote);
            g2d.draw(p0);

          }

        }





		}
	}


	public static Polygon getPolygon(int x,int y,int cote){// Forme le polygone
		int haut=cote/2;
	 int larg=(int)(cote*(Math.sqrt(3)/2));
	 Polygon p=new Polygon();
	 p.addPoint(x,y+haut);
	 p.addPoint(x+larg/2,y);
	 p.addPoint(x+(int)(1.5*larg),y);
	 p.addPoint(x+2*larg,y+haut);
	 p.addPoint(x+(int)(1.5*larg),y+2*haut);
	 p.addPoint(x+larg/2,y+2*haut);

		// p.addPoint(x-larg,y);// /
 		// p.addPoint((x-larg)/2,y+haut); // \
		// p.addPoint((x+larg)/2,y+haut); // \
    //
 		// p.addPoint(x+larg,y);// /
 		// p.addPoint((x+larg)/2,y-haut); // \
 		// p.addPoint((x-larg)/2,y-haut); // \
		return p;
	}

}




class Grid {
  Hex[][] Lignes = new Hex[30][30];

  public Hex[] GetNeighbours(int line, int column){
    Hex[] Neighbours = new Hex[6];
    if (line%2==0) {

      if ((line-1)>=0) {
        Neighbours[0] = this.Lignes[line-1][column];
      }else if( column==0  ){
        Neighbours[0] = new Hex(-1, -1, -1);
      }else{
        Neighbours[0] = new Hex(-1, -1, -1);
      }
      if ((line-1)>=0 && column+1<=29) {
        Neighbours[1] = this.Lignes[line-1][column+1];
      }else{
        Neighbours[1] = new Hex(-1, -1, -1);
      }
      if (column+1<=29) {
        Neighbours[2] = this.Lignes[line][column+1];

      }else{
        Neighbours[2] = new Hex(-1, -1, -1);
      }
      if ((line+1)<=29) {
        Neighbours[3] = this.Lignes[line+1][column];

      }else{
        Neighbours[3] = new Hex(-1, -1, -1);
      }
      if ((line+1)<=29 && column-1>=0) {
        Neighbours[4] = this.Lignes[line+1][column-1];

      }else{
        Neighbours[4] = new Hex(-1, -1, -1);
      }
      if ((column-1)>=0) {
        Neighbours[5] = this.Lignes[line][column-1];

      }else{
        Neighbours[5] = new Hex(-1, -1, -1);
      }
      return Neighbours;
    }else{
      if ((line-1)>=0) {
        Neighbours[0] = this.Lignes[line-1][column];
      }else{
        Neighbours[0] = new Hex(-1, -1, -1);
      }
      if ((line-1)>=0 && column+1<=29) {
        Neighbours[1] = this.Lignes[line-1][column+1];
      }else{
        Neighbours[1] = new Hex(-1, -1, -1);
      }
      if (column+1<=29) {
        Neighbours[2] = this.Lignes[line][column+1];

      }else{
        Neighbours[2] = new Hex(-1, -1, -1);
      }
      if ((line+1)<=29) {
        Neighbours[3] = this.Lignes[line+1][column];

      }else{
        Neighbours[3] = new Hex(-1, -1, -1);
      }
      if ((line+1)<=29 && column-1>=0  ) {
        Neighbours[4] = this.Lignes[line+1][column-1];

      }else{
        Neighbours[4] = new Hex(-1, -1, -1);
      }
      if ((column-1)>=0) {
        Neighbours[5] = this.Lignes[line][column-1];

      }else{
        Neighbours[5] = new Hex(-1, -1, -1);
      }
      return Neighbours;
    }

  }

}


class Hex extends Polygon{
   int x = 0;
   int y = 0;
   int z = 0;
	 boolean unite = false;
   int deep =0;
	 int deplacement = 0;
	int hp = 0;
	int range = 0;
	boolean legionnaire = false;
	boolean capitaine = false;
	boolean ennemi = false;
	int degatsmax = 0;
	public void legionnaire(){
		this.unite = true;
		this.legionnaire = true;
		this.hp = 5;
		this.range= 5;
		this.deplacement = 5;
		this.degatsmax = 5;
}
public void capitaine(){
	this.unite = true;
	this.capitaine = true;
	this.hp = 7;
	this.range= 7;
	this.deplacement = 7;
	this.degatsmax = 7;
}
public void ennemi(){
	this.unite = true;
	this.ennemi = true;
	this.hp = 6;
	this.range= 6;
	this.deplacement = 6;
	this.degatsmax = 6;
}
	public void reset(){
		this.unite = false;
		this.deplacement = 0;
		this.hp= 0;
		this.range = 0;
		this.legionnaire = false;
		this.capitaine = false;
		this.ennemi = false;
		this.degatsmax = 0;
	}
   public Hex(int x_, int y_, int z_){
     this.x = x_;
     this.y = y_;
     this.z = z_;

   }
   public Polygon DrawHexa(int x,int y,int cote){// Forme le polygone
		 int haut=cote/2;
 		int larg=(int)(cote*(Math.sqrt(3)/2));
 		Polygon p=new Polygon();
 		p.addPoint(x,y+haut);// /
 		p.addPoint(x+larg/2,y); // \
 		p.addPoint(x+(int)(1.5*larg),y);// |
 		p.addPoint(x+2*larg,y+haut); // /
 		p.addPoint(x+(int)(1.5*larg),y+2*haut);// |
 		p.addPoint(x+larg/2,y+2*haut);// \
 		return p;
  }
}
