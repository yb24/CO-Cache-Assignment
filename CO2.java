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

// Fully Associative
class CO2
{
	public static void main(String[] args) throws IOException
	{
		Reader.init(System.in);
		System.out.print("Input number of cache lines : ");
		int cl = Reader.nextInt();    // power of 2
		System.out.print("Input block size : ");
		int bsize = Reader.nextInt(); // power of 2

		int word_offset = (int)(Math.log(bsize)/Math.log(2));

		//System.out.println(cl);
		//System.out.println(bsize);
		//System.out.println(word_offset);

		//String test = Reader.next(); // 16 characters
		//String sub = test.substring(0,test.length()-word_offset);

		String[] check = new String[cl];

		int[][] arr = new int[cl][bsize];

		LinkedList<String> que = new LinkedList<String>();

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

					if(last.length()>0)
					{
						off = Integer.parseInt(last,2);
					}
					
					int data = Reader.nextInt();
					boolean alreadyExists = false;

					for(int u=0;u<cl;u++)
					{
						if(check[u]!=null && check[u].equals(sub))
						{
							que.removeFirstOccurrence(check[u]);
							que.add(check[u]);
							arr[u][off] = data;
							alreadyExists = true;
						}
					}

					if(alreadyExists == false)
					{
						if(que.size()<cl)
						{
							for(int a=0;a<cl;a++)
							{
								if(check[a]==null)
								{
									check[a] = sub;
									arr[a][off] = data;
									que.add(check[a]);
									break;
								}
							}
						}
						else
						{
							String temp = que.getFirst();
							for(int y=0;y<cl;y++)
							{
								if(check[y]!=null && check[y].equals(temp))
								{
									arr[y][off] = data;
									check[y] = sub;
									que.remove();
									que.add(check[y]);
								}
							}
						}
					}

					break;
				}

				case 2:
				{
					//read data
					String input = Reader.next();
					String first = input.substring(0,input.length()-word_offset);
					String end = input.substring(input.length()-word_offset,input.length());
					int dec=0;

					if(end.length()>0)
					{
						dec = Integer.parseInt(end,2);
					}

					boolean isFound = false;

					for(int w=0;w<cl;w++)
					{
						if(check[w]!=null && check[w].equals(first))
						{
							isFound = true;
							System.out.println(arr[w][dec]);
						}
					}

					if(isFound == false)
						{System.out.println("cache miss");}
					break;
				}

				case 3:
				{
					//output
					System.out.println("Output");
					for(int t=0;t<que.size();t++)
					{
						String tt=que.get(t);
						System.out.print(tt);
						System.out.print("		");
						for(int z=0;z<bsize;z++)
						{
							System.out.print(arr[t][z]);
							System.out.print("	");
						}
						System.out.println();
					}
					System.out.println();
					break;
				}
				default:
				{break;}
			}
		}
		while(command<4);
	}
}