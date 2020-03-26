package com.fradantim.plotter.core.util;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fradantim.plotter.core.Threads.ColorRunnable;

public class FileSystemUtil {
	
	public enum AppProperty{
		PLOTTER_PIXELS_PER_POINT("Plotter.PixelsPerPoint",250),
		PLOTTER_FULL_SCREEN("Plotter.FullScreen",true),
		PLOTTER_PIXEL_LINE_WIDTH("Plotter.PixelLineWidth",2),
		PLOTTER_BACKGROUND_COLOR("Plotter.BackgroundColor",Color.BLACK),
		COLORIZER_DEFAULT_COLOR("Colorizer.DefaultColor",Color.WHITE);
		
		private AppProperty(final String propertyName, final Object defaultValue) {
			this.propertyName=propertyName;
			this.defaultValue=defaultValue;
		}

		private final String propertyName;
		private final Object defaultValue;
		private Object currentValue;
		
		public void changeValue(Object newValue) {
			currentValue=newValue;
			fillPropertiesFile(false);
		}
		
		public Object getCurrentValue() {
			if(currentValue== null) {
				fillPropertiesValues();
			}
			return currentValue;
		}
		
		public static AppProperty getAppProperty(String name) {
			return Arrays.stream(values()).filter(p-> p.propertyName.equals(name)).findFirst().get();
		}
	}
	
	private static void fillPropertiesFile(Boolean forceDefaultValue) {
		List<SimpleProperty> properties = new ArrayList<>();
		for(AppProperty appProperty: AppProperty.values()) {
			SimpleProperty property = new SimpleProperty(appProperty.propertyName, forceDefaultValue? appProperty.defaultValue : appProperty.getCurrentValue());
			properties.add(property);
		}
		
		save(properties, PROPERTIES_FILE);
	}
	
	private static void fillPropertiesValues() {
		if(! getFile(PROPERTIES_FILE).exists()) {
			fillPropertiesFile(true);
		} 

		List<SimpleProperty> storedProperties = load(PROPERTIES_FILE);
			
		for(SimpleProperty storedProperty : storedProperties) {
			try {
				AppProperty property = AppProperty.getAppProperty(storedProperty.name);
				property.currentValue = storedProperty.value;
			} catch (IllegalArgumentException e) {}
		}
		
		for(AppProperty appProperty : AppProperty.values()) {
			if(appProperty.currentValue ==null) {
				appProperty.currentValue=appProperty.defaultValue;					
			}
		}
	}
	
	private final static String ROOT_FOLDER="plotter";
	public final static String SCREENSHOTS_FOLDER=ROOT_FOLDER+"/screenshots";
	private final static String LOGS_FOLDER=ROOT_FOLDER+"/logs";
	
	private final static String LAST_JOBS_FILE = ROOT_FOLDER+"/LastJobs.json";
	private final static String PROPERTIES_FILE = ROOT_FOLDER+"/Properties.json";

	private final static ObjectMapper mapper= new ObjectMapper();
	
	static {
		mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		
		//create folders if those don't exist
		if(! getFile(ROOT_FOLDER).exists()) {
			for(String folder : Arrays.asList(ROOT_FOLDER, SCREENSHOTS_FOLDER, LOGS_FOLDER)) {
				File folderFile= getFile(folder);
				if(!folderFile.exists()) {
					createFolder(folderFile);
				}
			}
			fillPropertiesFile(true);
		} else {
			fillPropertiesValues();
		}			
	}
	
	private static void createFolder(File folder) {
		if(!folder.exists())
		    if(folder.mkdir()){
		    	System.out.println("Created successfully "+folder);
		    } else {
		    	System.out.println("Couldn't create directory "+folder);
		    }
	}
		
	public static File getFile(String relativePathToFile) {
		//is Gdx ready?
		if(Gdx.files!= null) {
			return Gdx.files.external(relativePathToFile).file();
		} else {
			String home=System.getProperty("user.home");
			return new File(home+"/"+relativePathToFile);
		}
	}
	
	private static <T> void save(List<T> list, String File) {
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(getFile(File), new ListWrapper<T>(list));
		} catch (IOException e) {
			e.printStackTrace(); //TODO logging
		}
	}
	
	private static <T> List<T> load(String strFile) {
		try {
			File file= getFile(strFile);
			if(file.exists()) {
				ListWrapper<T> jobsWrapper = mapper.readValue(file,new TypeReference<ListWrapper<T>>(){});
				return jobsWrapper.getItems();
			}
		} catch(Exception e) {
			e.printStackTrace(); //TODO logging
		}
		return Collections.emptyList();
	}
	
	public static void saveLastJobs(List<ColorRunnable> jobs) {
		save(jobs, LAST_JOBS_FILE);
	}
	
	public static List<ColorRunnable> loadLastJobs() {
		return load(LAST_JOBS_FILE);
	}	
	
	public static int getAvailableCores() {
		int corePoolSize = Runtime.getRuntime().availableProcessors();
		return corePoolSize<=1 ? corePoolSize : corePoolSize-1;
	}
}

final class ListWrapper<T>{

	@JsonTypeInfo(
	        use = JsonTypeInfo.Id.CLASS,
	        include = JsonTypeInfo.As.PROPERTY,
	        property = "type")
	private List<T> items;
	
	/** empty constructor for deserialization*/
	public ListWrapper() {}
	
	public ListWrapper(List<T> items) {
		this.items=items;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}
}

final class SimpleProperty{
	public String name;
	
	@JsonTypeInfo(
	        use = JsonTypeInfo.Id.CLASS,
	        include = JsonTypeInfo.As.PROPERTY,
	        property = "type")
	public Object value;
	
	/** empty constructor for deserialization*/
	public SimpleProperty() {}

	public SimpleProperty(String name, Object value) {
		this.name = name;
		this.value = value;
	}
}