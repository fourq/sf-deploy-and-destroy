package system;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import system.ANSIControlCodes;

public class DirectoryEnvironment implements MetadataEnvironment {

	private String name = null;
	private String directory = null;

	public DirectoryEnvironment(String name) {

		this.name = name;
		this.directory = PropertyReader.getEnviromentProperty(name,
				"dir.source.root");
	}

	@Override
	public String getEnvironment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public String getLogin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getLocationFolder() {
		String folderName = PropertyReader.USER_PATH + File.separator
				+ this.name;
		File folder = new File(folderName);

		return folder;
	}

	@Override
	public File getSourceFolder() {
		String folderName = PropertyReader.USER_PATH
				+ File.separator
				+ this.name
				+ File.separator
				+ PropertyReader
						.getSystemProperty("sf.environments.unzip.src.name");
		File folder = new File(folderName);

		return folder;
	}

	public File getRetrieveZip() {

		return null;
	}

	@Override
	public File getDestroyZip() {
		String zip = PropertyReader.USER_PATH + File.separator + this.name
				+ File.separator
				+ PropertyReader.getSystemProperty("sf.destruct.zip.file.name");
		File zipFile = new File(zip);

		return zipFile;
	}

	@Override
	public PackageBuilder retreive(String overrideSourceDest) {
		
		System.out.println(ANSIControlCodes.WHITE + "### Retrieving " + this.name + " (directory) ###");

		File dir = new File(PropertyReader.USER_PATH + File.separator
				+ this.name);
		dir.mkdirs();

		File sourceDir = new File(this.directory);
		if (!sourceDir.exists()) {
			try {
				throw new Exception("No directory named " + this.directory
						+ " found. Please check your configuration file: "
						+ PropertyReader.USER_PATH + File.separator + this.name);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(1);
		}

		File copyToDir  = new File(this.getLocationFolder()
				+ File.separator
				+ PropertyReader
						.getSystemProperty("sf.environments.unzip.src.name"));
		
		// override dest dir if supplied
		File destFileOverride = null;
		if(overrideSourceDest != null) {
			destFileOverride = new File(overrideSourceDest);
			if(destFileOverride.exists()) {
				copyToDir = destFileOverride;
			}
		}
		
		try {
			FileUtils.copyDirectory(sourceDir, copyToDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void printRetreiveChanges() {
		// TODO Auto-generated method stub

	}
}
