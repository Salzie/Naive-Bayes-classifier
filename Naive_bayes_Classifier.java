import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.math.*;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
public class  Naive_bayes_Classifier extends JFrame implements ActionListener {
 
//UI Elements	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel Input,Output;
 	JButton Button;
 	JTextField In,Out;
 
 	static double anger=0;
 	static double joy=0;
 	static double love=0;
 	static double sad=0;
 	static double surprise=0;
//HashMap for Storing DataSet	
	Map<String,Integer> Anger=new HashMap<String,Integer>();
	Map<String,Integer> Joy=new HashMap<String,Integer>();
	Map<String,Integer> Love=new HashMap<String,Integer>();
	Map<String,Integer> Sad=new HashMap<String,Integer>();
	Map<String,Integer> Suprise=new HashMap<String,Integer>();


 	Naive_bayes_Classifier ()
 	{
 		Input=new JLabel("INPUT TEXT");
  
  		Output=new JLabel("Sentiment Predicted");
  		Button=new JButton("Predict");
 
  		In=new JTextField(60);
  		Out=new JTextField(10);
		
		String [] Emotions={"Anger","Joy","Love","Sad","Suprise"};
		try{
		//Loading Dataset
		for(int i=0;i<Arrays.asList(Emotions).size();i++)
		{

		        BufferedReader br = new BufferedReader(new FileReader("/home/robin/Sentiment-Analysis-master/Dataset/"+Emotions[i]));//Path to Dataset
			String feature;

			while ((feature= br.readLine()) != null) {

				String val[]=feature.split(",");
				if(i==0)
					Anger.put(val[0].replaceAll(" +", " "),Integer.parseInt(val[1]));	
				if(i==1)
					Joy.put(val[0].replaceAll(" +", " "),Integer.parseInt(val[1]));	
				if(i==2)
					Love.put(val[0].replaceAll(" +", " "),Integer.parseInt(val[1]));	
				if(i==3)
					Sad.put(val[0].replaceAll(" +", " "),Integer.parseInt(val[1]));	
				if(i==4)
					Suprise.put(val[0].replaceAll(" +", " "),Integer.parseInt(val[1]));	
			}
		}


		//Add all the elements
		add(Input);
  		add(In);
  		add(Output);
  		add(Out);
  		add(Button);
  
 		Button.addActionListener(this);
  		setSize(700,200);
  		setLayout(new FlowLayout());
  		setTitle("Sentiment Predictor");}
		catch(Exception e)
		{
			e.printStackTrace();
		}
 	}

