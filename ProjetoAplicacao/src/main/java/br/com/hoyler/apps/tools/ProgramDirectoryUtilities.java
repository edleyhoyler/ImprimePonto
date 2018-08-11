package br.com.hoyler.apps.tools;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

import br.com.hoyler.apps.imprimeponto.CadastroMetodoMain;

public class ProgramDirectoryUtilities
{
	private static String getJarName()
	{
		return new File(ProgramDirectoryUtilities.class.getProtectionDomain()
				.getCodeSource()
				.getLocation()
				.getPath())
				.getName();
	}

	private static boolean runningFromJAR()
	{	
	    String JAR_EXTENSION = "jar";
		String jarName = getJarName();
		return jarName.contains("." + JAR_EXTENSION);
	}

	public static String getProgramDirectory()
	{
		if (runningFromJAR())
		{
			return getCurrentJARDirectory();
		} else
		{
			return getCurrentProjectDirectory();
		}
	}
	
	private static String getCurrentProjectDirectory()
	{
		return new File("").getAbsolutePath();
	}

	private static String getCurrentJARDirectory()
	{
		try
		{
			return new File(ProgramDirectoryUtilities.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
		} catch (URISyntaxException exception)
		{
			exception.printStackTrace();
		}

		return null;
	}
	
	public static String getApplcatonPathFileReal(){
		
		String file  = CadastroMetodoMain.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		return file;
	}
	
	public static String getApplcatonPathReal(){
		File pto = null;
		try {
			pto = new File(CadastroMetodoMain.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return(pto.getAbsolutePath());
	}
	
    public static String getApplcatonPath(){
        CodeSource codeSource = CadastroMetodoMain.class.getProtectionDomain().getCodeSource();
        File rootPath = null;
        try {
            rootPath = new File(codeSource.getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }           
        return rootPath.getParentFile().getPath();
    }//end of getApplcatonPath()

}