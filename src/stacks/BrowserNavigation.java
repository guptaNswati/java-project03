package stacks;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Simulates a browser's back and forward buttons by recording links that are visited
 * and then keeping a stack of "back" links and a stack of "forward" links.
 *
 * @author Foothill College, Bita M, [YOUR NAME HERE]
 */
public class BrowserNavigation 
{
	public static final boolean SHOW_DETAILS = true;

	// Two stacks: one for "Back" button links; another for "Forward" button links
	private StackList<String> backLinks;
	private StackList<String> forwardLinks;
	
	// the current webpage the user is viewing
	private String currentLink;
	
	// TODO: define the class Navigtor that provides three navigation options where the user can:
	//       1. Set the current link via setCurrentLink(linkName) method.
	//       2. Replace the current link by going back one link via goBack() method.
	//       3. Replace the current link by going forward one link via goForward() method.
	private Navigator navigationFeature;
	
	/**
	 * Sets up the navigation feature of the browser with empty back and forward links.
	 */
	public BrowserNavigation()
	{
		navigationFeature = new Navigator();
		this.currentLink = navigationFeature.getCurrentLink();
		this.backLinks = navigationFeature.getBackLinks();
		this.forwardLinks = navigationFeature.getForwardLinks();
	}

	/**
	 * Accessor method to the navigation feature.
	 * @return    an object that enables the navigating the available links.
	 */
	protected Navigator getNavigationFeature()
	{
		return navigationFeature;
	}
	
	/**
	 * Search both back links and forward links to see if we can find 
	 * the link of interest. 
	 * @param linkName	The link to search in the backLinks and forwardLinks.
	 */
	public boolean search(String linkName)
	{
		// first check if the current page equals the requested link
		if (currentLink.contains(linkName))
			return true;

		// TODO: Prior to calling search() method backLinks object should store a Stack of links.
		Iterator<String> iterator = backLinks.iterator();	
		
		// iterates over the backLinks to see if link has been seen
		while (iterator.hasNext())
		{
			String walker = iterator.next();
			if (walker.equals(linkName))
				return true;
		}

		// TODO: Prior to calling search() method forwardLinks object should store a Stack of links.
		iterator = forwardLinks.iterator();
		
		// iterates over the forwardLinks to see if link has been seen
		while (iterator.hasNext())
		{
			String walker = iterator.next();
			if (walker.equals(linkName))
				return true;
		}
		
		// link is not seen
		return false;
	}

	/**
	 * Print both back and forward links.
	 * @param message	Additional details about the current set of links to be printed.
	 */
	public void printLinks(String message)
	{
		System.out.println("\n\n" + message);
		
		// get a reference to the immutable current link String object
		this.currentLink = navigationFeature.getCurrentLink();
		System.out.println("Current Link: " + currentLink);
		
		// print the two stacks that are keeping track of back and forward links
		System.out.println("\n" + backLinks);
		System.out.println("\n" + forwardLinks);
	}

	/**
	 * Creates an object of type BrowserNavigation which contains two StackList objects:
	 * one stack for back links and another stack for forward links.
	 * Scans a plain text file to determine which links is the current link and whether to go
	 * forward or backward. Also, search to determine whether a given link is the current
	 *  link or lies in the forward/back collections.
	 */
	public static void main(String[] args) 
	{
		// TODO: Test all features of the Navigator class
		//       This includes testing for boundary conditions.
		final String FILENAME = "resources/links.txt";
		
		// NOTE: An example of testing the boundary condition when back links stack is empty
		//final String FILENAME = "resources/popEmptyStackOfLinks.txt";
		
		// TODO: Provide test input files in addition to those provide.
		// final String FILENAME = ??

		BrowserNavigation bn = new BrowserNavigation();
		Navigator navigator = bn.getNavigationFeature();
		
		File infile = new File(FILENAME);
		try 
		{
			Scanner input = new Scanner(infile);

			String line = "";
			int lineNum = 0;
			while (input.hasNextLine()) 
			{
				lineNum++;
				line = input.nextLine(); 
				String [] tokens = line.split(" ");

				// Note: Once we see a new link. The forward link stack is not valid.
				//       So, need to clear the forward links
				if (line.contains("link"))
				{
					String linkName = tokens[1];

					/* TODO: Sets currentLink to the supplied link address. Places the 
				             old currentLink on the backLinks stack. Then, clears the 
				             forwardLinks stack.sets current link, 
					 */
					navigator.setCurrentLink(linkName);

					if (SHOW_DETAILS)
						bn.printLinks("At line #" + lineNum + ": " + line);
					continue;
				}

				/* TODO: If there is a link on the backLinks stack, goes to the top link on the
				 *       backLinks stack. Pushes the old currentLink onto the forwardLinks stack.
				 *       Remember to check for boundary conditions.
				 */
				if (line.contains("back"))
				{
					navigator.goBack();

					if (SHOW_DETAILS)
						bn.printLinks("At line #" + lineNum + ": " + line);
					continue;
				}

				/* TODO: If there is a link on the forwardLinks stack, goes to the top link on the
				 *       forwardLinks stack. Pushes the old currentLink onto the backLinks stack.
				 *       Remember to check for boundary conditions.
				 */
				if (line.contains("forward"))
				{					
					navigator.goForward();

					if (SHOW_DETAILS)
						bn.printLinks("At line #" + lineNum + ": " + line);
					continue;
				}

				// search for a link
				if (line.contains("contains"))
				{
					String linkName = tokens[1];
					boolean found = bn.search(linkName);
					if (found)
						System.out.printf("\nLink %s found\n", linkName);
					else
						System.out.printf("\nLink %s NOT found\n", linkName);
				}
			}		
			
			// closing the input file resource
			input.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}

		bn.printLinks("\nAt the end of test file " + FILENAME);


		// flush the error stream
		System.err.flush();

		System.out.println("\nDone with BrowserNavigation.");
	}	
}
