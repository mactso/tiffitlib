package net.tiffit.tiffitlib.utils;

import java.util.Map;

import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ParticleUtils {

	private static Map<Integer, EnumParticleTypes> PARTICLE_MAP;
	private static Map<String, EnumParticleTypes> PARTICLE_NAME_MAP;
	
	public static EnumParticleTypes createParticle(String enumname, String name, int id, boolean ignorerange, int argcount){
		EnumParticleTypes particle = EnumHelper.addEnum(EnumParticleTypes.class, enumname, new Class<?>[]{String.class, int.class, boolean.class, int.class}, name, id, ignorerange, argcount);
		checkFields();
		PARTICLE_MAP.put(particle.getParticleID(), particle);
		PARTICLE_NAME_MAP.put(particle.getParticleName(), particle);
		return particle;
	}
	
	private static void checkFields(){
		if(PARTICLE_MAP == null){
			PARTICLE_MAP = ReflectionHelper.getPrivateValue(EnumParticleTypes.class, null, 53);
		}
		if(PARTICLE_NAME_MAP == null){
			PARTICLE_NAME_MAP = ReflectionHelper.getPrivateValue(EnumParticleTypes.class, null, 54);
		}
	}
	
	
}
