import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.lang.*;


/** Class for buffered reading int and double values */
class Reader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;

    /** call this method to initialize reader for InputStream */
    static void init(InputStream input) {
        reader = new BufferedReader(
                     new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
    }

    /** get next word */
    static String next() throws IOException {
        while ( ! tokenizer.hasMoreTokens() ) {
            //TODO add check for eof if necessary
            tokenizer = new StringTokenizer(
                   reader.readLine() );
        }
        return tokenizer.nextToken();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
    
    static double nextDouble() throws IOException {
        return Double.parseDouble( next() );
    }

    static long nextLong() throws IOException {
        return Long.parseLong( next() );
    }
}

// Direct Mapping
class CO1
{
	public static void main(String[] args) throws IOException
	{
		Reader.init(System.in);
		System.out.print("Input number of cache lines : ");
		int cl = Reader.nextInt();    // power of 2
		System.out.print("Input block size : ");
		int bsize = Reader.nextInt(); // power of 2

		int word_offset = (int)(Math.log(bsize)/Math.log(2));

		String[] check = new String[cl];

		int[][] arr = new int[cl][bsize];

		//LinkedList<String> que = new LinkedList<String>();

		int command;

		do
		{
			System.out.print("Enter command : ");
			command = Reader.nextInt();

			switch(command)
			{
				case 1:
				{
					//write data
					String inp = Reader.next();
					String sub = inp.substring(0,inp.length()-word_offset);
					String last = inp.substring(inp.length()-word_offset,inp.length());
					int off=0;

					int index = Integer.parseInt(sub,2)%cl;

					if(last.length()>0)
					{
						off = Integer.parseInt(last,2);
					}
					
					int data = Reader.nextInt();

					check[index] = sub;
					arr[index][off] = data;

					break;
				}

				case 2:
				{
					//read data
					String input = Reader.next();
					String first = input.substring(0,input.length()-word_offset);
					String end = input.substring(input.length()-word_offset,input.length());
					int dec=0;

					int ind = Integer.parseInt(first,2)%cl;

					if(end.length()>0)
					{
						dec = Integer.parseInt(end,2);
					}


					if(check[ind]!=null && check[ind].equals(first))
					{
						System.out.println(arr[ind][dec]);
					}
					else
					{
						System.out.println("cache miss");
					}

					break;
				}

				case 3:
				{
					//output
					System.out.println("Output");
					
					for(int r=0;r<cl;r++)
					{
						if(check[r]!=null)
						{
							System.out.print(check[r]);
							System.out.print("		");
							for(int g=0;g<bsize;g++)
							{
								System.out.print(arr[r][g]);
								System.out.print("	");
							}
							System.out.println();
						}	
					}

					break;
				}
				default:
				{break;}
			}
		}
		while(command<4);
	}
}