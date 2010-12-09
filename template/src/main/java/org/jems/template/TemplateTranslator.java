/*******************************************************************/
/*
	File: TemplateTranslator.java

	A simple template translator.

	The translator looks for the tags <template></template>
	which is turned into a method called generate. All literal
	text between the template tags is turned into out.print
	statements. 

	Custom code is pacled in <% %> fragments and output
	verbatim.

	Author: James Cowan	
*/
/*******************************************************************/

package org.jems.template;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/*******************************************************************/
/**
 * TemplateTranslator
 *
 */
/*******************************************************************/

public class TemplateTranslator implements FileFilter
{
	protected String		m_targetDir;
	protected String		m_srcDir;
	
	protected final String	TEMPLATE = "template";
	protected final String	SCRIPT_BEGIN = "<%";
	protected final String  SCRIPT_END = "%>";
	
	static protected Logger		m_log = Logger.getLogger(TemplateTranslator.class);
	
	public TemplateTranslator(String srcDir, String targetDir)
	{
		m_srcDir = srcDir;
		m_targetDir = targetDir;
	}

	/*******************************************************************/
	/* public methods */
	/*******************************************************************/
	/** accept */
	
	public boolean accept(File file)
	{
		return file.getAbsolutePath().toLowerCase().endsWith("."+TEMPLATE);
		
	} // accept
	
	/*******************************************************************/
	/** generate */
	
	public void generate() throws IOException, TemplateException
	{
	File srcDir = new File(m_srcDir);
	File targetDir = new File(m_targetDir);
	File templateFiles[] = srcDir.listFiles(this);
	
		FileUtils.forceMkdir(targetDir);
	
		if (templateFiles == null)
		{
			throw new TemplateException("no templates");
		}
		
		for (int count=0; count<templateFiles.length; count++)
		{
		File templateFile = templateFiles[count];
		String srcName = templateFile.getName();
		String destName = srcName.substring(0, srcName.indexOf("."))+".java";
		File destFile = new File(targetDir, destName);
		boolean update = true;
		
			if (destFile.exists())
			{
				if (destFile.lastModified() > templateFile.lastModified())
				{
					update = false;
					m_log.info(destName+" is up to date");
				}
			}
		
			if (update)
			{
			FileOutputStream fos = new FileOutputStream(destFile);
			PrintStream ps = new PrintStream(fos);
			
				m_log.info("Generate: "+destName);
				generateTemplate(templateFile, ps);
				fos.close();
			}
		}
		
	} // generate
	
	/*******************************************************************/
	/* protected methods */
	/*******************************************************************/
	/** generateTemplate */
	
	protected void generateTemplate(File templateFile, PrintStream ps)
	throws IOException, TemplateException
	{
	String src = getSource(templateFile);
	String templateTag = "<"+TEMPLATE+">";
	String endTemplateTag = "</"+TEMPLATE+">";
	
		while (true)
		{
		int startTemplate = src.indexOf(templateTag);
		int endTemplate = src.indexOf(endTemplateTag);

			// check if any more templates or exist
	
			if (startTemplate < 0)
			{
				ps.print(src.toString());
				return;
			}
		
			// check if valid template
		
			if (endTemplate < 0)
			{
				throw new TemplateException("Missing "+templateTag);
			}
			
			ps.print(src.substring(0, startTemplate));
		
			startTemplate += templateTag.length();
		
			String template = src.substring(startTemplate, endTemplate);
		
			generateTemplate(ps, template);
		
			src = src.substring(endTemplate+endTemplateTag.length());
		}
	
	} // generateTemplate
	
	/*******************************************************************/
	/** generateTemplate */
	
	protected void generateTemplate(PrintStream ps, String src) 
	throws IOException, TemplateException
	{
		while (true)
		{
		int begin = src.indexOf(SCRIPT_BEGIN);
		
			if (begin < 0)
			{
				generateTemplate(ps, src, src.length());
				break;
			}
						
			int end = src.indexOf(SCRIPT_END);
			
			if (end < 0)
			{
				throw new TemplateException("Missing "+SCRIPT_END);
			}
			
			generateTemplate(ps, src, begin);
			
			begin += SCRIPT_BEGIN.length();

			String script = src.substring(begin, end);
			
			ps.print(script);
			
			src = src.substring(end+SCRIPT_END.length());
		}
		
	} // generateTemplate
	
	/*******************************************************************/
	/** generateTemplate */
	
	protected void generateTemplate(PrintStream ps, String src, int end) 
	throws IOException
	{
	String tmp = "";
	
		for (int count=0; count<end; count++)
		{
		char ch = src.charAt(count);
		
			switch (ch)
			{
			case '\n':
				ps.println("");
				ps.print("\t\tout.println(\""+tmp+"\");");
				tmp = "";
				break;
			case '\r':
				break;
			default:
				tmp += ch;
			}
		}
		
		if (tmp.length() > 0)
		{
			ps.println("");
			ps.print("\t\tout.print(\""+tmp+"\");");			
		}
		
	} // generateTemplate
	
	/*******************************************************************/
	/** getSource */
	
	protected String getSource(File file) 
	throws IOException, TemplateException
	{
	StringBuffer sb = new StringBuffer();
	BufferedReader br = null; 
	
		try
		{
		FileReader fr = new FileReader(file);
		char buffer[] = new char[4096];
		int nBytes;
		
			br = new BufferedReader(fr);
		
			while ((nBytes=br.read(buffer)) >= 0)
			{
				if (nBytes > 0)
				{
					sb.append(buffer, 0, nBytes);
				}
			}	
		}
		catch (FileNotFoundException fe)
		{
		String msg = "Cannot read file: "+file.getAbsolutePath()+
					 " error: "+fe.getMessage();
		
			throw new TemplateException(msg);
		}
		finally
		{
			if (br != null)
			{
				br.close();
			}
		}
		
		return sb.toString();
		
	} // getSource
	
	/*******************************************************************/
	/* static classes */
	/*******************************************************************/
	
	public static void main(String argv[])throws TemplateException
	{
		if (argv.length < 2)
		{
		String msg = "arguments are source directory and target directory";
		
			throw new TemplateException(msg);
		}
		
		try
		{
		TemplateTranslator translator = new TemplateTranslator(argv[0], argv[1]);

			translator.generate();
		}
		catch (Exception e)
		{
			System.out.println("Exception: "+e.getMessage());
			e.printStackTrace(System.out);
			System.exit(1);
		}
	}
	
} // TemplateTranslator

/*******************************************************************/


