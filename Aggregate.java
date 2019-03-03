import java.io.*;
import java.util.Scanner;
import java.util.LinkedList;
import java.io.File;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

public class Aggregate{

	public static void showUsage(){
		System.err.printf("Usage: java Aggregate <function> <aggregation column> <csv file> <group column 1> <group column 2> ...\n");
		System.err.printf("Where <function> is one of \"count\", \"sum\", \"avg\"\n");	
	}

	public static void main(String[] args){
		
		//At least four arguments are needed
		if (args.length < 4){
			showUsage();
			return;
		}
		String agg_function = args[0];
		String agg_column = args[1];
		String csv_filename = args[2];
		String[] group_columns = new String[args.length - 3];
		for(int i = 3; i < args.length; i++)
			group_columns[i-3] = args[i];
		
		if (!agg_function.equals("count") && !agg_function.equals("sum") && !agg_function.equals("avg")){
			showUsage();
			return;
		}
		
		BufferedReader br = null;
		
		try{
			br = new BufferedReader(new FileReader(csv_filename));
		}catch( IOException e ){
			System.err.printf("Error: Unable to open file %s\n",csv_filename);
			return;
		}
		
		String header_line;
		try{
			header_line = br.readLine(); //The readLine method returns either the next line of the file or null (if the end of the file has been reached)
		} catch (IOException e){
			System.err.printf("Error reading file\n", csv_filename);
			return;
		}
		if (header_line == null){
			System.err.printf("Error: CSV file %s has no header row\n", csv_filename);
			return;
		}
		
		//Split the header_line string into an array of string values using a comma
		//as the separator.

		String[] column_names = header_line.split(",");
		int [] testlist = new int[column_names.length];
		int k = 0;
		for(int y = 0;y < group_columns.length; y++){
			for(int i = 0; i < column_names.length;i++){
				if(group_columns[y].equals(column_names[i])){
					testlist[k++] = i+1;
				}
			}
		}	
		int [] officalist = new int[group_columns.length];
		for(int i = 0; i < group_columns.length;i++){
			officalist[i] = testlist[i];
		}
 
		// check this with args.column_names

		
		//As a diagnostic, print out all of the argument data and the column names from the CSV file
		//(for testing only: delete this from your final version)
		LinkedList<String> input = new LinkedList<String>();
		LinkedList<String> output = new LinkedList<String>();
		while(header_line != null){
			try{
				String[] split = header_line.split(",");
				output.push(header_line);
				header_line = br.readLine(); 

			} catch (IOException e){
				System.err.printf("Error reading file\n", csv_filename);
				return;
			}
		}

		LinkedList<String> transform = new LinkedList<String>();
		while(output.peek()!= null){
			input.push(output.pop());
		}
		input.pop();
		while(input.peek()!= null){
			String[] split = input.pop().split(",");
			String sum = "";
			for(int i = 0;i < officalist.length;i++){
				String temp = split[officalist[i] - 1];
				sum = sum + temp + ",";	
			}
			sum = sum + split[split.length-1];
			output.push(sum);
		}
		Collections.sort(output);

		if(args[0].equals("count")){

			String keep = "";
			int counter = 1;
			while(output.peek()!= null){
				String sum = "";
				String[] split = output.pop().split(",");
				for(int i = 0; i < split.length-1;i++){
					sum = sum +split[i] + ",";
				}

				if(keep.equals(sum)&&keep != ""){
					counter ++;
				}else if(!keep.equals(sum) && keep != ""){
					transform.push(keep + Integer.toString(counter));
					counter = 1;
				}
				keep = sum;
			} 
			transform.push(keep + Integer.toString(counter));
	

		}else if(args[0].equals("avg")){
			Double average = 0.00;
			String keep = "";
			Double counter = 0.00;
			int count = 1;
			while(output.peek()!= null){
				String sum = "";
				String[] split = output.pop().split(",");
				for(int i = 0; i < split.length-1;i++){
					sum = sum  +split[i] + ",";
				}


				if(keep.equals(sum)&& keep!= ""){
					count++;
					counter = counter + Double.parseDouble((split[split.length-1]));
				}else if(!keep.equals(sum) && keep != ""){
					average = counter/count;
					transform.push(keep + Double.toString(average));
					counter = Double.parseDouble((split[split.length-1]));
					count = 1;
				}else if(!keep.equals(sum) && keep == ""){
					counter = Double.parseDouble((split[split.length-1]));
				}
				keep = sum;

			} 
			average = counter/count;
			transform.push(keep + Double.toString(average));
		
		}else{

			String keep = "";
			Double counter = 0.00;
			int first;
			while(output.peek()!= null){
				String sum = "";
				String[] split = output.pop().split(",");
				for(int i = 0; i < split.length-1;i++){
					sum = sum +split[i] + ",";
				}


				if(keep.equals(sum)&& keep!= ""){
					counter = counter + Double.parseDouble((split[split.length-1]));
				}else if(!keep.equals(sum) && keep != ""){
					transform.push(keep + Double.toString(counter));
					counter = Double.parseDouble((split[split.length-1]));
				}else if(!keep.equals(sum) && keep == ""){
					counter = counter + Double.parseDouble((split[split.length-1]));
				}
				keep = sum;

			} 
			transform.push(keep + Double.toString(counter));
		}

		for(int i = 0; i < group_columns.length;i++){
		System.out.print(group_columns[i] + ",");
		}
		System.out.println(args[1]);
		while(transform.peek()!= null){
		System.out.println(transform.pop());
		}

	}
}