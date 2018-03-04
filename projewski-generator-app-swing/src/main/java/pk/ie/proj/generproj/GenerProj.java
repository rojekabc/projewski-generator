package pk.ie.proj.generproj;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import pk.ie.proj.exceptions.GeneratorException;
import pk.ie.proj.interfaces.GeneratorInterface;
import pk.ie.proj.interfaces.PackageInterface;
import pk.ie.proj.interfaces.ParameterInterface;
import pk.ie.proj.interfaces.ViewDataInterface;
import pk.ie.proj.tools.Convert;
import pk.ie.proj.tools.GeneratedData;
import pk.ie.proj.tools.GeneratorStoreFile;
import pk.ie.proj.tools.Mysys;

public class GenerProj
	extends DefaultHandler
{
//	private String actualParam=null; // aktualnie obrabiany parametr pliku konfiguracyjnego z XMLa
//	private String actName=null, actTick=null;
//	private String rdBuf=null;
	private static String configurationFile="generproj.cnf";
	// Do systemu otwierania okien i zamykania, gdzie jedno jest zawsze aktualne
	// a wyjscie nastepuje, gdy juz zostanie zamkniete okno glowne
	private static java.util.Vector<JFrame> storeFrame = new java.util.Vector<JFrame>();
	private static JFrame actualFrame = null;

	// tablica nazw plikow z zapamietanymi generatorami
	private static java.util.Vector<String> vecGeneratorFile = new java.util.Vector<String>();
	// tablica zbiorow danych
	private static java.util.Vector<GeneratedData> vecGeneratedData = new java.util.Vector<GeneratedData>();
	// tablica klas testow
	private static java.util.Vector<ClassItem> vecTest = new java.util.Vector<ClassItem>();
	// tablica klas podgladow
	private static java.util.Vector<ClassItem> vecView = new java.util.Vector<ClassItem>();
	// tablica klas generatorow
	private static java.util.Vector<ClassItem> vecGeneratorType = new java.util.Vector<ClassItem>();
	// tablica klas pakietow
	private static Vector<Class<?>> vecPackage = new Vector<Class<?>>();

	// ciag okreslajacy miejsce z ustawieniami jezykowymi
	private static String langFolder = "docpl";
	private static HelpFrame helpframe = null;

	public static void removeGeneratorType( Object o )
	{
		vecGeneratorType.remove( o );
	}

	public static ClassItem [] listGeneratorTypes()
	{
		Vector<ClassItem> vecRet = new Vector<ClassItem>();
		vecRet.addAll(vecGeneratorType);
		int i, j;
		for ( i=0; i<vecPackage.size(); i++ )
		{
			PackageInterface pack = null;
			try {
				pack = (PackageInterface) vecPackage.get(i).newInstance();
			} catch (InstantiationException e) {
				Mysys.error(e);
				continue;
			} catch (IllegalAccessException e) {
				Mysys.error(e);
				continue;
			}
			Class<?> [] tmp = pack.listGenerator();
			if ( tmp == null )
				continue;
			for ( j=0; j<tmp.length; j++ )
			{
				ClassItem checkClass = new ClassItem(tmp[j]);
				if ( vecRet.contains( checkClass ) == false )
					vecRet.add( checkClass );
			}
		}
		ClassItem [] ret = new ClassItem[vecRet.size()];
		ret = vecRet.toArray(ret);
		return ret;
	}
	
	// po podanej sciezce ustal klase generatora i sprawdz czy jest to interfejs
	// GeneratorInterface oraz czy istnieje szansa stworzenia instancji takowej
	// klasy generatora, a nsatepnie ustalona nazwe dodaj do tablicy
	// Zamiast sciezki rownie dobrze mozna podac bezposrednio nazwe klasy. Wtedy
	// zostanie ona przetestowana pod katem tego, czy jest Generatorem i po
	// pozytywnym tescie dodana do tablicy
	public static ClassItem addGeneratorType( String classpath ) throws ClassNotFoundException
	{
		String classname = classpath.replace(
				System.getProperty("file.separator").charAt(0), '.');
		if ( classname.startsWith(".") )
			classname = classname.substring(1);
		if ( classname.endsWith(".class" ) )
				classname = classname.substring(0, classname.length()-6);
		
		Class<?> klasa;
		try {
			klasa = Class.forName(classname);
		} catch (ClassNotFoundException e) {
			Mysys.println(e.toString());
			throw e;
		}
		ClassItem classItem = new ClassItem(klasa);
		// sprawdz, czy poprawnie sie buduje
		// GeneratorInterface.buildGeneratorFromName( classname );
		
		if ( vecGeneratorType.contains( classItem ) )
			return null;
		vecGeneratorType.add( classItem );
		return classItem;
	}	
		
	public static String getLangFolder()
	{
		return langFolder;
	}

	// ustawienie katalogu j�zykowego, r�wnocze�nie sprawdza istnienie
	// plik�w koniecznych do funkcjonowania
	// Tam s� helpy i plik nazw //TODO: plik nazw
	public static boolean setLangFolder( String folderName )
	{
		if ( folderName == null )
			return false;
		java.io.File plik = new java.io.File( folderName );
		if ( !plik.exists() ) {
			try {
				URL resource = GenerProj.class.getResource("/" + folderName);
				if ( resource != null ) {
					plik = new File(resource.toURI());
				}
			} catch (URISyntaxException ignore) {
			}
			if (!plik.exists()) {
				return false;
			}
		}
		if ( !plik.isDirectory() )
			return false;
		langFolder = folderName;
		return true;
	}

	public static boolean addGeneratedData( GeneratedData gdt )
	{
		if ( vecGeneratedData.contains( gdt ) )
			return true;
		vecGeneratedData.add( gdt );
		return true;
	}

	public static void removeGeneratedData( Object gdt )
	{
		String filename = ((GeneratedData)gdt).getFileName();
		java.io.File f;
		if ( filename.endsWith(".gdt") )
			f = new java.io.File( filename );
		else
			f = new java.io.File( filename+".gdt" );
		if ( !f.delete() )
			Mysys.error("Nieudane usuwanie pliku " + f.getName());
		vecGeneratedData.remove( gdt );
	}

	public static Object [] listGeneratedData()
	{
		return vecGeneratedData.toArray();
	}

	public static GeneratorInterface createGenerator( String filename )
		throws GeneratorException
	{
			if ( filename.endsWith(".gen") )
				return GeneratorStoreFile.loadGeneratorFromFile( filename );
			else
				return GeneratorStoreFile.loadGeneratorFromFile( filename+".gen" );
	}
	
	// bez lub z rozszerzeniem .java plik interfejsu LaborDataInterface
	public static ClassItem addView( String classpath ) throws ClassNotFoundException
	{
		String classname = classpath.replace(
				System.getProperty("file.separator").charAt(0), '.');
		if ( classname.startsWith(".") )
			classname = classname.substring(1);
		if ( classname.endsWith(".class" ) )
				classname = classname.substring(0, classname.length()-6);
		Class<?> klasa;
		try {
			klasa = Class.forName(classname);
		} catch (ClassNotFoundException e) {
			Mysys.println(e.toString());
			throw e;
		}
		
		ClassItem classItem = new ClassItem(klasa);
		// sprawdzenie jakosci
		// buildViewFromName( classname );
		if ( vecView.contains( classItem ) )
			return null; // juz istnieje
		vecView.add( classItem );
		return classItem; // dodano
	}
	
	// wylistuj dost�pne widoki, ale z uwzgl�dnieniem �r�d�a danych
	// Odpytuje wtedy dost�pne widoki, czy s� w stanie przedstawi� dane �r�d�o danych
	// i je�eli tak, to wtedy dodaje je do listy
	public static ClassItem [] listView(ParameterInterface dataSource)
	{
		Vector<ClassItem> vecRet = new Vector<ClassItem>();
		int i, j;
		
		if ( dataSource == null )
			return GenerProj.listView();

		for ( i=0; i<vecView.size(); i++ )
		{
			ClassItem ci = vecView.get(i);
			ViewDataInterface vdi;
			try {
				vdi = (ViewDataInterface)ci.getClassItem().newInstance();
			} catch (InstantiationException e) {
				Mysys.error("Nie moge utworzyc instancji widoku: " + e.toString());
				continue;
			} catch (IllegalAccessException e) {
				Mysys.error("Nie moge utworzyc instancji widoku: " + e.toString());
				continue;
			}
			if ( vdi.canViewData(dataSource) )
				vecRet.add(ci);
		}
		
		for ( i=0; i<vecPackage.size(); i++ )
		{
			PackageInterface pack = null;
			try {
				pack = (PackageInterface) vecPackage.get(i).newInstance();
			} catch (InstantiationException e) {
				Mysys.error(e);
				continue;
			} catch (IllegalAccessException e) {
				Mysys.error(e);
				continue;
			}
			Class<?> [] tmp = pack.listViewData();
			if ( tmp == null )
				continue;
			for ( j=0; j<tmp.length; j++ )
			{
				ClassItem checkClass = new ClassItem(tmp[j]);
				
				if ( vecRet.contains( checkClass ) )
					continue;
				ViewDataInterface vdi;
				try {
					vdi = (ViewDataInterface)tmp[j].newInstance();
				} catch (InstantiationException e) {
					Mysys.error("Nie moge utworzyc instancji widoku: " + e.toString());
					continue;
				} catch (IllegalAccessException e) {
					Mysys.error("Nie moge utworzyc instancji widoku: " + e.toString());
					continue;
				}
				if ( vdi.canViewData(dataSource) )
					vecRet.add( checkClass );
			}
		}
		ClassItem [] ret = new ClassItem[vecRet.size()];
		ret = vecRet.toArray(ret);
		return ret;
	}
	
	public static ClassItem [] listView()
	{
		Vector<ClassItem> vecRet = new Vector<ClassItem>();
		vecRet.addAll(vecView);
		int i, j;
		for ( i=0; i<vecPackage.size(); i++ )
		{
			PackageInterface pack = null;
			try {
				pack = (PackageInterface) vecPackage.get(i).newInstance();
			} catch (InstantiationException e) {
				Mysys.error(e);
				continue;
			} catch (IllegalAccessException e) {
				Mysys.error(e);
				continue;
			}
			Class<?> [] tmp = pack.listViewData();
			if ( tmp == null )
				continue;
			for ( j=0; j<tmp.length; j++ )
			{
				ClassItem checkClass = new ClassItem(tmp[j]);
				if ( vecRet.contains( checkClass ) == false )
					vecRet.add( checkClass );
			}
		}
		ClassItem [] ret = new ClassItem[vecRet.size()];
		ret = vecRet.toArray(ret);
		return ret;
	}

	// bez lub z rozszerzeniem .java plik interfejsu LaborDataInterface
	public static ClassItem addTest( String classpath ) throws ClassNotFoundException
	{
		String classname = classpath.replace(
				System.getProperty("file.separator").charAt(0), '.');
		if ( classname.startsWith(".") )
			classname = classname.substring(1);
		if ( classname.endsWith(".class" ) )
				classname = classname.substring(0, classname.length()-6);
		// sprawdzenie jakosci
		//buildTestFromName( classname );
		Class<?> klasa;
		try {
			klasa = Class.forName(classname );
		} catch (ClassNotFoundException e) {
			Mysys.println(e.toString());
			throw e;
		}
		
		ClassItem classItem = new ClassItem(klasa);
		
		if ( vecTest.contains( classItem ) )
			return null; // juz istnieje
		vecTest.add( classItem );
		return classItem; // dodano
	}

	public static ClassItem [] listTest()
	{
		Vector<ClassItem> vecRet = new Vector<ClassItem>();
		vecRet.addAll(vecTest);
		int i, j;
		for ( i=0; i<vecPackage.size(); i++ )
		{
			PackageInterface pack = null;
			try {
				pack = (PackageInterface) vecPackage.get(i).newInstance();
			} catch (InstantiationException e) {
				Mysys.error(e);
				continue;
			} catch (IllegalAccessException e) {
				Mysys.error(e);
				continue;
			}
			Class<?> [] tmp = pack.listLaborData();
			if ( tmp == null )
				continue;
			for ( j=0; j<tmp.length; j++ )
			{
				ClassItem checkClass = new ClassItem(tmp[j]);
				if ( vecRet.contains( checkClass ) == false )
					vecRet.add( checkClass );
			}
		}
		ClassItem [] ret = new ClassItem[vecRet.size()];
		ret = vecRet.toArray(ret);
		return ret;
	}

	// nazwa bez rozszerzenia / generator - zapisanie do pliku i dodanie do
	// zbior�w - jezeli gi jest jako null, to znaczy, ze istnieje zalozenie, iz
	// dany plik istnieje i nalezy wykonac probe odczytywania pliku
	public static boolean addGenerator(String filename, GeneratorInterface gi)
		throws GeneratorException
	{
		if ( gi == null )
			gi = createGenerator( filename ); // na niepowodzeniu bedzie null
		else
		{
			if ( filename.endsWith(".gen") )
				GeneratorStoreFile.saveGeneratorToFile( filename, gi );
			else
				GeneratorStoreFile.saveGeneratorToFile( filename+".gen", gi );
		}
		if ( vecGeneratorFile.contains( filename ) )
			return true;
		if ( gi == null )
			return false;
		vecGeneratorFile.add( filename );
		return true;
	}

	// usuni�cie ze zbior�w generator'a - usuwa r�wnie� wygenerowany plik !
	// z generatorem
	public static void removeGenerator( String filename )
	{
		java.io.File f;
		if ( filename.endsWith(".gen") )
			f = new java.io.File( filename );
		else
			f = new java.io.File( filename+".gen" );
		if ( !f.delete() )
			Mysys.error("Nieudane usuwanie pliku " + f.getName());
		vecGeneratorFile.remove( filename );
	}

	public static Vector<String> listGenerator( )
	{
		return vecGeneratorFile;
	}
	
	// wyj�cie z zapisaniem aktualnych ustawie�
	protected static void doSystemExit()
	{
		doSaveConfiguration();
		System.exit(0);
	}

	protected static void doCopyOfConfigurationFile()
	{
			java.io.File plik = new java.io.File(configurationFile);
			java.io.File pliktmp = new java.io.File( configurationFile + ".sav");
			if ( pliktmp.exists() )
			{
				System.out.println("Plik z kopi� zapsaow� ju� istnieje. Praca wstrzymana.");
				System.exit(0);
			}
			if ( !plik.renameTo(pliktmp) )
			{
				System.out.println("Nie mog� utowrzy� kopii zapasowej pliku konfiguracji.");
				System.out.println("Wstrzymanie pracy programu.");
				System.exit(0);
			}
	}

	protected static void doLoadConfiguration()
	{
		try {
			DefaultHandler han = new GenerProj();
			SAXParserFactory fac = SAXParserFactory.newInstance();
			SAXParser par = fac.newSAXParser();
			java.io.File plik = new java.io.File(configurationFile);
			if ( !plik.exists() )
				doSaveConfiguration();
			par.parse( new java.io.File(configurationFile), han );
		} catch ( java.io.IOException e ) {
			System.out.println("B��d wczytywania pliku konfiguracyjnego.");
			System.out.println( e.toString() );
		} catch ( SAXException e ) {
			System.out.println("B��d parsera XML na pliku konfiguracyjnym");
			System.out.println("Pr�ba utworzenia kopii pliku konfiguracyjnego");
			doCopyOfConfigurationFile();
		} catch ( javax.xml.parsers.ParserConfigurationException e ) {
			System.out.println("B��d konfiguracji parsera XML");
			System.out.println("Pr�ba utworzenia kopii pliku konfiguracyjnego");
			doCopyOfConfigurationFile();
		}
	}

	protected static void doSaveConfiguration()
	{
		try {
			java.io.PrintStream savefile = new java.io.PrintStream(
					new java.io.FileOutputStream( configurationFile ) );
//			Object [] vecGeneratorType = listGeneratorTypes(); 
			savefile.println("<?xml version='1.0' encoding='Latin1'?>");
			// zapisz wektor z klasami generator�w
			savefile.println("<GenerProjConfiguration>");
			for ( int i = 0; i<vecGeneratorType.size(); i++ )
			{
				savefile.println("<GeneratorType class=\"" +
						vecGeneratorType.get(i).toString() + "\" />");
			}
			for ( int i = 0; i<vecGeneratorFile.size(); i++ )
			{
				savefile.println("<GeneratorFile file=\"" +
						vecGeneratorFile.get(i) + "\" />");
			}
			for ( int i = 0; i<vecGeneratedData.size(); i++ )
			{
				GeneratedData gdt = (GeneratedData)vecGeneratedData.get(i);
				savefile.println("<GeneratedData name=\"" + gdt.getName()
						+ "\" tick=\"" + gdt.getDataTick() + "\" />");
			}
			for ( int i = 0; i<vecTest.size(); i++ )
			{
				savefile.println("<Test class=\"" +
						vecTest.get(i).toString() + "\" />");
			}
			for ( int i = 0; i<vecView.size(); i++ )
			{
				savefile.println("<View class=\"" +
						vecView.get(i).toString() + "\" />");
			}
			for ( int i = 0; i<vecPackage.size(); i++ )
			{
				savefile.println("<Package class=\"" +
						vecPackage.get(i).getName() + "\" />");
			}
			savefile.println("</GenerProjConfiguration>");
			savefile.close();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public static void doSwitchToFrame( JFrame frame )
	{
		if ( frame != null )
			storeFrame.add( frame );
		if ( storeFrame.size() == 0 )
			doSystemExit();
		if ( actualFrame != null )
			actualFrame.setVisible(false);
		actualFrame = (JFrame)storeFrame.lastElement();
		actualFrame.pack();
		actualFrame.setVisible(true);
	}

	public static void doCloseActualFrame()
	{
		if ( actualFrame == null )
			doSystemExit();
		actualFrame.setVisible(false);
		actualFrame.dispose();
		if ( storeFrame.size() == 0 )
			doSystemExit();
		storeFrame.remove( actualFrame );
		if ( storeFrame.size() == 0 )
			doSystemExit();
		actualFrame = (JFrame)storeFrame.lastElement();
		actualFrame.setVisible(true);
	}

	public static void main( String [] args )
	{
		try
		{
			LookAndFeelInfo[] installedLookAndFeels = UIManager.getInstalledLookAndFeels();
			if ( installedLookAndFeels != null )
			{
				for (LookAndFeelInfo lookAndFeelInfo : installedLookAndFeels)
				{
					System.out.println(lookAndFeelInfo.getClassName());
				}
			}
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			
			String osname = System.getProperty("os.name");
			if ( osname.equals("Windows XP") )
				UIManager.setLookAndFeel("com.stefankrause.xplookandfeel.XPLookAndFeel");
			else if ( osname.equals("Linux") )
			{
//		javax.swing.UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
//		javax.swing.UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel"); 
		javax.swing.UIManager.setLookAndFeel("com.digitprop.tonic.TonicLookAndFeel"); 
			}
			else
				System.out.println("Nieznany system operacyjny: "+osname);
		}
		catch ( Exception e )
		{
			System.out.println(e.toString());
		}
		doLoadConfiguration();
		if ( !setLangFolder( langFolder ) )
		{
				System.out.println("Cannot use correctly language folder "+langFolder);
				System.exit(0);
		}
		doSwitchToFrame( new MainFrame() );
	}

	/** 
	* Metoda wywo�ywana przy rozpocz�ciu analizy dokumentu XML
 */
	public void startDocument()
		throws SAXException
	{
//		rdBuf = new String("");
	}

	/** 
	* Metoda wywo�ywana przy zako�czeniu analizy dokumentu XML
 */
	public void endDocument()
		throws SAXException
	{
	}

	/** 
	* Metoda wywo�ywana przy rozpocz�ciu analizowania znacznika XML
 */
	public void startElement( String uriname, String sname, String qname,
			Attributes attr )
		throws SAXException
	{
		try {
			String name = sname;
			if ( name.equals("") )
				name = qname;
			
			if ( name.equals("GeneratedData") )
			{
				long l = Convert.tryToLong( attr.getValue("tick") );
				GeneratedData gdt = new GeneratedData( attr.getValue( "name" ), l );
				addGeneratedData( gdt );				
			}
			else if ( name.equals("GeneratorType") )
			{
				if ( addGeneratorType( attr.getValue("class") ) == null )
					Mysys.println("Nieudane �adowanie typu generatora " + attr.getValue("class") );
			}
			else if ( name.equals("GeneratorFile") )
			{
				if ( addGenerator( attr.getValue("file"), null ) == false )
					Mysys.println("Nieudane �adowanie generatora " + attr.getValue("file") );
			}
			else if ( name.equals("Test") )
			{
				addTest(attr.getValue("class"));
			}
			else if ( name.equals("View") )
			{
				addView( attr.getValue("class") );
			}
			else if ( name.equals("Package") )
			{
				addPackage( attr.getValue("class") );
			}
		} catch ( Exception e ) {
			System.out.println( "startElement " + e.toString() );
		}
	}

	private boolean addPackage(String value) {
		if ( value == null )
			return false;
		Class<?> klasa;
		try {
			klasa = Class.forName(value);
		} catch (ClassNotFoundException e) {
			Mysys.println(e.toString());
			return false;
		}
		if ( vecPackage.contains(klasa) )
			return false;
		vecPackage.add(klasa);
		return true;
	}

	/** 
	* Metoda wywo�ywana przy zamkni�ciu analizowania znacznika XML
 */
/*	public void endElement( String uriname, String sname, String qname )
	{
	}
*/
	/** 
	* Metoda wywo�ywana po uzyskaniu zawarto�ci znacznika XML
 */
/*	public void characters( char[] buf, int offset, int len )
		throws SAXException
	{
	}
*/
	// po��cz nazw� bazowa z nazw� klasy, kt�ra mo�e by� z kropkami
	// i ostatecznie dodaj nazw� klasy z rozszerzeniem .htm
	private static java.io.File concat( String basefolder, String classname )
	{
		int i = 0;
		java.io.File x = new java.io.File( basefolder );
		i = classname.indexOf('.');
		while ( i != -1 )
		{
			x = new java.io.File( x, classname.substring(0, i));
			classname = classname.substring( i+1 );
			i = classname.indexOf('.');
		}
		x = new java.io.File( x, classname+".htm");
		return x;
	}
	
	public static boolean isHelpFile( String name )
	{
		java.io.File helpfile = concat(langFolder, name);
		if ( (!helpfile.exists()) || (!helpfile.isFile()) )
		{
			System.out.println("No helpfile: " + helpfile.toString());
			return false;
		}
		return true;
	}

	public static void showHelp( String name )
	{
		java.io.File helpfile = concat(langFolder, name);
		if ( !isHelpFile( name ) )
		{
			System.out.println("Niedost�pny plik pomocy " + helpfile.getPath());
			return;
		}
		if ( helpframe == null )
			helpframe = new HelpFrame( helpfile.getAbsolutePath(), 10, 10, 450, 300 );
		else
			helpframe.setNewHelp( helpfile.getAbsolutePath() );
	}

	public static void showHelp( ParameterInterface pi )
	{
		String name = pi.getClass().getName();
		if ( isHelpFile( name ) )
			showHelp(name);
		else {
			if ( helpframe == null )
				helpframe = new HelpFrame( pi, 10, 10, 450, 300 );
			else
				helpframe.setNewHelp( pi );
		}
	}

	public static void showHelp( Object obj )
	{
		String name = obj.getClass().getName();
		showHelp( name );
	}

	public static String getDescription( int index )
	{
		try {
			return Mysys.getDescription(GenerProj.class.getSimpleName(), "" + index, "generproj", null);
		} catch ( Exception e )	{
			Mysys.println(e.toString());
			return "";
		}
	}
}
