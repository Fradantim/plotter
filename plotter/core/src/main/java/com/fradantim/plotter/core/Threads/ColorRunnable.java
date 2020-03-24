package com.fradantim.plotter.core.Threads;

import com.badlogic.gdx.graphics.Color;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fradantim.plotter.core.Plotter;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EulerPVITask.class),
        @JsonSubTypes.Type(value = ImprovedEulerPVITask.class),
        @JsonSubTypes.Type(value = RungeKuttaPVITask.class),
        @JsonSubTypes.Type(value = SimpleFunctionTask.class),
        @JsonSubTypes.Type(value = SimplePipeTask.class)
})
public interface ColorRunnable extends Runnable{
	
	public Color getColor();
	
	public void setPlotter(Plotter plotter);	
	
	public default String getFormattedName() {
		return toString();
	}
	
	public void innerRun();
	
	public default void run() {
		try {
			innerRun();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
