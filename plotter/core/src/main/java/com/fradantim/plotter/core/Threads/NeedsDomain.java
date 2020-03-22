package com.fradantim.plotter.core.Threads;

import java.util.HashMap;
import java.util.List;

public interface NeedsDomain {
	
	public void setDomain(HashMap<String, List<Float>> domainByVar);
	
	public List<String> getVars();
	
}
