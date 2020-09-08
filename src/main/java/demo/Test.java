package demo;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Test {

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con= DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mims_db","root","sapphire123");
        String sql = " INSERT INTO demo_csv(prc_year,prc_qtr,dept) VALUES(?,?,?) ";
        CSVReader reader = null;
        try
        {
            //parsing a CSV file into CSVReader class constructor
            reader = new CSVReader(new FileReader("C:\\Users\\hc\\Desktop\\Table_3_Full.csv"));
            String [] nextLine;
            int count=0;
            while ((nextLine = reader.readNext()) != null)
            {
                count++;
                for(int i=0; i<nextLine.length; i++)
                {
                    System.out.println("iiiii  "+i+"    "+nextLine.length);
                    if(count>1){
                       // System.out.println(i+"  "+nextLine[i]);
                        PreparedStatement ps = con.prepareStatement(sql);
                        if(nextLine[i].equals("")){
                            ps.setInt(1,0);
                        }
                        else if(!(nextLine[i].trim().equals("-")) || nextLine[i].contains("(") || nextLine[i].contains(")")) {
                            nextLine[i]=nextLine[i].replace("(","");
                            nextLine[i]=nextLine[i].replace(")","");
                            ps.setInt(1, Integer.parseInt(nextLine[i].trim().replace(",", "")));
                        } else{
                            ps.setInt(1,0);
                        }
                        i++;
                        if(nextLine[i].equals("")){
                            ps.setInt(2,0);
                        }
                        else if(!(nextLine[i].trim().equals("-")) || nextLine[i].contains("(") || nextLine[i].contains(")")){
                            nextLine[i]=nextLine[i].replace("(","");
                            nextLine[i]=nextLine[i].replace(")","");
                            ps.setInt(2,Integer.parseInt(nextLine[i].trim().replace(",","")));
                        }else {
                            ps.setInt(2,0);
                        }
                            System.out.println(nextLine[i]);
                            i++;
                            ps.setString(3,nextLine[i]);
                            ps.execute();
                            ps. close();

                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("Successfully Added Data!");
    }
}
