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

// n way Set Associative
class CO3
{
	public static void main(String[] args) throws IOException
	{
		Reader.init(System.in);
		System.out.print("Input number of cache lines : ");
		int cl = Reader.nextInt();    // power of 2
		System.out.print("Input block size : ");
		int bsize = Reader.nextInt(); // power of 2
		System.out.print("Input n : ");
		int n = Reader.nextInt();     // power of 2

		int word_offset = (int)(Math.log(bsize)/Math.log(2));

		int limit = cl/n;

		int number_of_groups_binary = (int)(Math.log(limit)/Math.log(2));


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

					int group = Integer.parseInt(sub,2)%number_of_groups_binary;
					
					int data = Reader.nextInt();
					boolean alreadyExists = false;
					boolean idk = false;

					for(int u=n*group;u<n*group+n;u++)
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
						for(int a=n*group;a<n*group+n;a++)
						{
							if(check[a]==null)
							{
								check[a] = sub;
								arr[a][off] = data;
								que.add(check[a]);
								idk = true;
								break;
							}
						}

						if(idk == true)
						{
							String temp = check[n*group];
							int sdf = que.indexOf(temp);
							arr[n*group][off] = data;
							check[n*group] = sub;
							que.removeFirstOccurrence(temp);
							que.add(check[n*group]);
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

					int grp = Integer.parseInt(first,2)%number_of_groups_binary;

					boolean isFound = false;

					for(int w=n*grp;w<n*grp+n;w++)
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
					for (int e=0;e<cl;e++)
					{
						if(check[e]!=null)
						{
							System.out.print(check[e]);
							System.out.print("		");
							for(int k=0;k<bsize;k++)
							{
								System.out.print(arr[e][k]);
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