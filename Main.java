import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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


public class Main {


	public static void main(String[] args) {
		JFrame win=new JFrame(); // Crée la fenetre principale
		JPanel jp=(JPanel) win.getContentPane(); // Récupère le conteneur de la fenêtre
    BufferedImage img = null;
    try {
    URL url = new URL("file:/assets/Plateau.png");

    img = ImageIO.read(url);
  } catch (IOException e) {
  }


		final AffGrille jp2=new AffGrille(); // Crée une instance de la classe privée AffGrille

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
        g=jp2.grid;
				if(!p.contains(arg0.getPoint())){
					jp2.repaint();
				}

			}


		});// Repeind jp2 lorsque la souris se déplace
		jp2.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				super.mouseClicked(arg0);
        // Hex[] tab = jp2.grid.GetNeighbours(jp2.x, jp2.y);
				// JOptionPane.showMessageDialog(null,"Hexagone n:"+jp2.numero);
        JOptionPane.showMessageDialog(null,"Hexagone n:"+jp2.numero +"/ x: "+jp2.grid.Lignes[jp2.x][jp2.y].x +" y: "+jp2.grid.Lignes[jp2.x][jp2.y].y +" z: "+jp2.grid.Lignes[jp2.x][jp2.y].z +" deepl: "+jp2.grid.Lignes[jp2.x][jp2.y].deep);
        // JOptionPane.showMessageDialog(null," neighbour 1 x:" + tab[0].x + " neighbour 1 y:" + tab[0].y );
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
	final static int cote=26; // Ceci définit la taille du côté d'un polygone
	int numero=0; // Retien le n° du polygone sur lequel est la souris
  int x = 0;
  int y = 0;


	Polygon pol;
  Grid grid;

	@Override
	public void paint(Graphics arg0) {
    Grid grid = new Grid();
    Hex origine = new Hex(0,0,0);
    grid.Lignes[0][0] = origine;

		Polygon p2=getPolygon(0, 0, cote); // Crée un hexagone
		Rectangle r=p2.getBounds(); // Récupère le plus petit rectangle // aux bord de la fenêtre dans lequel l'hexagone peut s'inscrire
		Point hovered=null;
		arg0.setColor(Color.black);
		super.paint(arg0);
		Graphics2D g2d;



		g2d=(Graphics2D) arg0;
		BasicStroke bs1=new BasicStroke(1);// Permet de fixer l'épaisseur du trait dans la suite
		BasicStroke bs3=new BasicStroke(3);// Idem

		g2d.setStroke(bs1);

    Hex hex = new Hex(0,0,0);


		for(int l=0;l<30;l=l+2){// Remarquer le "+2" car la grille est constituée de 2 sous grilles (les lignes impaires sont décallées)
			for(int c=0;c<30;c++){
				Point p;
				p=getMousePosition();

				// Polygon poly=getPolygon(c*r.width, (int)(l*cote*1.5),cote);

        hex = new Hex(c, -c, l);
        grid.Lignes[l][c]= hex;

        Polygon hex0 = hex.DrawHexa(c*r.width, (int)(l*cote*1.5),cote);


				if(p!=null && hex0.contains(p)){
					hovered=new Point(c*r.width, (int)(l*cote*1.5));
					numero=l*10+c;
          x=l;
          y=c;

					pol=hex0;
          this.grid=grid;
				}
				g2d.draw(hex0);
			}
		}
		for(int l=1;l<30;l=l+2){
			for(int c=0;c<30;c++)
			{
				Point p;
				p=getMousePosition();
				Polygon poly=getPolygon(c*r.width+r.width/2,  (int)(l*cote*1.5+0.5),cote);
        hex = new Hex(c, -c, l);
        grid.Lignes[l][c]= hex;

        Polygon hex1 = hex.DrawHexa(c*r.width+r.width/2,  (int)(l*cote*1.5+0.5),cote);
				//arg0.setColor(Color.black);
				if(p!=null && poly.contains(p)){
					hovered=new Point(c*r.width+r.width/2,  (int)(l*cote*1.5+0.5));
					numero=l*10+c;
          x=l;
          y=c;

          pol=hex1;
          this.grid=grid;
				}
				g2d.draw(hex1);
			}
		}
		if(hovered!=null){
      arg0.setColor(Color.yellow);
      arg0.setColor(Color.red);
			g2d.setStroke(bs3);
			Polygon p=getPolygon(hovered.x, hovered.y,cote);
			g2d.draw(p);
      g2d.setStroke(bs3);


       Hex[] Neighbours = grid.GetNeighbours(hovered.y/39,hovered.x/44  );
       // if (Neighbours[5].x>=0) {
       //   JOptionPane.showMessageDialog(null, "x :" + Neighbours[5]);
       //
       // }

       if (Neighbours[0].x>=0) {
         arg0.setColor(Color.blue);

         if (Neighbours[0].z%2==0) {
            Polygon p0 = Neighbours[0].DrawHexa(Neighbours[0].x*r.width, (int)(Neighbours[0].z*cote*1.5),cote);
            g2d.draw(p0);

         }else{
           Polygon p0 = Neighbours[0].DrawHexa((Neighbours[0].x-1)*r.width+r.width/2, (int)(Neighbours[0].z*cote*1.5+0.5),cote);
           g2d.draw(p0);

         }

       }
        if (Neighbours[1].x>=0) {
          arg0.setColor(Color.green);
          if (Neighbours[1].z%2==0) {
             Polygon p0 = Neighbours[1].DrawHexa(Neighbours[1].x*r.width, (int)(Neighbours[1].z*cote*1.5),cote);
             g2d.draw(p0);

          }else{
            Polygon p0 = Neighbours[1].DrawHexa((Neighbours[1].x-1)*r.width+r.width/2, (int)(Neighbours[1].z*cote*1.5+0.5),cote);
            g2d.draw(p0);

          }

        }
        if (Neighbours[2].x>=0) {
          arg0.setColor(Color.orange);
          if (Neighbours[2].z%2==0) {
             Polygon p0 = Neighbours[2].DrawHexa(Neighbours[2].x*r.width, (int)(Neighbours[2].z*cote*1.5),cote);
             g2d.draw(p0);

          }else{
            Polygon p0 = Neighbours[2].DrawHexa(Neighbours[2].x*r.width+r.width/2, (int)(Neighbours[2].z*cote*1.5+0.5),cote);
            g2d.draw(p0);

          }

        }
        if (Neighbours[3].x>=0) {
          arg0.setColor(Color.pink);
          if (Neighbours[3].z%2==0) {
             Polygon p0 = Neighbours[3].DrawHexa((Neighbours[3].x+1)*r.width, (int)(Neighbours[3].z*cote*1.5),cote);
             g2d.draw(p0);

          }else{
            Polygon p0 = Neighbours[3].DrawHexa(Neighbours[3].x*r.width+r.width/2, (int)(Neighbours[3].z*cote*1.5+0.5),cote);
            g2d.draw(p0);

          }

        }
        if (Neighbours[4].x>=0) {
          arg0.setColor(Color.black);
          if (Neighbours[4].z%2==0) {
             Polygon p0 = Neighbours[4].DrawHexa((Neighbours[4].x+1)*r.width, (int)(Neighbours[4].z*cote*1.5),cote);
             g2d.draw(p0);

          }else{
            Polygon p0 = Neighbours[4].DrawHexa(Neighbours[4].x*r.width+r.width/2, (int)(Neighbours[4].z*cote*1.5+0.5),cote);
            g2d.draw(p0);

          }

        }
        if (Neighbours[5].x>=0) {
          arg0.setColor(Color.gray);
          if (Neighbours[5].z%2==0) {
             Polygon p0 = Neighbours[5].DrawHexa(Neighbours[5].x*r.width, (int)(Neighbours[5].z*cote*1.5),cote);
             g2d.draw(p0);

          }else{
            Polygon p0 = Neighbours[5].DrawHexa(Neighbours[5].x*r.width+r.width/2, (int)(Neighbours[5].z*cote*1.5+0.5),cote);
            g2d.draw(p0);

          }

        }





		}
	}


	public static Polygon getPolygon(int x,int y,int cote){// Forme le polygone
		int haut=cote/2;
		int larg=(int)(cote*(Math.sqrt(3)/2));
		Polygon p=new Polygon();
		p.addPoint(x,y+haut);// /
		p.addPoint(x+larg,y); // \
		p.addPoint(x+2*larg,y+haut);// |
		p.addPoint(x+2*larg,y+(int)(1.5*cote)); // /
		p.addPoint(x+larg,y+2*cote);// \
		p.addPoint(x,y+(int)(1.5*cote));// |
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

      }else if(column==0){
        Neighbours[4] = this.Lignes[line+1][0];
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

   int deep =200;

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
 		p.addPoint(x+larg,y); // \
 		p.addPoint(x+2*larg,y+haut);// |
 		p.addPoint(x+2*larg,y+(int)(1.5*cote)); // /
 		p.addPoint(x+larg,y+2*cote);// \
 		p.addPoint(x,y+(int)(1.5*cote));// |
 		return p;
  }
}
