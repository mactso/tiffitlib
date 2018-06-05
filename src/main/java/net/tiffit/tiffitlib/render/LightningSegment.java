package net.tiffit.tiffitlib.render;

import net.minecraft.util.math.Vec3d;
import net.tiffit.tiffitlib.utils.RenderUtils;

public class LightningSegment {

	private Vec3d end;
	private Vec3d origin;
	private int color;
	
	public LightningSegment(Vec3d end, Vec3d origin, int color){
		this.end = end;
		this.origin = origin;
		this.color = color;
	}
	
	public void render(){
		RenderUtils.renderLine(new Vec3d(0, 0, 0), end, origin, color);
	}
	
}
