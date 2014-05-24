package main;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.Series;
import com.xeiam.xchart.StyleManager.ChartType;
import com.xeiam.xchart.XChartPanel;


public class Charter implements Runnable{
	
	private int nColumns = 10;
	
	@Override
	public void run(){

		int nLines = count(IzdaDcha.logFile);
		if (nLines == 0){
			JOptionPane.showMessageDialog(null, "Aún no existen datos para este usuario", "Aviso",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		Collection<Number> right = new ArrayList<Number>();
		Collection<Number> wrong = new ArrayList<Number>();
		Collection<Number> timeout = new ArrayList<Number>();
		Collection<Number> index = new ArrayList<Number>();
		
		FileReader fr;
		BufferedReader br;
		try {
			fr = new FileReader(IzdaDcha.logFile);
			br = new BufferedReader(fr);
	        String line;
	        
	        int i = 0;
	        while ((line = br.readLine()) != null) {
	            if (i > (nLines - nColumns)){
	            	right.add(Double.parseDouble(line.split("-")[0]));
	            	wrong.add(Double.parseDouble(line.split("-")[1]));
	            	timeout.add(Double.parseDouble(line.split("-")[2]));
	            	index.add((double) i);
	            }
	            i++;
	        }
	        br.close();
	        fr.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    Chart chart = new ChartBuilder().chartType(ChartType.Bar).width(800).height(400).
	    		title(IzdaDcha.logFile).xAxisTitle("Intentos").yAxisTitle("Contador").build();
	    chart.addSeries("Timeouts", index, timeout);
	    chart.addSeries("Fallos", index, wrong);
	    chart.addSeries("Aciertos", index, right);
	    
	    chart.getStyleManager().setXAxisMax(10);
	    chart.getStyleManager().setXAxisMin(0);
	    chart.getStyleManager().setYAxisMax(10);
	    chart.getStyleManager().setYAxisMin(0);
	    
	    Iterator<Series> iterator = chart.getSeriesMap().values().iterator();
	    Series serie;
	    Color[] colors = {Color.ORANGE, Color.RED, Color.GREEN};
	    int i = 0;
	    while (iterator.hasNext()){
	    	serie = iterator.next();
	    	serie.setLineColor(colors[i]);
	    	i++;
	    }
	 
	    XChartPanel panel = new XChartPanel(chart);
	    loadChart(panel);
	    
	}
	
	private void loadChart(JPanel panel){
		panel.setSize(800, 500);
		panel.setMaximumSize(panel.getSize());
		panel.setMinimumSize(panel.getSize());
		JFrame frame = new JFrame();
		frame.setSize(panel.getSize());
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}
	
	public int count(String filename) {
	    try {
		    InputStream is = new BufferedInputStream(new FileInputStream(filename));
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        is.close();
	        return (count == 0 && !empty) ? 1 : count;
	    } catch (IOException e) {
			e.printStackTrace();
		}
		return 0; 
	}

}
