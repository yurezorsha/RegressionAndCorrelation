import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class ShowGraph extends JFrame {
	
	private XY xy;
	private int n;
	
	public ShowGraph(String applicationTitle, XY xy, int n) {
	      super(applicationTitle);
	      this.n=n;
	      this.xy=xy;	   
	      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	      
	   }
	   
	public void create(String applicationTitle, String chartTitle, XY xy)
	{
		
		JFreeChart xylineChart = ChartFactory.createXYLineChart(
		         chartTitle ,
		         "X" ,
		         "Y" ,
		         createDataset(n) ,
		         PlotOrientation.VERTICAL ,
		         true , true , false);
		
		         
		      ChartPanel chartPanel = new ChartPanel( xylineChart );
		      chartPanel.setPreferredSize( new java.awt.Dimension( 700 , 700 ) );
		      XYPlot plot = xylineChart.getXYPlot( );
		      
		      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
		      renderer.setSeriesPaint( 0 , Color.RED );
		      renderer.setSeriesPaint( 1 , Color.BLUE );
		      renderer.setSeriesShape(0, new Ellipse2D.Double(0, -4, 5, 5));
		      renderer.setSeriesShape(1, new Ellipse2D.Double(0, -4, 5, 5));

		      
		      renderer.setSeriesStroke( 0 , new BasicStroke( 2.0f ) );
		      renderer.setSeriesStroke( 1 , new BasicStroke( 2.0f ) );
		     
		      renderer.setSeriesLinesVisible(0, false);
		      plot.setRenderer( renderer ); 
		      plot.setDataset(createDataset(n));
		      setContentPane( chartPanel ); 
	}
	
	   private XYDataset createDataset(int n) {
		 XYSeries basic = new XYSeries( "base" ); 
		 
		      for (int i = 0; i < xy.getX().size(); i++) {
				  basic.add(xy.getX().get(i), xy.getY().get(i));
	     }
		      
		      String title="";
	switch (n) {
	
		case 0:			  
			  title ="y="+Math.ceil(xy.getA()*1000)/1000+"+"+Math.ceil(xy.getB()*1000)/1000+"*x";		
		      break;
		case 1:		 
			  title= "y="+Math.ceil(xy.getA0()*1000)/1000+"*exp("+Math.ceil(xy.getA1()*1000)/1000+"*x)"; 			  
			  break;
		case 2:			  
			  title= "y="+Math.ceil(xy.getPl_a()*1000)/1000+"*ln(x)+"+Math.ceil(xy.getPl_b()*1000)/1000;			
			  break;
		default:
			break;
		}
	
	 System.out.println(xy.getX());
	 System.out.println(xy.getY_solve());
	 XYSeries lineXY = new XYSeries(title); 
     for (int i = 0; i < xy.getX().size(); i++) {
		lineXY.add(xy.getX().get(i), xy.getY_solve().get(i));
	  }

	     XYSeriesCollection dataset = new XYSeriesCollection( );    
	     dataset.addSeries(basic);
	     dataset.addSeries(lineXY);  
	    
	      	      return dataset;
	   }

}
