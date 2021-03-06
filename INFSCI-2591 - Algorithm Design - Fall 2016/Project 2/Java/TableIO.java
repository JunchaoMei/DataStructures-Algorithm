import java.io.*;
import jxl.*;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;

class TableIO
{
	public static int[][] matrix(String xlsName) throws Exception
	{
        jxl.Workbook book = null;
        book = Workbook.getWorkbook(new File(xlsName));

        Sheet sheet = book.getSheet(0);
        int rows = sheet.getRows();
        int cols = sheet.getColumns();
        int[][] tab = new int[rows][cols];

        for(int r = 0; r < rows; r++)
		{
            Cell[] rowCells = sheet.getRow(r);

            for(int c = 0; c < cols; c++)
			{
                Cell cell = rowCells[c];
                String cellValue = cell.getContents();
                int value = Integer.parseInt(cellValue);
                tab[r][c] = value;
            }//end for loop c
        }//end for loop r

		return tab;
	}//end matrix

    public static void createTable(String fileName, int[][] graph) throws RowsExceededException, WriteException
	{
        WritableWorkbook book;
        try {
            System.out.println("---start creating table---");
            //create file
            book = Workbook.createWorkbook(new File(fileName));
            
            //create worksheet "Sheet1", index 0  
            WritableSheet sheet = book.createSheet("Sheet1", 0);
/*
            //A1(0,0) C2(1,2)    
            //content: "Hello World"
            Label label = new Label(0,0,"Hello World");
            //add cell [label] to worksheet
            sheet.addCell(label);
*/
			int n = graph.length-1;

            jxl.write.Number[][] Cells = new jxl.write.Number[n+1][n+1];
			for (int i=0; i<=n; i++)
			{
				for (int j=0; j<=n; j++)
				{
					Cells[i][j] = new jxl.write.Number(j,i,graph[i][j]);
					sheet.addCell(Cells[i][j]);
				}//end for loop j
			}//end for loop i
            
            //write data & close
            book.write();
            book.close();
            System.out.println("---end creating table---");
        } catch (IOException e)
		{
            System.out.println(e);
        }//end catch
    }//end createTable

	//test methods
	public static void main(String[] args) throws Exception
	{
		//regular print
		int[][] table = matrix("test-TableIO.xls");

        for(int x = 0; x < table.length; x++)
		{
            for(int y = 0; y < table[x].length; y++)
				System.out.println("table[" + x + "][" + y +"] = " + table[x][y]);
        }//end for loop x

		//test infinity
		int infinity = Integer.MAX_VALUE;
		System.out.println(infinity);

		//create *.xls file
		createTable("test-output.xls", table);
    }//end main
}//end class