        //OnClick Function for the button
 	public void actionPerformed(ActionEvent ae)
 	{
  		String input;
		String [] Emotions={"Anger","Joy","Love","Sad","Suprise"};

  		if(ae.getSource()==Button)
  		{
  			input=In.getText().toString();

                        //Formatting the testing data for classification
			input =input.replaceAll("@\\w+", "at_user");//replace @username to at_user
			input=input.replaceAll("[ ]\\W+"," ");//Replace all non alpha-numeric Characters
			input=input.replaceAll(" +", " ");//Remove extra whitespaces
			input=input.replaceAll("#", "");//Remove hashtags
			input=input.replaceAll("https://.*","");//Remove urls
			input=input.replaceAll("[^\\w||^\\s]"," ");//Remove all puncuations  marks

			//Declaring Parameters need for Classification			
			String words[]=input.split(" ");
			int words_no=Arrays.asList(words).size();
			double[][] probability =new double[words_no][10];
			double[] inter_probs=new double[10];
		 	BigDecimal[] final_probs=new BigDecimal[10];


			for(int i=0;i<words_no;i++)
				for(int j=0;j<5;j++)
				{
					probability[i][j]=0.333;
                               		final_probs[j]=new BigDecimal(1.0);

			        }

			int curr=0,present=0;
			double probs_sum=0.0;

                        //Naives Bayes Classifier			
			for(String word:words)
			{
				if(Anger.containsKey(word))
				{
					double times=Anger.get(word);
					double total=Anger.size();
					inter_probs[0]=times/total;
					anger=inter_probs[0];
					System.out.println("anger"+anger);	
				}
				else
				{
					inter_probs[0]=0;
					//anger=(int) inter_probs[0];
				}
			
				if(Joy.containsKey(word))
				{
					double times=Joy.get(word);
					double total=Joy.size();
					inter_probs[1]=times/total;
					joy=inter_probs[1];
					System.out.println("joy"+joy);
				}
				else
				{
					inter_probs[1]=0;
					//joy=(int) inter_probs[1];
				}

			
				if(Love.containsKey(word))
				{
					double times=Love.get(word);
					double total=Love.size();
					inter_probs[2]=times/total;
					love=inter_probs[2];
					System.out.println("love"+love);
				}
				else
				{
					inter_probs[2]=0;
					//love=(int)inter_probs[2];
				}
				
				if(Suprise.containsKey(word))
				{
					double times=Suprise.get(word);
					double total=Suprise.size();
					inter_probs[4]=times/total;
					surprise=inter_probs[4];
					System.out.println("surprise"+surprise);
				}
				else
				{
					inter_probs[4]=0;
					//surprise=(int)inter_probs[4];
				}
				if(Sad.containsKey(word))
				{ 
					double times=Sad.get(word);
					double total=Sad.size();
					inter_probs[3]=times/total;
					sad= inter_probs[3];
					System.out.println("sad"+sad);
				}
				else
				{
					inter_probs[3]=0;
					//sad=(int) inter_probs[3];
				}
			
				for(int i=0;i<5;i++)
				{
					if(inter_probs[i]>0)
					{
						probability[curr][i]=inter_probs[i];
						probs_sum+=inter_probs[i];
						present++;
                                 
					}
				}
				double rem_sum=1-probs_sum;
			
				if(present>0)
				{  
					for(int j=0;j<5;j++)
					{
						if(probability[curr][j]==0.333)
							probability[curr][j]=0.000001*rem_sum;
						
						System.out.println(word+","+Emotions[j]+": "+probability[curr][j]);			
					} 
  				}	
 

				curr++;
				probs_sum=0.0;
				present=0;
			}


			int emo=-1;
			BigDecimal large=new BigDecimal(-34.56);
			MathContext mc=new MathContext(20);

			for(int j=0;j<5;j++)
				for(int i=0;i<words_no;i++)
					final_probs[j]=final_probs[j].multiply(new BigDecimal(probability[i][j]),mc);
			for(int i=0;i<5;i++)
			{  
				System.out.println(final_probs[i]);	
				if(large.compareTo(final_probs[i])==-1)
				{
					large=final_probs[i];
					emo=i;
				}				
			}

  			Out.setText(String.valueOf(Emotions[emo]));
  		}
  		PieChart demo = new PieChart( "Mood Analysis" );  
	      demo.setSize( 560 , 367 );    
	      RefineryUtilities.centerFrameOnScreen( demo );    
	      demo.setVisible( true ); 
  
	}
 	public static class PieChart extends ApplicationFrame 
 	{
 		 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public PieChart( String title ) {
 		      super( title ); 
 		      setContentPane(createDemoPanel( ));
 		   }
 		   
 		   private static PieDataset createDataset( ) {
 		      DefaultPieDataset dataset = new DefaultPieDataset( );
 		      dataset.setValue( "Anger" , anger );  
 		      dataset.setValue( "Joy" , joy );   
 		      dataset.setValue( "Love" , love );    
 		      dataset.setValue( "Sad" , sad );
 		      dataset.setValue( "Surprise" , surprise );
 		      return dataset;         
 		   }
 		   
 		   private static JFreeChart createChart( PieDataset dataset ) {
 		      JFreeChart chart = ChartFactory.createPieChart(      
 		         "Mood Analysis",   // chart title 
 		         dataset,          // data    
 		         true,             // include legend   
 		         true, 
 		         false);

 		      return chart;
 		   }
 		   
 		   public static JPanel createDemoPanel( ) {
 		      JFreeChart chart = createChart(createDataset( ) );  
 		      return new ChartPanel( chart ); 
 		   }

 		   public static void main( String[ ] args ) {
 		      PieChart demo = new PieChart( "Mood Analysis" );  
 		      demo.setSize( 560 , 367 );    
 		      RefineryUtilities.centerFrameOnScreen( demo );    
 		      demo.setVisible( true ); 
 		   }
 	}

 	public static void main(String args[])
 	{
  		Naive_bayes_Classifier classifier=new Naive_bayes_Classifier();
  		classifier.setVisible(true);
  		classifier.setLocation(700,200);
  		 
  		
  	}}

